package zw.co.macheyo.mhuricore.service.contact;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.macheyo.mhuricore.exception.BusinessException;
import zw.co.macheyo.mhuricore.exception.ResourceNotFoundException;
import zw.co.macheyo.mhuricore.model.Account;
import zw.co.macheyo.mhuricore.model.Contact;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/contact")
public class ContactController {
    @Autowired
    ContactService service;
    @Autowired
    ContactModelAssembler assembler;
    @Autowired
    ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<EntityModel<Contact>> create(@Valid @RequestBody ContactDTO contact) {
        EntityModel<Contact> entityModel = assembler.toModel(service.create(modelMapper.map(contact, Contact.class)).orElseThrow(()->new BusinessException("An error occurred creating contact")));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/list")
    public CollectionModel<EntityModel<Contact>> list() {
        return CollectionModel.of(service.findAll(), linkTo(methodOn(ContactController.class).list()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Contact>> getById(@PathVariable Long id) {
        EntityModel<Contact> entityModel = assembler.toModel(service.findById(id).orElseThrow(()->new ResourceNotFoundException("Contact","id",id)));
        return ResponseEntity
                .ok(entityModel);
    }

    @PutMapping
    public ResponseEntity<EntityModel<Contact>> update(@Valid @RequestBody ContactDTO contact){
        EntityModel<Contact> entityModel = assembler.toModel(service.update(modelMapper.map(contact,Contact.class)).orElseThrow(()-> new BusinessException("Error occurred updating contact")));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping
    public ResponseEntity<EntityModel<Account>> delete(@Valid @RequestBody ContactDTO contact){
        service.delete(modelMapper.map(contact,Contact.class));
        return ResponseEntity.noContent().build();
    }
}
