package zw.co.macheyo.mhuricore.service.usergroup;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.macheyo.mhuricore.exception.BusinessException;
import zw.co.macheyo.mhuricore.exception.ResourceNotFoundException;
import zw.co.macheyo.mhuricore.model.UserGroup;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Kudzai.Macheyo
 */
@RestController
@RequestMapping("/user-group")
public class UserGroupController {
    @Autowired
    UserGroupService service;
    @Autowired
    UserGroupModelAssembler assembler;
    @Autowired
    ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<EntityModel<UserGroup>> create(@Valid @RequestBody UserGroupDTO userGroup) {
        EntityModel<UserGroup> entityModel = assembler.toModel(service.create(modelMapper.map(userGroup,UserGroup.class)).orElseThrow(()->new BusinessException("An error occurred creating user-group")));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/list")
    public CollectionModel<EntityModel<UserGroup>> list() {
        return CollectionModel.of(service.findAll(), linkTo(methodOn(UserGroupController.class).list()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UserGroup>> getById(@PathVariable Long id) {
        EntityModel<UserGroup> entityModel = assembler.toModel(service.findById(id).orElseThrow(()->new ResourceNotFoundException("User group","id",id)));
        return ResponseEntity
                .ok(entityModel);
    }

    @PutMapping
    public ResponseEntity<EntityModel<UserGroup>> update(@Valid @RequestBody UserGroupDTO userGroup){
        EntityModel<UserGroup> entityModel = assembler.toModel(service.update(modelMapper.map(userGroup, UserGroup.class)).orElseThrow(()-> new BusinessException("Error occurred updating user group")));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping
    public ResponseEntity<EntityModel<UserGroup>> delete(@Valid @RequestBody UserGroupDTO userGroup){
        service.delete(modelMapper.map(userGroup,UserGroup.class));
        return ResponseEntity.noContent().build();
    }
}
