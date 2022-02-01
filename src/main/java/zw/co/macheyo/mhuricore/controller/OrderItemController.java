package zw.co.macheyo.mhuricore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.macheyo.mhuricore.model.Item;
import zw.co.macheyo.mhuricore.modelAssembler.ItemModelAssembler;
import zw.co.macheyo.mhuricore.service.ItemService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/order")
public class OrderItemController {
    @Autowired
    ItemService itemService;
    @Autowired
    ItemModelAssembler assembler;

    @PostMapping("/{orderId}/item")
    public ResponseEntity<?> create(@PathVariable Long orderId, @Valid @RequestBody Item item, HttpServletRequest httpServletRequest) {
        EntityModel<Item> entityModel = assembler.toModel(itemService.save(orderId, item, httpServletRequest));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/item/list")
    public CollectionModel<EntityModel<Item>> list() {
        return CollectionModel.of(itemService.findAll(), linkTo(methodOn(OrderItemController.class).list()).withSelfRel());
    }

    @GetMapping("/item/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        EntityModel<Item> entityModel = assembler.toModel(itemService.findById(id));
        return ResponseEntity
                .ok(entityModel);
    }

    @PutMapping("/update/{orderId}/item/{itemId}")
    public ResponseEntity<?> update(@PathVariable Long orderId, @PathVariable Long itemId, @Valid @RequestBody Item item, HttpServletRequest httpServletRequest){
        EntityModel<Item> entityModel = assembler.toModel(itemService.update(orderId, itemId, item, httpServletRequest));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }
}
