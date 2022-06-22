package zw.co.macheyo.mhuricore.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import zw.co.macheyo.mhuricore.enums.OrderStatus;

import javax.persistence.*;

@Audited
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaleOrder extends BaseEntity{
    @Column(unique = true, nullable = false)
    private String reference;
    @JoinColumn(name = "contact_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Contact contact;
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.IN_PROGRESS;

}
