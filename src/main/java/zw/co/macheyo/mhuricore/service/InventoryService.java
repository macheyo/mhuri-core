package zw.co.macheyo.mhuricore.service;

import org.springframework.stereotype.Component;
import zw.co.macheyo.mhuricore.model.Inventory;
import zw.co.macheyo.mhuricore.model.InventoryDto;
import zw.co.macheyo.mhuricore.model.Product;

import javax.servlet.http.HttpServletRequest;

@Component
public interface InventoryService {
    Inventory record(Long purchaseId, Long productId, InventoryDto inventory, HttpServletRequest httpServletRequest);
    void adjust(Product product, double quantity);
}
