package zw.co.macheyo.mhuricore.service.purchaseorder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.stereotype.Service;
import zw.co.macheyo.mhuricore.enums.OrderStatus;
import zw.co.macheyo.mhuricore.model.PurchaseOrder;
import zw.co.macheyo.mhuricore.model.PurchaseOrderItem;
import zw.co.macheyo.mhuricore.service.doubleentry.DoubleEntryService;
import zw.co.macheyo.mhuricore.service.purchaseorderitem.PurchaseOrderItemRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Kudzai.Macheyo
 */
@Slf4j
@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService{
    @Autowired
    PurchaseOrderRepository repository;
    @Autowired
    PurchaseOrderModelAssembler assembler;

    @Autowired
    PurchaseOrderItemRepository purchaseOrderItemRepository;
    @Autowired
    DoubleEntryService doubleEntryService;

    @Override
    public Optional<PurchaseOrder> findById(Object id) {
        return repository.findById((Long) id);
    }

    @Override
    public List<EntityModel<PurchaseOrder>> findAll() {
        return repository.findAll()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PurchaseOrder> create(PurchaseOrder purchaseOrder) {
        return Optional.of(repository.save(purchaseOrder));
    }

    @Override
    public Optional<PurchaseOrder> update(PurchaseOrder purchaseOrder) {
        return repository.findById(purchaseOrder.getId()).map(po->{
            po.setReference(purchaseOrder.getReference());
            return repository.save(po);
        });
    }

    @Override
    public void delete(PurchaseOrder purchaseOrder) {
        repository.delete(purchaseOrder);
    }

    @Override
    public Optional<PurchaseOrder> findByReference(String reference) {
        return repository.findByReference(reference);
    }

    @Override
    public Optional<PurchaseOrder> complete(PurchaseOrder purchaseOrder) {
        return repository.findById(purchaseOrder.getId()).map(po->{
            EntityModel<PurchaseOrder> entityModel = assembler.toModel(po);
            doubleEntryService.addEntries("purchases",
                    "cash",
                    entityModel.getRequiredLink(IanaLinkRelations.SELF).toString(),
                    purchaseOrderTotal(purchaseOrderItemRepository.findByOrderId(po.getId())));
            po.setStatus(OrderStatus.COMPLETE);
            return repository.save(po);
        });
    }

    @Override
    public Optional<PurchaseOrder> cancel(PurchaseOrder purchaseOrder) {
        return repository.findById(purchaseOrder.getId()).map(po->{
            po.setStatus(OrderStatus.CANCELLED);
            return repository.save(po);
        });
    }

    private Double purchaseOrderTotal(List<PurchaseOrderItem> purchaseOrderItems) {
        double totalPurchasePrice = 0;
        for(PurchaseOrderItem purchaseOrderItem: purchaseOrderItems){
            totalPurchasePrice += purchaseOrderItem.getPrice()*purchaseOrderItem.getQuantity();
        }
        return totalPurchasePrice;
    }
}
