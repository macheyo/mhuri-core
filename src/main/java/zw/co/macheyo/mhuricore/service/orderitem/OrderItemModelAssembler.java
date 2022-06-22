package zw.co.macheyo.mhuricore.service.orderitem;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderItemModelAssembler implements RepresentationModelAssembler<OrderItem, EntityModel<OrderItem>> {
    @NonNull
    @Override
    public EntityModel<OrderItem> toModel(OrderItem entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(OrderItemController.class).list()).withRel("list"));
    }
    @NonNull
    @Override
    public CollectionModel<EntityModel<OrderItem>> toCollectionModel(Iterable<? extends OrderItem> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
