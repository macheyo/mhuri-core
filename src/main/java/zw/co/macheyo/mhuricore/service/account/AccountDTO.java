package zw.co.macheyo.mhuricore.service.account;

import lombok.Data;
import zw.co.macheyo.mhuricore.enums.AccountType;

/**
 * @author Kudzai.Macheyo
 */
@Data
public class AccountDTO {
    private Long id;
    private String name;
    private AccountType accountType;
}
