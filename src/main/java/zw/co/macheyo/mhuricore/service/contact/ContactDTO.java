package zw.co.macheyo.mhuricore.service.contact;

import lombok.Data;
import zw.co.macheyo.mhuricore.enums.ContactType;

/**
 * @author Kudzai.Macheyo
 */
@Data
public class ContactDTO {
    private Long id;
    private String name;
    private String msisdn;
    private ContactType contactType;
}
