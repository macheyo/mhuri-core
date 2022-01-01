package zw.co.macheyo.mhuricore.modelAssembler;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import zw.co.macheyo.mhuricore.controller.CustomerController;
import zw.co.macheyo.mhuricore.controller.ProductController;
import zw.co.macheyo.mhuricore.model.Customer;
import zw.co.macheyo.mhuricore.model.Product;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CustomerModelAssembler implements RepresentationModelAssembler<Customer, EntityModel<Customer>> {
    @Override
    public EntityModel<Customer> toModel(Customer entity) {
        return EntityModel.of(entity, linkTo(methodOn(CustomerController.class).list()).withRel("customers"));
    }

    @Override
    public CollectionModel<EntityModel<Customer>> toCollectionModel(Iterable<? extends Customer> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
