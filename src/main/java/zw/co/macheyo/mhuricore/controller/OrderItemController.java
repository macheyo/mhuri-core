package zw.co.macheyo.mhuricore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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
    public EntityModel<Item> create(@PathVariable Long orderId, @Valid @RequestBody Item item, HttpServletRequest httpServletRequest) {
        return assembler.toModel(itemService.save(orderId, item, httpServletRequest));
    }

    @GetMapping("/item/list")
    public CollectionModel<EntityModel<Item>> list() {
        return CollectionModel.of(itemService.findAll(), linkTo(methodOn(OrderItemController.class).list()).withSelfRel());
    }

    @GetMapping("/item/{id}")
    public EntityModel<Item> getById(@PathVariable Long id) {
        Item item = itemService.findById(id);
        return assembler.toModel(item);
    }

    @PutMapping("/update/{orderId}/item/{itemId}")
    public EntityModel<Item> update(@PathVariable Long orderId, @PathVariable Long itemId, @Valid @RequestBody Item item, HttpServletRequest httpServletRequest){
        Item updatedItem = itemService.update(orderId, itemId, item, httpServletRequest);
        return assembler.toModel(updatedItem);
    }
}
