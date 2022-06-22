package zw.co.macheyo.mhuricore.service.orderitem;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import zw.co.macheyo.mhuricore.model.PurchaseOrderItem;
import zw.co.macheyo.mhuricore.model.SaleOrderItem;
import zw.co.macheyo.mhuricore.service.purchaseorderitem.PurchaseOrderItemRepository;
import zw.co.macheyo.mhuricore.service.saleorderitem.SaleOrderItemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Kudzai.Macheyo
 */
@Slf4j
@Service
public class OrderItemServiceImpl implements OrderItemService{
    @Autowired
    PurchaseOrderItemRepository purchaseOrderItemRepository;

    @Autowired
    SaleOrderItemRepository saleOrderItemRepository;
    @Autowired
    OrderItemConverter orderItemConverter;
    @Autowired
    OrderItemModelAssembler assembler;

    @Override
    public Optional<OrderItem> findById(Object id) {
        return Optional.empty();
    }

    @Override
    public List<EntityModel<OrderItem>> findAll() {
        List<PurchaseOrderItem> purchaseOrderItems = purchaseOrderItemRepository.findAll();
        List<SaleOrderItem> saleOrderItems = saleOrderItemRepository.findAll();
        List<OrderItem> orderItems = new ArrayList<>();
        for(PurchaseOrderItem purchaseOrderItem: purchaseOrderItems){
            orderItems.add(orderItemConverter.purchaseOrderItemToOrderItem(purchaseOrderItem));
        }
        for (SaleOrderItem saleOrderItem: saleOrderItems){
            orderItems.add(orderItemConverter.saleOrderItemToOrderItem(saleOrderItem));
        }
        return orderItems.stream().map(assembler::toModel).collect(Collectors.toList());
    }

    @Override
    public Optional<OrderItem> create(OrderItem orderItem) {
        return Optional.empty();
    }

    @Override
    public Optional<OrderItem> update(OrderItem orderItem) {
        return Optional.empty();
    }

    @Override
    public void delete(OrderItem orderItem) {

    }
}
