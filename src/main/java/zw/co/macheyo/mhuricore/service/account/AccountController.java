package zw.co.macheyo.mhuricore.service.account;

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

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    AccountService service;
    @Autowired
    AccountModelAssembler assembler;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<EntityModel<Account>> create(@Valid @RequestBody AccountDTO account) {
        EntityModel<Account> entityModel = assembler.toModel(service.create(modelMapper.map(account,Account.class)).orElseThrow(()->new BusinessException("An error occurred creating account")));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/list")
    public CollectionModel<EntityModel<Account>> list() {
        return CollectionModel.of(service.findAll(), linkTo(methodOn(AccountController.class).list()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Account>> getById(@PathVariable Long id) {
        EntityModel<Account> entityModel = assembler.toModel(service.findById(id).orElseThrow(()->new ResourceNotFoundException("Account","id",id)));
        return ResponseEntity
                .ok(entityModel);
    }

    @PutMapping
    public ResponseEntity<EntityModel<Account>> update(@Valid @RequestBody AccountDTO account){
        EntityModel<Account> entityModel = assembler.toModel(service.update(modelMapper.map(account,Account.class)).orElseThrow(()->new ResourceNotFoundException("Account","id",account.getId())));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping
    public ResponseEntity<EntityModel<Account>> delete(@Valid @RequestBody AccountDTO account){
        service.delete(modelMapper.map(account,Account.class));
        return ResponseEntity.noContent().build();
    }
}
