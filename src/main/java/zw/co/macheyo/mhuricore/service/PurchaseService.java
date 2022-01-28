package zw.co.macheyo.mhuricore.service;

import org.springframework.hateoas.EntityModel;
import org.springframework.transaction.annotation.Transactional;
import zw.co.macheyo.mhuricore.model.Order;
import zw.co.macheyo.mhuricore.model.Purchase;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PurchaseService {

    Purchase save(Purchase purchase, HttpServletRequest httpServletRequest);

    Purchase update(Long id, Purchase product, HttpServletRequest httpServletRequest);

    List<EntityModel<Purchase>> findAll();

    Purchase findById(Long id);


    @Transactional
    Purchase close(Long id, HttpServletRequest httpServletRequest);
}
