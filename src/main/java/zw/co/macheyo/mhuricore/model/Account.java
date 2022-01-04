package zw.co.macheyo.mhuricore.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Audited
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
@Getter
@Setter
@RequiredArgsConstructor
public class Account extends BaseEntity{
    @Size(max=255)
    @Column(nullable = false, unique = true)
    private String name;
    private AccountType accountType;
    @OneToMany(mappedBy = "account")
    Set<AccountTransaction> transactions;
}
