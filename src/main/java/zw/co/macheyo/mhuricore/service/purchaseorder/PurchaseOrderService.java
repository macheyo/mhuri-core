package zw.co.macheyo.mhuricore.service.purchaseorder;

import zw.co.macheyo.mhuricore.common.AppService;
import zw.co.macheyo.mhuricore.model.PurchaseOrder;

import java.util.Optional;

/**
 * @author Kudzai.Macheyo
 */
public interface PurchaseOrderService extends AppService<PurchaseOrder> {
    Optional<PurchaseOrder> findByReference(String reference);

    Optional<PurchaseOrder> complete(PurchaseOrder purchaseOrder);

    Optional<PurchaseOrder> cancel(PurchaseOrder purchaseOrder);
}
