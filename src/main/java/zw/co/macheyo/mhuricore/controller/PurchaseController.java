package zw.co.macheyo.mhuricore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import zw.co.macheyo.mhuricore.model.Order;
import zw.co.macheyo.mhuricore.model.Purchase;
import zw.co.macheyo.mhuricore.modelAssembler.PurchaseModelAssembler;
import zw.co.macheyo.mhuricore.service.PurchaseService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    @Autowired
    PurchaseService purchaseService;
    @Autowired
    PurchaseModelAssembler assembler;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public EntityModel<Purchase> create(@Valid @RequestBody Purchase purchase, HttpServletRequest httpServletRequest) {
        return assembler.toModel(purchaseService.save(purchase, httpServletRequest));
    }

    @GetMapping("/list")
    public CollectionModel<EntityModel<Purchase>> list() {
        return CollectionModel.of(purchaseService.findAll(), linkTo(methodOn(PurchaseController.class).list()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Purchase> getById(@PathVariable Long id) {
        Purchase purchase = purchaseService.findById(id);
        return assembler.toModel(purchase);
    }

    @PutMapping("/update/{id}")
    public EntityModel<Purchase> update(@PathVariable Long id, @Valid @RequestBody Purchase purchase, HttpServletRequest httpServletRequest){
        Purchase updatedPurchase = purchaseService.update(id, purchase, httpServletRequest);
        return assembler.toModel(updatedPurchase);
    }

    @PostMapping("/close/{id}")
    public EntityModel<Purchase> close(@PathVariable Long id, HttpServletRequest httpServletRequest){
        Purchase closedPurchase = purchaseService.close(id, httpServletRequest);
        return assembler.toModel(closedPurchase);
    }
}
