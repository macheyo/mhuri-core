package zw.co.macheyo.mhuricore.service.purchaseorder;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.macheyo.mhuricore.model.PurchaseOrder;

import java.util.Optional;

/**
 * @author Kudzai.Macheyo
 */
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
    Optional<PurchaseOrder> findByReference(String reference);
}
