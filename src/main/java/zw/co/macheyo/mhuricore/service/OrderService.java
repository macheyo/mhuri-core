package zw.co.macheyo.mhuricore.service;

import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import zw.co.macheyo.mhuricore.model.Order;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
@Component
public interface OrderService {
    Order save(Order order);

    Order update(Long id, Order order);

    Order complete(Long id);

    List<EntityModel<Order>> findAll();

    Order findById(Long id);

    void deleteById(Long id);

    Order cancel(Long id);
}
