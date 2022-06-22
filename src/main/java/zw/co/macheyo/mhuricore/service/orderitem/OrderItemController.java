package zw.co.macheyo.mhuricore.service.orderitem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Kudzai.Macheyo
 */
@RestController
@RequestMapping("/order-item")
public class OrderItemController {
    @Autowired
    OrderItemService service;

    @GetMapping("/list")
    public CollectionModel<EntityModel<OrderItem>> list() {
        return CollectionModel.of(service.findAll(), linkTo(methodOn(OrderItemController.class).list()).withSelfRel());
    }
}
