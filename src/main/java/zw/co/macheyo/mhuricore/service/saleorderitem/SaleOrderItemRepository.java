package zw.co.macheyo.mhuricore.service.saleorderitem;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.macheyo.mhuricore.model.SaleOrderItem;

import java.util.List;

/**
 * @author Kudzai.Macheyo
 */
public interface SaleOrderItemRepository extends JpaRepository<SaleOrderItem, Long> {
    List<SaleOrderItem> findByOrderId(Long id);
}
