package zw.co.macheyo.mhuricore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.macheyo.mhuricore.model.Order;
import zw.co.macheyo.mhuricore.modelAssembler.OrderModelAssembler;
import zw.co.macheyo.mhuricore.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderModelAssembler assembler;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@Valid @RequestBody Order order) {
        EntityModel<Order> entityModel = assembler.toModel(orderService.save(order));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/list")
    public CollectionModel<EntityModel<Order>> list() {
        return CollectionModel.of(orderService.findAll(), linkTo(methodOn(OrderController.class).list()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        EntityModel<Order> entityModel = assembler.toModel(orderService.findById(id));
        return ResponseEntity
                .ok(entityModel);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Order order){
        EntityModel<Order> entityModel = assembler.toModel(orderService.update(id, order));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        orderService.deleteById(id);
        return ResponseEntity
                .noContent().build();
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<?> complete(@PathVariable Long id){
        EntityModel<Order> entityModel= assembler.toModel(orderService.complete(id));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancel(@PathVariable Long id) {
        EntityModel<Order> entityModel = assembler.toModel(orderService.cancel(id));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }
}
