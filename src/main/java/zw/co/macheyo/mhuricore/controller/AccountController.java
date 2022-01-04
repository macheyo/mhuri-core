package zw.co.macheyo.mhuricore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import zw.co.macheyo.mhuricore.model.Account;
import zw.co.macheyo.mhuricore.model.Contact;
import zw.co.macheyo.mhuricore.modelAssembler.AccountModelAssembler;
import zw.co.macheyo.mhuricore.modelAssembler.ContactModelAssembler;
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
    public EntityModel<Account> create(@Valid @RequestBody Account account, HttpServletRequest httpServletRequest) {
        return assembler.toModel(accountService.save(account, httpServletRequest));
    }

    @GetMapping("/list")
    public CollectionModel<EntityModel<Account>> list() {
        return CollectionModel.of(accountService.findAll(), linkTo(methodOn(AccountController.class).list()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Account> getById(@PathVariable Long id) {
        Account account = accountService.findById(id);
        return assembler.toModel(account);
    }

    @PutMapping("/update/{id}")
    public EntityModel<Account> update(@PathVariable Long id, @Valid @RequestBody Account account, HttpServletRequest httpServletRequest){
        Account updatedAccount = accountService.update(id, account, httpServletRequest);
        return assembler.toModel(updatedAccount);
    }
}
