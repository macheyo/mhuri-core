package zw.co.macheyo.mhuricore.modelAssembler;


import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import zw.co.macheyo.mhuricore.controller.AccountController;
import zw.co.macheyo.mhuricore.model.Account;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AccountModelAssembler implements RepresentationModelAssembler<Account, EntityModel<Account>> {
    @NonNull
    @Override
    public EntityModel<Account> toModel(Account entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(AccountController.class).getById(entity.getId())).withSelfRel(),
                linkTo(methodOn(AccountController.class).list()).withRel("accounts"));
    }
    @NonNull
    @Override
    public CollectionModel<EntityModel<Account>> toCollectionModel(Iterable<? extends Account> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
