package zw.co.macheyo.mhuricore.modelAssembler;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import zw.co.macheyo.mhuricore.controller.OrderController;
import zw.co.macheyo.mhuricore.model.Item;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ItemModelAssembler implements RepresentationModelAssembler<Item, EntityModel<Item>> {
    @NonNull
    @Override
    public EntityModel<Item> toModel(Item entity) {
        return EntityModel.of(entity, linkTo(methodOn(OrderController.class).list()).withRel("order items"));
    }
    @NonNull
    @Override
    public CollectionModel<EntityModel<Item>> toCollectionModel(Iterable<? extends Item> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
