package zw.co.macheyo.mhuricore.service.inventory;

import lombok.Data;
import zw.co.macheyo.mhuricore.enums.InventoryStatus;
import zw.co.macheyo.mhuricore.model.Product;

/**
 * @author Kudzai.Macheyo
 */
@Data
public class InventoryDTO {
    private Long id;
    private Product product;
    private InventoryStatus inventoryStatus;
    private Double quantity;
    private String assignee;
}
