package zw.co.macheyo.mhuricore.service;

import org.springframework.hateoas.EntityModel;
import zw.co.macheyo.mhuricore.model.Customer;
import zw.co.macheyo.mhuricore.model.Order;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order save(Order order, HttpServletRequest httpServletRequest);

    Order update(Long id, Order order, HttpServletRequest httpServletRequest);

    List<EntityModel<Order>> findAll();

    Order findById(Long id);

}
