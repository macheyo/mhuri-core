package zw.co.macheyo.mhuricore.modelAssembler;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import zw.co.macheyo.mhuricore.controller.TransactionController;
import zw.co.macheyo.mhuricore.model.Transaction;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TransactionModelAssembler implements RepresentationModelAssembler<Transaction, EntityModel<Transaction>> {
    @NonNull
    @Override
    public EntityModel<Transaction> toModel(Transaction entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(TransactionController.class).getById(entity.getId())).withSelfRel(),
                linkTo(methodOn(TransactionController.class).list()).withRel("transactions"));
    }
    @NonNull
    @Override
    public CollectionModel<EntityModel<Transaction>> toCollectionModel(Iterable<? extends Transaction> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
