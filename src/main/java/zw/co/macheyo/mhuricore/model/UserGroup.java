package zw.co.macheyo.mhuricore.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zw.co.macheyo.mhuricore.enums.UserGroupStatus;

import javax.persistence.*;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Kudzai.Macheyo
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserGroup extends BaseEntity {
    @Basic(optional = false)
    @Column(name = "name", unique = true)
    private String name;
    @Column(name = "description")
    private String description;
    @Enumerated(EnumType.STRING)
    @Basic(optional = false)
    @Column(name = "status",columnDefinition = "varchar(50) default 'ACTIVE'")
    private UserGroupStatus status;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "group_role_mapping",
            joinColumns = {@JoinColumn(name = "group_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<Role> roles = new TreeSet<>();
}
