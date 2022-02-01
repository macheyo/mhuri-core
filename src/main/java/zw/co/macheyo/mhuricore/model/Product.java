package zw.co.macheyo.mhuricore.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import java.util.Set;

@Audited
@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Product extends BaseEntity{
    @Size(max=255)
    private String name;
    @OneToMany(mappedBy = "product")
    @JsonBackReference
    @ToString.Exclude
    private Set<Item> orderItems;
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    Set<Inventory> order_runs;
    private double price;

}
