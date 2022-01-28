package zw.co.macheyo.mhuricore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import zw.co.macheyo.mhuricore.model.Account;
import zw.co.macheyo.mhuricore.model.Inventory;
import zw.co.macheyo.mhuricore.model.InventoryDto;
import zw.co.macheyo.mhuricore.modelAssembler.AccountModelAssembler;
import zw.co.macheyo.mhuricore.modelAssembler.InventoryModelAssembler;
import zw.co.macheyo.mhuricore.service.InventoryService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired
    InventoryService inventoryService;
    @Autowired
    InventoryModelAssembler assembler;

    @PostMapping("/save/purchase/{purchaseId}/product/{productId}")
    public EntityModel<Inventory> create(@PathVariable Long purchaseId,@PathVariable Long productId,@Valid @RequestBody InventoryDto inventory, HttpServletRequest httpServletRequest) {
        return assembler.toModel(inventoryService.record(purchaseId,productId, inventory, httpServletRequest));
    }

    @GetMapping("/list")
    public CollectionModel<EntityModel<Account>> list() {
        return null;
        //        return CollectionModel.of(inventoryService.findAll(), linkTo(methodOn(InventoryController.class).list()).withSelfRel());
    }
//
//    @GetMapping("/{id}")
//    public EntityModel<Account> getById(@PathVariable Long id) {
//        Account account = inventoryService.findById(id);
//        return assembler.toModel(account);
//    }
//
//    @PutMapping("/update/{id}")
//    public EntityModel<Account> update(@PathVariable Long id, @Valid @RequestBody Account account, HttpServletRequest httpServletRequest){
//        Account updatedAccount = inventoryService.update(id, account, httpServletRequest);
//        return assembler.toModel(updatedAccount);
//    }
}
