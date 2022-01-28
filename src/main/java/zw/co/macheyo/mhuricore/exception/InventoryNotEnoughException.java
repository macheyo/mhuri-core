package zw.co.macheyo.mhuricore.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@EqualsAndHashCode(callSuper = true)
@Data
@ResponseStatus(HttpStatus.NOT_FOUND)
public class InventoryNotEnoughException extends RuntimeException {

    private final String productName;
    private final double quantityAvailable;

    public InventoryNotEnoughException(String productName, double availableQuantity) {
        super(String.format("Not enough inventory for product with name '%s' only %s units remain", productName, availableQuantity));
        this.productName = productName;
        this.quantityAvailable = availableQuantity;
    }

}
