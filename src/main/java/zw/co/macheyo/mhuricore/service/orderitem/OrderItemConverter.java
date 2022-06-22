package zw.co.macheyo.mhuricore.service.orderitem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zw.co.macheyo.mhuricore.enums.OrderItemType;
import zw.co.macheyo.mhuricore.model.PurchaseOrderItem;
import zw.co.macheyo.mhuricore.model.SaleOrderItem;
import zw.co.macheyo.mhuricore.service.purchaseorderitem.PurchaseOrderItemService;
import zw.co.macheyo.mhuricore.service.saleorderitem.SaleOrderItemService;

/**
 * @author Kudzai.Macheyo
 */
@Component
public class OrderItemConverter {
    @Autowired
    PurchaseOrderItemService purchaseOrderItemService;
    @Autowired
    SaleOrderItemService saleOrderItemService;

    public OrderItem purchaseOrderItemToOrderItem(PurchaseOrderItem purchaseOrderItem){
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderItemType(OrderItemType.PURCHASE);
        orderItem.setProduct(purchaseOrderItem.getProduct());
        orderItem.setQuantity(purchaseOrderItem.getQuantity());
        orderItem.setPrice(purchaseOrderItem.getPrice());
        return orderItem;
    }

    public OrderItem saleOrderItemToOrderItem(SaleOrderItem saleOrderItem){
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderItemType(OrderItemType.SALE);
        orderItem.setProduct(saleOrderItem.getProduct());
        orderItem.setQuantity(saleOrderItem.getQuantity());
        orderItem.setPrice(saleOrderItem.getProduct().getPrice());
        return orderItem;
    }

}
