package zw.co.macheyo.mhuricore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import zw.co.macheyo.mhuricore.model.Contact;
import zw.co.macheyo.mhuricore.modelAssembler.ContactModelAssembler;
import zw.co.macheyo.mhuricore.service.ContactService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/contact")
public class ContactController {
    @Autowired
    ContactService contactService;
    @Autowired
    ContactModelAssembler assembler;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public EntityModel<Contact> create(@Valid @RequestBody Contact contact, HttpServletRequest httpServletRequest) {
        return assembler.toModel(contactService.save(contact, httpServletRequest));
    }

    @GetMapping("/list")
    public CollectionModel<EntityModel<Contact>> list() {
        return CollectionModel.of(contactService.findAll(), linkTo(methodOn(ContactController.class).list()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Contact> getById(@PathVariable Long id) {
        Contact contact = contactService.findById(id);
        return assembler.toModel(contact);
    }

    @PutMapping("/update/{id}")
    public EntityModel<Contact> update(@PathVariable Long id, @Valid @RequestBody Contact contact, HttpServletRequest httpServletRequest){
        Contact updatedContact = contactService.update(id, contact, httpServletRequest);
        return assembler.toModel(updatedContact);
    }
}
