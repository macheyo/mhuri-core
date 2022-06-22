package zw.co.macheyo.mhuricore.service.purchaseorderitem;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.macheyo.mhuricore.exception.BusinessException;
import zw.co.macheyo.mhuricore.exception.ResourceNotFoundException;
import zw.co.macheyo.mhuricore.model.PurchaseOrderItem;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Kudzai.Macheyo
 */
@RestController
@RequestMapping("/purchase-order-item")
public class PurchaseOrderItemController {
    @Autowired
    PurchaseOrderItemService service;
    @Autowired
    PurchaseOrderItemModelAssembler assembler;
    @Autowired
    ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<EntityModel<PurchaseOrderItem>> create(@Valid @RequestBody PurchaseOrderItemDTO purchaseOrderItem) {
        EntityModel<PurchaseOrderItem> entityModel = assembler.toModel(service.create(modelMapper.map(purchaseOrderItem, PurchaseOrderItem.class)).orElseThrow(() -> new BusinessException("An error occurred creating order item")));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/list")
    public CollectionModel<EntityModel<PurchaseOrderItem>> list() {
        return CollectionModel.of(service.findAll(), linkTo(methodOn(PurchaseOrderItemController.class).list()).withSelfRel());
    }

    @GetMapping("/list/purchase-order/{id}")
    public CollectionModel<EntityModel<PurchaseOrderItem>> listByPurchaseOrder(@PathVariable Long id){
        return CollectionModel.of(service.findByOrderId(id), linkTo(methodOn(PurchaseOrderItemController.class).listByPurchaseOrder(id)).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<PurchaseOrderItem>> getById(@PathVariable Long id) {
        EntityModel<PurchaseOrderItem> entityModel = assembler.toModel(service.findById(id).orElseThrow(()->new ResourceNotFoundException("Purchase Order Item","id",id)));
        return ResponseEntity
                .ok(entityModel);

    }

    @PutMapping
    public ResponseEntity<EntityModel<PurchaseOrderItem>> update(@Valid @RequestBody PurchaseOrderItemDTO purchaseOrderItem){
        PurchaseOrderItem updatedPurchaseOrderItem = service.update(modelMapper.map(purchaseOrderItem, PurchaseOrderItem.class)).orElseThrow(()->new ResourceNotFoundException("Purchase order Item","id",purchaseOrderItem.getId()));
        EntityModel<PurchaseOrderItem> entityModel = assembler.toModel(updatedPurchaseOrderItem);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/deliver")
    public ResponseEntity<EntityModel<PurchaseOrderItem>> updateDelivery(@Valid @RequestBody PurchaseOrderItemDTO purchaseOrderItem){
        PurchaseOrderItem deliveredPurchaseOrderItem = service.delivered(modelMapper.map(purchaseOrderItem, PurchaseOrderItem.class)).orElseThrow(()->new ResourceNotFoundException("Purchase order Item","id",purchaseOrderItem.getId()));
        EntityModel<PurchaseOrderItem> entityModel = assembler.toModel(deliveredPurchaseOrderItem);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping
    ResponseEntity<EntityModel<PurchaseOrderItem>> delete(@Valid @RequestBody PurchaseOrderItemDTO purchaseOrderItem){
        service.delete(modelMapper.map(purchaseOrderItem, PurchaseOrderItem.class));
        return ResponseEntity.noContent().build();
    }
}
