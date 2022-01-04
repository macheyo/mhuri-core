package zw.co.macheyo.mhuricore.service;

import org.springframework.hateoas.EntityModel;
import zw.co.macheyo.mhuricore.model.Account;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface AccountService{
    Account save(Account account, HttpServletRequest httpServletRequest);

    Account update(Long id, Account account, HttpServletRequest httpServletRequest);

    List<EntityModel<Account>> findAll();

    Account findById(Long id);
}