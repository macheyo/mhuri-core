package zw.co.macheyo.mhuricore.service.role;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.macheyo.mhuricore.exception.BusinessException;
import zw.co.macheyo.mhuricore.exception.ResourceNotFoundException;
import zw.co.macheyo.mhuricore.model.Role;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Kudzai.Macheyo
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    RoleService service;
    @Autowired
    RoleModelAssembler assembler;
    @Autowired
    ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<EntityModel<Role>> create(@Valid @RequestBody RoleDTO role) {
        EntityModel<Role> entityModel = assembler.toModel(service.create(modelMapper.map(role, Role.class)).orElseThrow(()->new BusinessException("An error occurred creating role")));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/list")
    public CollectionModel<EntityModel<Role>> list() {
        return CollectionModel.of(service.findAll(), linkTo(methodOn(RoleController.class).list()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Role>> getById(@PathVariable Long id) {
        EntityModel<Role> entityModel = assembler.toModel(service.findById(id).orElseThrow(()->new ResourceNotFoundException(Role.class.getSimpleName(),"id",id)));
        return ResponseEntity
                .ok(entityModel);
    }

    @PutMapping
    public ResponseEntity<EntityModel<Role>> update(@Valid @RequestBody RoleDTO role){
        EntityModel<Role> entityModel = assembler.toModel(service.update(modelMapper.map(role, Role.class)).orElseThrow(()-> new BusinessException("Error occurred updating role")));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping
    public ResponseEntity<EntityModel<Role>> delete(@Valid @RequestBody RoleDTO role){
        service.delete(modelMapper.map(role, Role.class));
        return ResponseEntity.noContent().build();
    }
}
