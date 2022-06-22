package zw.co.macheyo.mhuricore.service.usergroup;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import zw.co.macheyo.mhuricore.model.UserGroup;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Kudzai.Macheyo
 */
@Component
public class UserGroupModelAssembler implements RepresentationModelAssembler<UserGroup, EntityModel<UserGroup>> {
    @Autowired
    ModelMapper modelMapper;
    @NonNull
    @Override
    public EntityModel<UserGroup> toModel(UserGroup entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(UserGroupController.class).getById(entity.getId())).withSelfRel(),
                linkTo(methodOn(UserGroupController.class).update(modelMapper.map(entity, UserGroupDTO.class))).withRel("update"),
                linkTo(methodOn(UserGroupController.class).delete(modelMapper.map(entity, UserGroupDTO.class))).withRel("delete"),
                linkTo(methodOn(UserGroupController.class).list()).withRel("list"));
    }
    @NonNull
    @Override
    public CollectionModel<EntityModel<UserGroup>> toCollectionModel(Iterable<? extends UserGroup> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
