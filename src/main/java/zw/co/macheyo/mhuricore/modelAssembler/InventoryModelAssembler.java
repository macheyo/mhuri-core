package zw.co.macheyo.mhuricore.modelAssembler;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import zw.co.macheyo.mhuricore.controller.InventoryController;
import zw.co.macheyo.mhuricore.model.Inventory;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class InventoryModelAssembler implements RepresentationModelAssembler<Inventory, EntityModel<Inventory>> {
    @NonNull
    @Override
    public EntityModel<Inventory> toModel(Inventory entity) {
        return EntityModel.of(entity, linkTo(methodOn(InventoryController.class).list()).withRel("inventories"));
    }
    @NonNull
    @Override
    public CollectionModel<EntityModel<Inventory>> toCollectionModel(Iterable<? extends Inventory> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
