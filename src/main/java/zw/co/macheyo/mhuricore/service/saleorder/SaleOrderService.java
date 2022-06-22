package zw.co.macheyo.mhuricore.service.saleorder;

import zw.co.macheyo.mhuricore.common.AppService;
import zw.co.macheyo.mhuricore.model.SaleOrder;

import java.util.Optional;

/**
 * @author Kudzai.Macheyo
 */
public interface SaleOrderService extends AppService<SaleOrder> {
    Optional<SaleOrder> complete(SaleOrder saleOrder);

    Optional<SaleOrder> cancel(SaleOrder saleOrder);

    Optional<SaleOrder> findByReference(String reference);
}
