package zw.co.macheyo.mhuricore.modelAssembler;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import zw.co.macheyo.mhuricore.controller.ContactController;
import zw.co.macheyo.mhuricore.model.Contact;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ContactModelAssembler implements RepresentationModelAssembler<Contact, EntityModel<Contact>> {
    @NonNull
    @Override
    public EntityModel<Contact> toModel(Contact entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(ContactController.class).getById(entity.getId())).withSelfRel(),
                linkTo(methodOn(ContactController.class).list()).withRel("contacts"));
    }
    @NonNull
    @Override
    public CollectionModel<EntityModel<Contact>> toCollectionModel(Iterable<? extends Contact> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
