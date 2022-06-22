package zw.co.macheyo.mhuricore.service.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import zw.co.macheyo.mhuricore.model.Account;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService{
    @Autowired
    AccountRepository repository;
    @Autowired
    AccountModelAssembler assembler;


    @Override
    public Optional<Account> findById(Object id) {
        return repository.findById((Long) id);
    }

    @Override
    public List<EntityModel<Account>> findAll() {
        return repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Account> create(Account account) {
        return Optional.of(repository.save(account));
    }

    @Override
    public Optional<Account> update(Account account) {
        return repository.findById(account.getId()).map(a->{
            a.setName(account.getName());
            a.setAccountType(account.getAccountType());
            return repository.save(a);
        });
    }

    @Override
    public void delete(Account account) {
        repository.delete(account);
    }
}
