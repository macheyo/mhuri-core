package zw.co.macheyo.mhuricore.service.saleorder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.stereotype.Service;
import zw.co.macheyo.mhuricore.enums.OrderStatus;
import zw.co.macheyo.mhuricore.model.SaleOrder;
import zw.co.macheyo.mhuricore.model.SaleOrderItem;
import zw.co.macheyo.mhuricore.service.doubleentry.DoubleEntryService;
import zw.co.macheyo.mhuricore.service.purchaseorderitem.PurchaseOrderItemService;
import zw.co.macheyo.mhuricore.service.saleorderitem.SaleOrderItemRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Kudzai.Macheyo
 */
@Slf4j
@Service
public class SaleOrderServiceImpl implements SaleOrderService{
    @Autowired
    SaleOrderRepository repository;
    @Autowired
    SaleOrderItemRepository saleOrderItemRepository;
    @Autowired
    DoubleEntryService doubleEntryService;
    @Autowired
    PurchaseOrderItemService purchaseOrderItemService;
    @Autowired
    SaleOrderModelAssembler assembler;

    @Override
    public Optional<SaleOrder> findById(Object id) {
        return repository.findById((Long) id);
    }

    @Override
    public List<EntityModel<SaleOrder>> findAll() {
        return repository.findAll()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SaleOrder> create(SaleOrder saleOrder) {
        return Optional.of(repository.save(saleOrder));
    }

    @Override
    public Optional<SaleOrder> update(SaleOrder saleOrder) {
        return repository.findById(saleOrder.getId()).map(so->{
            so.setContact(saleOrder.getContact());
            so.setReference(saleOrder.getReference());
            so.setStatus(saleOrder.getStatus());
            return repository.save(so);
        });
    }

    @Override
    public void delete(SaleOrder saleOrder) {
        repository.delete(saleOrder);
    }

    @Override
    public Optional<SaleOrder> complete(SaleOrder saleOrder) {
        return repository.findById(saleOrder.getId()).map(so->{
            EntityModel<SaleOrder> entityModel = assembler.toModel(so);
            List<SaleOrderItem> saleOrderItems = saleOrderItemRepository.findByOrderId(so.getId());
            doubleEntryService.addEntries("cash",
                    "sales",
                    entityModel.getRequiredLink(IanaLinkRelations.SELF).toString(),
                    saleOrderTotal(saleOrderItems));
            adjustPurchaseOrders(saleOrderItems);
            so.setStatus(OrderStatus.COMPLETE);
            return repository.save(so);
        });
    }

    @Override
    public Optional<SaleOrder> cancel(SaleOrder saleOrder) {
        return repository.findById(saleOrder.getId()).map(so->{
            so.setStatus(OrderStatus.CANCELLED);
            return repository.save(so);
        });
    }

    @Override
    public Optional<SaleOrder> findByReference(String reference) {
        return repository.findByReference(reference);
    }

    private Double saleOrderTotal(List<SaleOrderItem> saleOrderItems) {
        double totalSalePrice = 0;
        for(SaleOrderItem saleOrderItem: saleOrderItems){
            totalSalePrice += saleOrderItem.getProduct().getPrice()*saleOrderItem.getQuantity();
        }
        return totalSalePrice;
    }

    private void adjustPurchaseOrders(List<SaleOrderItem> saleOrderItems){
        for (SaleOrderItem saleOrderItem: saleOrderItems){
            purchaseOrderItemService.adjust(saleOrderItem.getProduct(),saleOrderItem.getQuantity());
        }
    }
}
