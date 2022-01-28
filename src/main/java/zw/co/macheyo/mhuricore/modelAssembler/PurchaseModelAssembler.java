package zw.co.macheyo.mhuricore.modelAssembler;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import zw.co.macheyo.mhuricore.controller.PurchaseController;
import zw.co.macheyo.mhuricore.model.Purchase;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PurchaseModelAssembler implements RepresentationModelAssembler<Purchase, EntityModel<Purchase>> {
    @NonNull
    @Override
    public EntityModel<Purchase> toModel(Purchase entity) {
        return EntityModel.of(entity, linkTo(methodOn(PurchaseController.class).list()).withRel("purchases"));
    }
    @NonNull
    @Override
    public CollectionModel<EntityModel<Purchase>> toCollectionModel(Iterable<? extends Purchase> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
