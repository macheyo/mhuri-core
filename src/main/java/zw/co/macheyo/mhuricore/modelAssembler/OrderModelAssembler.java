package zw.co.macheyo.mhuricore.modelAssembler;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import zw.co.macheyo.mhuricore.controller.OrderController;
import zw.co.macheyo.mhuricore.model.Order;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>> {
    @NonNull
    @Override
    public EntityModel<Order> toModel(Order entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(OrderController.class).getById(entity.getId())).withSelfRel(),
                linkTo(methodOn(OrderController.class).list()).withRel("orders"));
    }
    @NonNull
    @Override
    public CollectionModel<EntityModel<Order>> toCollectionModel(Iterable<? extends Order> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
