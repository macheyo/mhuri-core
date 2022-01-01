package zw.co.macheyo.mhuricore.service;

import org.springframework.hateoas.EntityModel;
import zw.co.macheyo.mhuricore.model.Customer;
import zw.co.macheyo.mhuricore.model.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CustomerService {
    Customer save(Customer customer, HttpServletRequest httpServletRequest);

    Customer update(Long id, Customer customer, HttpServletRequest httpServletRequest);

    List<EntityModel<Customer>> findAll();

    Customer findById(Long id);
}
