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
import javax.validation.constraints.Size;
import java.util.Set;

@Audited
@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Contact extends BaseEntity{
    @Size(max=255)
    private String name;
    @Column(unique = true)
    private String msisdn;
    private ContactType contactType;
    @OneToMany(mappedBy = "contact")
    @JsonBackReference
    @ToString.Exclude
    private Set<Order> orders;

}
