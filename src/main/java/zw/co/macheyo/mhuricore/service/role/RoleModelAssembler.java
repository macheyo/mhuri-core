package zw.co.macheyo.mhuricore.service.role;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import zw.co.macheyo.mhuricore.model.Role;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RoleModelAssembler implements RepresentationModelAssembler<Role, EntityModel<Role>> {
    @Autowired
    ModelMapper modelMapper;

    @NonNull
    @Override
    public EntityModel<Role> toModel(Role entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(RoleController.class).getById(entity.getId())).withSelfRel(),
                linkTo(methodOn(RoleController.class).update(modelMapper.map(entity, RoleDTO.class))).withRel("update"),
                linkTo(methodOn(RoleController.class).delete(modelMapper.map(entity, RoleDTO.class))).withRel("delete"),
                linkTo(methodOn(RoleController.class).list()).withRel("list"));
    }
    @NonNull
    @Override
    public CollectionModel<EntityModel<Role>> toCollectionModel(Iterable<? extends Role> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
