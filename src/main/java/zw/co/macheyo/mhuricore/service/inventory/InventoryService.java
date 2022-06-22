package zw.co.macheyo.mhuricore.service.inventory;

import org.springframework.stereotype.Component;
import zw.co.macheyo.mhuricore.common.AppService;
import zw.co.macheyo.mhuricore.model.Inventory;
import zw.co.macheyo.mhuricore.model.Product;

import java.util.Optional;

@Component
public interface InventoryService extends AppService<Inventory> {
    Optional<Inventory> findByProduct(Product product);

    boolean isAvailable(Product product, Double quantity);
}
