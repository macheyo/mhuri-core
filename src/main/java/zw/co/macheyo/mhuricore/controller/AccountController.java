package zw.co.macheyo.mhuricore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.macheyo.mhuricore.model.Account;
import zw.co.macheyo.mhuricore.modelAssembler.AccountModelAssembler;
import zw.co.macheyo.mhuricore.service.AccountService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    AccountService accountService;
    @Autowired
    AccountModelAssembler assembler;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@Valid @RequestBody Account account, HttpServletRequest httpServletRequest) {
        EntityModel<Account> entityModel = assembler.toModel(accountService.save(account, httpServletRequest));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/list")
    public CollectionModel<EntityModel<Account>> list() {
        return CollectionModel.of(accountService.findAll(), linkTo(methodOn(AccountController.class).list()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        EntityModel<Account> entityModel = assembler.toModel(accountService.findById(id));
        return ResponseEntity
                .ok(entityModel);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Account account, HttpServletRequest httpServletRequest){
        EntityModel<Account> entityModel = assembler.toModel(accountService.update(id, account, httpServletRequest));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, HttpServletRequest httpServletRequest){
        accountService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
