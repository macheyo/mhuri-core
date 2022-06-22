package zw.co.macheyo.mhuricore.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import zw.co.macheyo.mhuricore.enums.OrderStatus;

import javax.persistence.*;
import java.util.Set;

@Audited
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
@Getter
@Setter
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "orders")
public class Order extends BaseEntity{

    private Contact contact;
    @OneToMany(mappedBy = "order")
    @JsonManagedReference
    @ToString.Exclude
    private Set<Item> items;
    private OrderStatus orderStatus = OrderStatus.IN_PROGRESS;

}
