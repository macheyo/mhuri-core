package zw.co.macheyo.mhuricore.service;

import org.springframework.hateoas.EntityModel;
import zw.co.macheyo.mhuricore.model.Transaction;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface TransactionService {
    Transaction save(Transaction transaction);

    Transaction update(Long id, Transaction transaction);

    List<EntityModel<Transaction>> findAll();

    Transaction findById(Long id);
}
