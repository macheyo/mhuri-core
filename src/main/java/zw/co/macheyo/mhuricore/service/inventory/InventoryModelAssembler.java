package zw.co.macheyo.mhuricore.service.inventory;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import zw.co.macheyo.mhuricore.model.Inventory;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class InventoryModelAssembler implements RepresentationModelAssembler<Inventory, EntityModel<Inventory>> {
    @Autowired
    ModelMapper modelMapper;
    @NonNull
    @Override
    public EntityModel<Inventory> toModel(Inventory entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(InventoryController.class).getById(entity.getId())).withSelfRel(),
                linkTo(methodOn(InventoryController.class).update(modelMapper.map(entity,InventoryDTO.class))).withRel("update"),
                linkTo(methodOn(InventoryController.class).delete(modelMapper.map(entity, InventoryDTO.class))).withRel("delete"),
                linkTo(methodOn(InventoryController.class).list()).withRel("list"));
    }
    @NonNull
    @Override
    public CollectionModel<EntityModel<Inventory>> toCollectionModel(Iterable<? extends Inventory> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
