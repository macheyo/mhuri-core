package zw.co.macheyo.mhuricore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import zw.co.macheyo.mhuricore.model.Transaction;
import zw.co.macheyo.mhuricore.modelAssembler.TransactionModelAssembler;
import zw.co.macheyo.mhuricore.service.TransactionService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    TransactionService transactionService;
    @Autowired
    TransactionModelAssembler assembler;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public EntityModel<Transaction> create(@Valid @RequestBody Transaction transaction, HttpServletRequest httpServletRequest) {
        return assembler.toModel(transactionService.save(transaction, httpServletRequest));
    }

    @GetMapping("/list")
    public CollectionModel<EntityModel<Transaction>> list() {
        return CollectionModel.of(transactionService.findAll(), linkTo(methodOn(TransactionController.class).list()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Transaction> getById(@PathVariable Long id) {
        Transaction transaction = transactionService.findById(id);
        return assembler.toModel(transaction);
    }

    @PutMapping("/update/{id}")
    public EntityModel<Transaction> update(@PathVariable Long id, @Valid @RequestBody Transaction transaction, HttpServletRequest httpServletRequest){
        Transaction updatedTransaction = transactionService.update(id, transaction, httpServletRequest);
        return assembler.toModel(updatedTransaction);
    }
}
