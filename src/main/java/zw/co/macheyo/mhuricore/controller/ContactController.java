package zw.co.macheyo.mhuricore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.macheyo.mhuricore.model.Contact;
import zw.co.macheyo.mhuricore.modelAssembler.ContactModelAssembler;
import zw.co.macheyo.mhuricore.service.ContactService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.ws.Response;

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
    public ResponseEntity<?> create(@Valid @RequestBody Contact contact, HttpServletRequest httpServletRequest) {
        EntityModel<Contact> entityModel = assembler.toModel(contactService.save(contact, httpServletRequest));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/list")
    public CollectionModel<EntityModel<Contact>> list() {
        return CollectionModel.of(contactService.findAll(), linkTo(methodOn(ContactController.class).list()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        EntityModel<Contact> entityModel = assembler.toModel(contactService.findById(id));
        return ResponseEntity
                .ok(entityModel);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Contact contact, HttpServletRequest httpServletRequest){
        EntityModel<Contact> entityModel = assembler.toModel(contactService.update(id, contact, httpServletRequest));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        contactService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
