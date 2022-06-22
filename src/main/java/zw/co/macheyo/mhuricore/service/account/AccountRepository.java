package zw.co.macheyo.mhuricore.service.account;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.macheyo.mhuricore.model.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByName(String name);
}
