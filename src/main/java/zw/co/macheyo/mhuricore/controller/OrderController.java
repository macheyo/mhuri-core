package zw.co.macheyo.mhuricore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import zw.co.macheyo.mhuricore.model.Customer;
import zw.co.macheyo.mhuricore.model.Order;
import zw.co.macheyo.mhuricore.modelAssembler.CustomerModelAssembler;
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
    public EntityModel<Order> create(@Valid @RequestBody Order order, HttpServletRequest httpServletRequest) {
        return assembler.toModel(orderService.save(order, httpServletRequest));
    }

    @GetMapping("/list")
    public CollectionModel<EntityModel<Order>> list() {
        return CollectionModel.of(orderService.findAll(), linkTo(methodOn(OrderController.class).list()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Order> getById(@PathVariable Long id) {
        Order order = orderService.findById(id);
        return assembler.toModel(order);
    }

    @PutMapping("/update/{id}")
    public EntityModel<Order> update(@PathVariable Long id, @Valid @RequestBody Order order, HttpServletRequest httpServletRequest){
        Order updatedOrder = orderService.update(id, order, httpServletRequest);
        return assembler.toModel(updatedOrder);
    }
}
