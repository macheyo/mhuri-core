package zw.co.macheyo.mhuricore.service.purchaseorder;

import lombok.Data;
import zw.co.macheyo.mhuricore.enums.OrderStatus;
import zw.co.macheyo.mhuricore.model.Contact;

/**
 * @author Kudzai.Macheyo
 */
@Data
public class PurchaseOrderDTO {
    private Long id;
    private String reference;
    private Contact contact;
    private OrderStatus status;
}
