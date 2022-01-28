package zw.co.macheyo.mhuricore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.macheyo.mhuricore.model.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
