package zw.co.macheyo.mhuricore.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.Set;

@Audited
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "customers")
public class Customer extends BaseEntity{
    @Size(max=255)
    private String name;
    @Column(unique = true)
    private String msisdn;
    @OneToMany(mappedBy = "customer")
    @JsonBackReference
    @ToString.Exclude
    private Set<Order> orders;

}
