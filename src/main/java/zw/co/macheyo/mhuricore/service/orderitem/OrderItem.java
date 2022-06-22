package zw.co.macheyo.mhuricore.service.orderitem;

import lombok.Data;
import zw.co.macheyo.mhuricore.enums.OrderItemType;
import zw.co.macheyo.mhuricore.model.Product;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author Kudzai.Macheyo
 */
@Data
public class OrderItem {
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Product product;
    private Double price;
    private Double quantity;
    @Enumerated(EnumType.STRING)
    private OrderItemType orderItemType;
}
