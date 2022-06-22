package zw.co.macheyo.mhuricore.service.purchaseorderitem;

import org.springframework.hateoas.EntityModel;
import zw.co.macheyo.mhuricore.common.AppService;
import zw.co.macheyo.mhuricore.model.Product;
import zw.co.macheyo.mhuricore.model.PurchaseOrderItem;

import java.util.List;
import java.util.Optional;

/**
 * @author Kudzai.Macheyo
 */
public interface PurchaseOrderItemService extends AppService<PurchaseOrderItem> {
    List<EntityModel<PurchaseOrderItem>> findByOrderId(Long id);

    Optional<PurchaseOrderItem> delivered(PurchaseOrderItem purchaseOrderItem);

    void adjust(Product product, Double quantity);
}
