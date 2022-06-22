package zw.co.macheyo.mhuricore.service.inventory;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.macheyo.mhuricore.exception.BusinessException;
import zw.co.macheyo.mhuricore.exception.ResourceNotFoundException;
import zw.co.macheyo.mhuricore.model.Inventory;
import zw.co.macheyo.mhuricore.service.product.ProductController;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired
    InventoryService service;
    @Autowired
    InventoryModelAssembler assembler;
    @Autowired
    ModelMapper modelMapper;


    @PostMapping
    public ResponseEntity<EntityModel<Inventory>> create(@Valid @RequestBody InventoryDTO inventory){
        EntityModel<Inventory> entityModel = assembler.toModel(service.create(modelMapper.map(inventory, Inventory.class)).orElseThrow(()->new BusinessException("An error occurred creating inventory")));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);

    }
    @GetMapping("/list")
    public CollectionModel<EntityModel<Inventory>> list() {
        return CollectionModel.of(service.findAll(), linkTo(methodOn(ProductController.class).list()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Inventory>> getById(@PathVariable Long id) {
        EntityModel<Inventory> entityModel = assembler.toModel(service.findById(id).orElseThrow(()->new ResourceNotFoundException("Inventory","id",id)));
        return ResponseEntity
                .ok(entityModel);

    }

    @PutMapping
    public ResponseEntity<EntityModel<Inventory>> update(@Valid @RequestBody InventoryDTO inventory){
        Inventory updatedInventory = service.update(modelMapper.map(inventory, Inventory.class)).orElseThrow(()->new ResourceNotFoundException("Inventory","id",inventory.getId()));
        EntityModel<Inventory> entityModel = assembler.toModel(updatedInventory);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping
    ResponseEntity<EntityModel<Inventory>> delete(@Valid @RequestBody InventoryDTO inventory){
        service.delete(modelMapper.map(inventory, Inventory.class));
        return ResponseEntity.noContent().build();
    }
}
