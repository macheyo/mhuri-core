package zw.co.macheyo.mhuricore.service.purchaseorderitem;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.macheyo.mhuricore.model.Product;
import zw.co.macheyo.mhuricore.model.PurchaseOrderItem;

import java.util.List;

/**
 * @author Kudzai.Macheyo
 */
public interface PurchaseOrderItemRepository extends JpaRepository<PurchaseOrderItem, Long> {
    List<PurchaseOrderItem> findByOrderId(Long id);

    List<PurchaseOrderItem> findAllByProduct(Product product, Sort createdDate);
}
