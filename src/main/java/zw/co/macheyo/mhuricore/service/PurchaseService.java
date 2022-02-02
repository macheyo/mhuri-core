package zw.co.macheyo.mhuricore.service;

import org.springframework.hateoas.EntityModel;
import org.springframework.transaction.annotation.Transactional;
import zw.co.macheyo.mhuricore.model.Order;
import zw.co.macheyo.mhuricore.model.Purchase;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PurchaseService {

    Purchase save(Purchase purchase);

    Purchase update(Long id, Purchase product);

    List<EntityModel<Purchase>> findAll();

    Purchase findById(Long id);


    @Transactional
    Purchase complete(Long id);

    void deleteById(Long id);

    Purchase cancel(Long id);
}
