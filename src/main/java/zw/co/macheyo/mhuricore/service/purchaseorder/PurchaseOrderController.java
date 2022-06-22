package zw.co.macheyo.mhuricore.service.purchaseorder;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.macheyo.mhuricore.exception.BusinessException;
import zw.co.macheyo.mhuricore.exception.ResourceNotFoundException;
import zw.co.macheyo.mhuricore.model.PurchaseOrder;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Kudzai.Macheyo
 */
@RestController
@RequestMapping("/purchase-order")
public class PurchaseOrderController {
    @Autowired
    PurchaseOrderService service;
    @Autowired
    PurchaseOrderModelAssembler assembler;
    @Autowired
    ModelMapper modelMapper;
    @PostMapping
    public ResponseEntity<EntityModel<PurchaseOrder>> create(@Valid @RequestBody PurchaseOrderDTO purchaseOrder){
        EntityModel<PurchaseOrder> entityModel = assembler.toModel(service.create(modelMapper.map(purchaseOrder, PurchaseOrder.class)).orElseThrow(()->new BusinessException("An error occurred creating purchase order")));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/list")
    public CollectionModel<EntityModel<PurchaseOrder>> list() {
        return CollectionModel.of(service.findAll(), linkTo(methodOn(PurchaseOrderController.class).list()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<PurchaseOrder>> getById(@PathVariable Long id) {
        EntityModel<PurchaseOrder> entityModel = assembler.toModel(service.findById(id).orElseThrow(()->new ResourceNotFoundException(PurchaseOrder.class.getSimpleName(),"id",id)));
        return ResponseEntity
                .ok(entityModel);

    }

    @PutMapping
    public ResponseEntity<EntityModel<PurchaseOrder>> update(@Valid @RequestBody PurchaseOrderDTO purchaseOrder){
        PurchaseOrder updatedPurchaseOrder = service.update(modelMapper.map(purchaseOrder,PurchaseOrder.class)).orElseThrow(()->new ResourceNotFoundException(PurchaseOrder.class.getSimpleName(),"id",purchaseOrder.getId()));
        EntityModel<PurchaseOrder> entityModel = assembler.toModel(updatedPurchaseOrder);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/complete")
    public ResponseEntity<EntityModel<PurchaseOrder>> complete(@Valid @RequestBody PurchaseOrderDTO purchaseOrder){
        EntityModel<PurchaseOrder> entityModel = assembler.toModel(service.complete(modelMapper.map(purchaseOrder, PurchaseOrder.class)).orElseThrow(()->new ResourceNotFoundException(PurchaseOrder.class.getSimpleName(),"id",purchaseOrder.getId())));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/cancel")
    public ResponseEntity<EntityModel<PurchaseOrder>> cancel(@Valid @RequestBody PurchaseOrderDTO purchaseOrder){
        EntityModel<PurchaseOrder> entityModel = assembler.toModel(service.cancel(modelMapper.map(purchaseOrder, PurchaseOrder.class)).orElseThrow(()->new ResourceNotFoundException(PurchaseOrder.class.getSimpleName(),"id",purchaseOrder.getId())));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping
    ResponseEntity<EntityModel<PurchaseOrder>> delete(@Valid @RequestBody PurchaseOrderDTO purchaseOrder){
        service.delete(modelMapper.map(purchaseOrder,PurchaseOrder.class));
        return ResponseEntity.noContent().build();
    }
}
