package zw.co.macheyo.mhuricore.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import zw.co.macheyo.mhuricore.enums.InventoryStatus;

import javax.persistence.*;

@Audited
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Inventory extends BaseEntity{
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @OneToOne(optional = false)
    private Product product;
    @Enumerated(EnumType.STRING)
    private InventoryStatus inventoryStatus;
    private Double quantity;
    private String assignee;

}
