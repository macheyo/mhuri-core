package zw.co.macheyo.mhuricore.service.purchaseorderitem;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import zw.co.macheyo.mhuricore.enums.InventoryStatus;
import zw.co.macheyo.mhuricore.exception.ResourceNotFoundException;
import zw.co.macheyo.mhuricore.model.Inventory;
import zw.co.macheyo.mhuricore.model.Product;
import zw.co.macheyo.mhuricore.model.PurchaseOrderItem;
import zw.co.macheyo.mhuricore.service.inventory.InventoryService;
import zw.co.macheyo.mhuricore.service.purchaseorder.PurchaseOrderService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Kudzai.Macheyo
 */
@Slf4j
@Service
public class PurchaseOrderItemServiceImpl implements PurchaseOrderItemService{
    @Autowired
    PurchaseOrderItemRepository repository;
    @Autowired
    InventoryService inventoryService;
    @Autowired
    PurchaseOrderService purchaseOrderService;
    @Autowired
    PurchaseOrderItemModelAssembler assembler;

    @Override
    public Optional<PurchaseOrderItem> findById(Object id) {
        return repository.findById((Long) id);
    }

    @Override
    public List<EntityModel<PurchaseOrderItem>> findAll() {
        return repository.findAll()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PurchaseOrderItem> create(PurchaseOrderItem purchaseOrderItem) {
        purchaseOrderItem.setOrder(purchaseOrderService.findByReference(purchaseOrderItem.getOrder().getReference()).orElseThrow(()->new ResourceNotFoundException("Purchase order","reference",purchaseOrderItem.getOrder().getReference())));
        return Optional.of(repository.save(purchaseOrderItem));
    }

    @Override
    public Optional<PurchaseOrderItem> update(PurchaseOrderItem purchaseOrderItem) {
        return repository.findById(purchaseOrderItem.getId()).map(poi->{
            poi.setProduct(purchaseOrderItem.getProduct());
            poi.setOrder(purchaseOrderItem.getOrder());
            poi.setPrice(purchaseOrderItem.getPrice());
            poi.setQuantity(purchaseOrderItem.getQuantity());
            return repository.save(poi);
        });
    }

    @Override
    public void delete(PurchaseOrderItem purchaseOrderItem) {
        repository.delete(purchaseOrderItem);
    }

    @Override
    public List<EntityModel<PurchaseOrderItem>> findByOrderId(Long id) {
        return repository.findByOrderId(id)
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PurchaseOrderItem> delivered(PurchaseOrderItem purchaseOrderItem) {
        return repository.findById(purchaseOrderItem.getId()).map(poi ->{
            Optional<Inventory> inventory = inventoryService.findByProduct(poi.getProduct());
            if(inventory.isPresent()){
                Inventory i = inventory.get();
                i.setQuantity(i.getQuantity() + poi.getQuantity());
                inventoryService.update(i);
            }else{
                Inventory i = new Inventory();
                i.setProduct(poi.getProduct());
                i.setInventoryStatus(InventoryStatus.ACTIVE);
                i.setQuantity(poi.getQuantity());
                inventoryService.create(i);
            }
            poi.setDelivered(true);
            return repository.save(poi);
        });
    }
    @Override
    public void adjust(Product product, Double quantity){
        List<PurchaseOrderItem> purchaseOrderItems = repository.findAllByProduct(product, Sort.by(Sort.Direction.ASC, "createdDate"));
        double dispatched = 0.0;
        while(dispatched<quantity){
            for(PurchaseOrderItem purchaseOrderItem: purchaseOrderItems){
                double itemsAvailable = purchaseOrderItem.getQuantity() - purchaseOrderItem.getSold();
                Double sold = purchaseOrderItem.getSold();
                if(itemsAvailable>=quantity-dispatched){
                    sold+=quantity-dispatched;
                    dispatched+=quantity;
                    purchaseOrderItem.setSold(sold);
                    repository.save(purchaseOrderItem);
                    break;
                }else {
                    sold+=itemsAvailable;
                    dispatched+=itemsAvailable;
                    purchaseOrderItem.setSold(sold);
                    repository.save(purchaseOrderItem);
                }

            }
        }
    }
}
