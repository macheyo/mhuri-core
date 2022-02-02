package zw.co.macheyo.mhuricore.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import java.util.Set;

@Audited
@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
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
    @OneToMany(mappedBy = "contact")
    @JsonBackReference
    @ToString.Exclude
    private Set<Purchase> purchases;


}
