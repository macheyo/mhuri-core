package zw.co.macheyo.mhuricore.service;

import org.springframework.hateoas.EntityModel;
import zw.co.macheyo.mhuricore.model.Item;
import zw.co.macheyo.mhuricore.model.Order;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ItemService {
    Item save(Long orderId, Item item, HttpServletRequest httpServletRequest);

    List<EntityModel<Item>> findAll();

    Item findById(Long id);

    Item update(Long orderId, Long itemId, Item item, HttpServletRequest httpServletRequest);
}
