package zw.co.macheyo.mhuricore.service.usergroup;

import lombok.Data;
import zw.co.macheyo.mhuricore.enums.UserGroupStatus;
import zw.co.macheyo.mhuricore.model.Role;

import java.util.Set;

/**
 * @author Kudzai.Macheyo
 */
@Data
public class UserGroupDTO {
    private Long id;
    private String name;
    private String description;
    private UserGroupStatus status;
    private Set<Role> roles;
}
