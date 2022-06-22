package zw.co.macheyo.mhuricore.service.saleorderitem;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import zw.co.macheyo.mhuricore.exception.BusinessException;
import zw.co.macheyo.mhuricore.exception.InventoryNotEnoughException;
import zw.co.macheyo.mhuricore.exception.ResourceNotFoundException;
import zw.co.macheyo.mhuricore.model.Inventory;
import zw.co.macheyo.mhuricore.model.SaleOrderItem;
import zw.co.macheyo.mhuricore.service.inventory.InventoryService;
import zw.co.macheyo.mhuricore.service.saleorder.SaleOrderService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Kudzai.Macheyo
 */
@Slf4j
@Service
public class SaleOrderItemServiceImpl implements SaleOrderItemService{
    @Autowired
    SaleOrderItemRepository repository;
    @Autowired
    SaleOrderService saleOrderService;
    @Autowired
    InventoryService inventoryService;
    @Autowired
    SaleOrderItemModelAssembler assembler;


    @Override
    public Optional<SaleOrderItem> findById(Object id) {
        return repository.findById((Long) id);
    }

    @Override
    public List<EntityModel<SaleOrderItem>> findAll() {
        return repository.findAll()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SaleOrderItem> create(SaleOrderItem saleOrderItem) {
        if(inventoryService.isAvailable(saleOrderItem.getProduct(),saleOrderItem.getQuantity())) {
            saleOrderItem.setOrder(saleOrderService.findByReference(saleOrderItem.getOrder().getReference()).orElseThrow(() -> new ResourceNotFoundException("Sale order", "reference", saleOrderItem.getOrder().getReference())));
        } else {
            Inventory inventory = inventoryService.findByProduct(saleOrderItem.getProduct()).orElseThrow(()->new ResourceNotFoundException("Inventory","product name",saleOrderItem.getProduct().getName()));
            double quantityRemaining = inventory.getQuantity();
            throw new InventoryNotEnoughException(saleOrderItem.getProduct().getName(), quantityRemaining);
        }
        return Optional.of(repository.save(saleOrderItem));
    }

    @Override
    public Optional<SaleOrderItem> update(SaleOrderItem saleOrderItem) {
        return repository.findById(saleOrderItem.getId()).map(soi->{
            soi.setProduct(saleOrderItem.getProduct());
            soi.setQuantity(saleOrderItem.getQuantity());
            soi.setOrder(saleOrderItem.getOrder());
            soi.setDelivered(saleOrderItem.isDelivered());
            return repository.save(soi);
        });
    }

    @Override
    public void delete(SaleOrderItem saleOrderItem) {
        repository.delete(saleOrderItem);
    }

    @Override
    public List<EntityModel<SaleOrderItem>> findByOrderId(Long id) {
        return repository.findByOrderId(id)
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SaleOrderItem> delivered(SaleOrderItem saleOrderItem) {
        return repository.findById(saleOrderItem.getId()).map(soi ->{
            Optional<Inventory> inventory = inventoryService.findByProduct(soi.getProduct());
            if(inventory.isPresent()){
                Inventory i = inventory.get();
                i.setQuantity(i.getQuantity() - soi.getQuantity());
                inventoryService.update(i);
            }else throw new BusinessException(soi.getProduct().getName()+" is out of stock.");
            soi.setDelivered(true);
            return repository.save(soi);
        });
    }
}
