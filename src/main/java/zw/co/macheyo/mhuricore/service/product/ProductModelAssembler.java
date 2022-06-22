package zw.co.macheyo.mhuricore.service.product;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import zw.co.macheyo.mhuricore.model.Product;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductModelAssembler implements RepresentationModelAssembler<Product, EntityModel<Product>> {
    @Autowired
    ModelMapper modelMapper;
    @NonNull
    @Override
    public EntityModel<Product> toModel(Product entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(ProductController.class).getById(entity.getId())).withSelfRel(),
                linkTo(methodOn(ProductController.class).update(modelMapper.map(entity,ProductDTO.class))).withRel("update"),
                linkTo(methodOn(ProductController.class).delete(modelMapper.map(entity,ProductDTO.class))).withRel("delete"),
                linkTo(methodOn(ProductController.class).list()).withRel("list"));
    }
    @NonNull
    @Override
    public CollectionModel<EntityModel<Product>> toCollectionModel(Iterable<? extends Product> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
