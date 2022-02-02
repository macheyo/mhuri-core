package zw.co.macheyo.mhuricore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zw.co.macheyo.mhuricore.exception.ResourceNotFoundException;
import zw.co.macheyo.mhuricore.model.Account;
import zw.co.macheyo.mhuricore.model.AccountTransaction;
import zw.co.macheyo.mhuricore.model.Transaction;
import zw.co.macheyo.mhuricore.repository.AccountRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
@Service
public class DoubleEntryServiceImpl implements DoubleEntryService{
    @Autowired
    AccountRepository accountRepository;
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void record(String accountToDebit, String accountToCredit, String reference, Double amount) {
        Account debitAccount = findAccountByName(accountToDebit);
        Account creditAccount = findAccountByName(accountToCredit);
        Transaction transaction = new Transaction();
        transaction.setReference(reference);
        entityManager.persist(transaction);
        AccountTransaction debitAccountTransaction = new AccountTransaction(debitAccount,transaction,amount);
        entityManager.persist(debitAccountTransaction);
        AccountTransaction creditAccountTransaction = new AccountTransaction(creditAccount,transaction,-amount);
        entityManager.persist(creditAccountTransaction);
        entityManager.close();
    }

    private Account findAccountByName(String name){
        return accountRepository.findByName(name).orElseThrow(()->new ResourceNotFoundException("account","name",name));
    }
}
