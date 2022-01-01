package zw.co.macheyo.mhuricore.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Set;

@Audited
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "orders")
public class Order extends BaseEntity{
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private Customer customer;
    @OneToMany(mappedBy = "order")
    @JsonBackReference
    @ToString.Exclude
    private Set<Item> items;
    private Status status;

}
