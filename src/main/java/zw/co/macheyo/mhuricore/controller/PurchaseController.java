package zw.co.macheyo.mhuricore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> create(@Valid @RequestBody Purchase purchase, HttpServletRequest httpServletRequest) {
        EntityModel<Purchase> entityModel = assembler.toModel(purchaseService.save(purchase, httpServletRequest));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/list")
    public CollectionModel<EntityModel<Purchase>> list() {
        return CollectionModel.of(purchaseService.findAll(), linkTo(methodOn(PurchaseController.class).list()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        EntityModel<Purchase> entityModel = assembler.toModel(purchaseService.findById(id));
        return ResponseEntity
                .ok(entityModel);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Purchase purchase, HttpServletRequest httpServletRequest){
        EntityModel<Purchase> entityModel = assembler.toModel(purchaseService.update(id, purchase, httpServletRequest));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PostMapping("/close/{id}")
    public ResponseEntity<?> close(@PathVariable Long id, HttpServletRequest httpServletRequest){
        EntityModel<Purchase> entityModel = assembler.toModel(purchaseService.close(id, httpServletRequest));
        return ResponseEntity
                .ok(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, HttpServletRequest httpServletRequest){
        purchaseService.deleteById(id);
        return ResponseEntity
                .noContent().build();
    }
}
