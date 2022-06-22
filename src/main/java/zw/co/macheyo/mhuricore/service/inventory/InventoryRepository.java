package zw.co.macheyo.mhuricore.service.inventory;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.macheyo.mhuricore.model.Inventory;
import zw.co.macheyo.mhuricore.model.Product;

import java.util.Optional;

/**
 * @author Kudzai.Macheyo
 */
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByProduct(Product product);
}
