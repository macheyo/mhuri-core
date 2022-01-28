package zw.co.macheyo.mhuricore.modelAssembler;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import zw.co.macheyo.mhuricore.controller.ProductController;
import zw.co.macheyo.mhuricore.model.Product;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductModelAssembler implements RepresentationModelAssembler<Product, EntityModel<Product>> {
    @NonNull
    @Override
    public EntityModel<Product> toModel(Product entity) {
        return EntityModel.of(entity, linkTo(methodOn(ProductController.class).list()).withRel("products"));
    }
    @NonNull
    @Override
    public CollectionModel<EntityModel<Product>> toCollectionModel(Iterable<? extends Product> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
