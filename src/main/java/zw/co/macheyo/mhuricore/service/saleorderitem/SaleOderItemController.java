package zw.co.macheyo.mhuricore.service.saleorderitem;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.macheyo.mhuricore.exception.BusinessException;
import zw.co.macheyo.mhuricore.exception.ResourceNotFoundException;
import zw.co.macheyo.mhuricore.model.SaleOrderItem;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Kudzai.Macheyo
 */
@RestController
@RequestMapping("/sale-order-item")
public class SaleOderItemController {
    @Autowired
    SaleOrderItemService service;
    @Autowired
    SaleOrderItemModelAssembler assembler;
    @Autowired
    ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<EntityModel<SaleOrderItem>> create(@Valid @RequestBody SaleOrderItemDTO saleOrderItem) {
        EntityModel<SaleOrderItem> entityModel = assembler.toModel(service.create(modelMapper.map(saleOrderItem, SaleOrderItem.class)).orElseThrow(() -> new BusinessException("An error occurred creating order item")));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/list")
    public CollectionModel<EntityModel<SaleOrderItem>> list() {
        return CollectionModel.of(service.findAll(), linkTo(methodOn(SaleOderItemController.class).list()).withSelfRel());
    }

    @GetMapping("/list/sale-order/{id}")
    public CollectionModel<EntityModel<SaleOrderItem>> listBySaleOrder(@PathVariable Long id){
        return CollectionModel.of(service.findByOrderId(id), linkTo(methodOn(SaleOderItemController.class).listBySaleOrder(id)).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<SaleOrderItem>> getById(@PathVariable Long id) {
        EntityModel<SaleOrderItem> entityModel = assembler.toModel(service.findById(id).orElseThrow(()->new ResourceNotFoundException("Sale Order Item","id",id)));
        return ResponseEntity
                .ok(entityModel);

    }

    @PutMapping
    public ResponseEntity<EntityModel<SaleOrderItem>> update(@Valid @RequestBody SaleOrderItemDTO saleOrderItem){
        SaleOrderItem updatedSaleOrderItem = service.update(modelMapper.map(saleOrderItem, SaleOrderItem.class)).orElseThrow(()->new ResourceNotFoundException("Sale order Item","id",saleOrderItem.getId()));
        EntityModel<SaleOrderItem> entityModel = assembler.toModel(updatedSaleOrderItem);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/deliver")
    public ResponseEntity<EntityModel<SaleOrderItem>> updateDelivery(@Valid @RequestBody SaleOrderItemDTO saleOrderItem){
        SaleOrderItem deliveredSaleOrderItem = service.delivered(modelMapper.map(saleOrderItem, SaleOrderItem.class)).orElseThrow(()->new ResourceNotFoundException("Sale order Item","id",saleOrderItem.getId()));
        EntityModel<SaleOrderItem> entityModel = assembler.toModel(deliveredSaleOrderItem);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping
    ResponseEntity<EntityModel<SaleOrderItem>> delete(@Valid @RequestBody SaleOrderItemDTO saleOrderItem){
        service.delete(modelMapper.map(saleOrderItem, SaleOrderItem.class));
        return ResponseEntity.noContent().build();
    }
}
