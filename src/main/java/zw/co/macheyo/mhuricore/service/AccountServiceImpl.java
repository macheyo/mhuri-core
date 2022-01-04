package zw.co.macheyo.mhuricore.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import zw.co.macheyo.mhuricore.exception.ResourceNotFoundException;
import zw.co.macheyo.mhuricore.model.Account;
import zw.co.macheyo.mhuricore.model.Contact;
import zw.co.macheyo.mhuricore.modelAssembler.AccountModelAssembler;
import zw.co.macheyo.mhuricore.modelAssembler.ContactModelAssembler;
import zw.co.macheyo.mhuricore.repository.AccountRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService{
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountModelAssembler assembler;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public Account save(Account account, HttpServletRequest httpServletRequest) {
        account.setCreatedBy(httpServletRequest.getUserPrincipal().getName());
        account.setLastModifiedBy(httpServletRequest.getUserPrincipal().getName());
        return accountRepository.save(modelMapper.map(account, Account.class));
    }

    @Override
    public Account update(Long id, Account account, HttpServletRequest httpServletRequest) {
        return accountRepository.findById(id).map(a->{
                    a.setName(account.getName());
                    a.setLastModifiedBy(httpServletRequest.getUserPrincipal().getName());
                    a.setLastModifiedDate(LocalDateTime.now());
                    return accountRepository.save(a);})
                .orElseThrow(()->new ResourceNotFoundException("account","id",id));
    }

    @Override
    public List<EntityModel<Account>> findAll() {
        return accountRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("account","id",id));
    }
}
