package zw.co.macheyo.mhuricore.service.saleorderitem;

import lombok.Data;
import zw.co.macheyo.mhuricore.model.Product;
import zw.co.macheyo.mhuricore.model.SaleOrder;

/**
 * @author Kudzai.Macheyo
 */
@Data
public class SaleOrderItemDTO {
    private Long id;
    private SaleOrder order;
    private Product product;
    private boolean isDelivered;
    private Double quantity;
}
