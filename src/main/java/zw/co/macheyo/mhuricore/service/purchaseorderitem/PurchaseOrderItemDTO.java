package zw.co.macheyo.mhuricore.service.purchaseorderitem;

import lombok.Data;
import zw.co.macheyo.mhuricore.model.Product;
import zw.co.macheyo.mhuricore.model.PurchaseOrder;

/**
 * @author Kudzai.Macheyo
 */
@Data
public class PurchaseOrderItemDTO {
    private Long id;
    private PurchaseOrder order;
    private Product product;
    private boolean isDelivered;
    private Double price;
    private Double quantity;
    private Double sold;
}
