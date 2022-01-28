package zw.co.macheyo.mhuricore.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InventoryKey implements Serializable {
    @Column(name = "product_id")
    Long productId;
    @Column(name = "purchase_id")
    Long purchaseId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InventoryKey)) return false;
        InventoryKey that = (InventoryKey) o;
        return Objects.equals(getProductId(), that.getProductId()) && Objects.equals(getPurchaseId(), that.getPurchaseId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductId(), getPurchaseId());
    }
}
