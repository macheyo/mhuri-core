package zw.co.macheyo.mhuricore.model;

import lombok.*;
import org.hibernate.envers.Audited;
import zw.co.macheyo.mhuricore.enums.ContactType;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Audited
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Contact extends BaseEntity{
    @Size(max=255)
    private String name;
    @Column(unique = true)
    private String msisdn;
    @Enumerated(EnumType.STRING)
    private ContactType contactType;
}
