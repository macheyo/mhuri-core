package zw.co.macheyo.mhuricore.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import zw.co.macheyo.mhuricore.enums.AccountType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Audited
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account extends BaseEntity{
    @Column(nullable = false, unique = true)
    private String name;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
}
