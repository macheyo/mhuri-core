package zw.co.macheyo.mhuricore.service;

import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import zw.co.macheyo.mhuricore.model.Order;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
@Component
public interface OrderService {
    Order save(Order order, HttpServletRequest httpServletRequest);

    Order update(Long id, Order order, HttpServletRequest httpServletRequest);

    Order complete(Long id, HttpServletRequest httpServletRequest);

    List<EntityModel<Order>> findAll();

    Order findById(Long id);

    void deleteById(Long id);
}
