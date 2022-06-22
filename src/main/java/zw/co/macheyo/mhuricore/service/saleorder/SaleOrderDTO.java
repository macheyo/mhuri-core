package zw.co.macheyo.mhuricore.service.saleorder;

import lombok.Data;
import zw.co.macheyo.mhuricore.enums.OrderStatus;
import zw.co.macheyo.mhuricore.model.Contact;

/**
 * @author Kudzai.Macheyo
 */
@Data
public class SaleOrderDTO {
    private Long id;
    private String reference;
    private Contact contact;
    private OrderStatus status;
}
