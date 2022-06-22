package zw.co.macheyo.mhuricore.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Audited
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderItem extends BaseEntity{
    @JoinColumn(name = "order_reference", referencedColumnName = "reference")
    @ManyToOne(optional = false)
    private PurchaseOrder order;
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Product product;
    private boolean isDelivered=false;
    private Double price;
    private Double quantity;
    private Double sold=0.0;
}
