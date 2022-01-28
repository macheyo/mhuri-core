package zw.co.macheyo.mhuricore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import zw.co.macheyo.mhuricore.model.Account;
import zw.co.macheyo.mhuricore.model.Inventory;
import zw.co.macheyo.mhuricore.model.InventoryDto;
import zw.co.macheyo.mhuricore.modelAssembler.InventoryModelAssembler;
import zw.co.macheyo.mhuricore.service.InventoryService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
    }
}
