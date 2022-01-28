package zw.co.macheyo.mhuricore.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
@Getter
@Setter
@NoArgsConstructor
@Audited
public class Inventory {
    @EmbeddedId
    InventoryKey id;
    @JsonIgnore
    @ManyToOne
    @MapsId("purchaseId")
    @JoinColumn(name = "purchase_id")
    Purchase purchase;
    @JsonIgnore
    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    Product product;
    double sellingPrice;
    double purchasePrice;
    double quantity;
    double availableQuantity;
    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    @JsonIgnore
    private LocalDateTime createdDate = LocalDateTime.now();

    public Inventory(Purchase purchase, Product product, double sellingPrice, double purchasePrice, double quantity) {
        this.id = new InventoryKey(purchase.getId(),product.getId());
        this.purchase = purchase;
        this.product = product;
        this.sellingPrice = sellingPrice;
        this.purchasePrice = purchasePrice;
        this.quantity = quantity;
        this.availableQuantity =  quantity;

    }
}
