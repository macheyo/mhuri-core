package zw.co.macheyo.mhuricore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import zw.co.macheyo.mhuricore.model.Customer;
import zw.co.macheyo.mhuricore.modelAssembler.CustomerModelAssembler;
import zw.co.macheyo.mhuricore.service.CustomerService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;
    @Autowired
    CustomerModelAssembler assembler;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public EntityModel<Customer> create(@Valid @RequestBody Customer customer, HttpServletRequest httpServletRequest) {
        return assembler.toModel(customerService.save(customer, httpServletRequest));
    }

    @GetMapping("/list")
    public CollectionModel<EntityModel<Customer>> list() {
        return CollectionModel.of(customerService.findAll(), linkTo(methodOn(CustomerController.class).list()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Customer> getById(@PathVariable Long id) {
        Customer customer = customerService.findById(id);
        return assembler.toModel(customer);
    }

    @PutMapping("/update/{id}")
    public EntityModel<Customer> update(@PathVariable Long id, @Valid @RequestBody Customer customer, HttpServletRequest httpServletRequest){
        Customer updatedCustomer = customerService.update(id, customer, httpServletRequest);
        return assembler.toModel(updatedCustomer);
    }
}
