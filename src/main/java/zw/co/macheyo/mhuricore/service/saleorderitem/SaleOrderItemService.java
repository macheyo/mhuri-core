package zw.co.macheyo.mhuricore.service.saleorderitem;

import org.springframework.hateoas.EntityModel;
import zw.co.macheyo.mhuricore.common.AppService;
import zw.co.macheyo.mhuricore.model.SaleOrderItem;

import java.util.List;
import java.util.Optional;

/**
 * @author Kudzai.Macheyo
 */
public interface SaleOrderItemService extends AppService<SaleOrderItem> {
    List<EntityModel<SaleOrderItem>> findByOrderId(Long id);

    Optional<SaleOrderItem> delivered(SaleOrderItem saleOrderItem);
}
