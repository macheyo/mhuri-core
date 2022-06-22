package zw.co.macheyo.mhuricore.service.saleorder;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.macheyo.mhuricore.model.SaleOrder;

import java.util.Optional;

/**
 * @author Kudzai.Macheyo
 */
public interface SaleOrderRepository extends JpaRepository<SaleOrder,Long> {
    Optional<SaleOrder> findByReference(String reference);
}
