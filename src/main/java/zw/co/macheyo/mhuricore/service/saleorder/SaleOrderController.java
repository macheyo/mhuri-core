package zw.co.macheyo.mhuricore.service.saleorder;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.macheyo.mhuricore.exception.BusinessException;
import zw.co.macheyo.mhuricore.exception.ResourceNotFoundException;
import zw.co.macheyo.mhuricore.model.SaleOrder;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Kudzai.Macheyo
 */
@Slf4j
@RestController
@RequestMapping("/sale-order")
public class SaleOrderController {
    @Autowired
    SaleOrderService service;
    @Autowired
    SaleOrderModelAssembler assembler;
    @Autowired
    ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<EntityModel<SaleOrder>> create(@Valid @RequestBody SaleOrderDTO saleOrder){
        EntityModel<SaleOrder> entityModel = assembler.toModel(service.create(modelMapper.map(saleOrder, SaleOrder.class)).orElseThrow(()->new BusinessException("An error occurred creating sale order")));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/list")
    public CollectionModel<EntityModel<SaleOrder>> list() {
        return CollectionModel.of(service.findAll(), linkTo(methodOn(SaleOrderController.class).list()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<SaleOrder>> getById(@PathVariable Long id) {
        EntityModel<SaleOrder> entityModel = assembler.toModel(service.findById(id).orElseThrow(()->new ResourceNotFoundException("Sale Order","id",id)));
        return ResponseEntity
                .ok(entityModel);

    }

    @PutMapping
    public ResponseEntity<EntityModel<SaleOrder>> update(@Valid @RequestBody SaleOrderDTO saleOrder){
        SaleOrder updatedSaleOrder = service.update(modelMapper.map(saleOrder,SaleOrder.class)).orElseThrow(()->new ResourceNotFoundException(SaleOrder.class.getSimpleName(),"id",saleOrder.getId()));
        EntityModel<SaleOrder> entityModel = assembler.toModel(updatedSaleOrder);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/complete")
    public ResponseEntity<EntityModel<SaleOrder>> complete(@Valid @RequestBody SaleOrderDTO saleOrder){
        EntityModel<SaleOrder> entityModel = assembler.toModel(service.complete(modelMapper.map(saleOrder,SaleOrder.class)).orElseThrow(()->new ResourceNotFoundException(SaleOrder.class.getSimpleName(),"id",saleOrder.getId())));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/cancel")
    public ResponseEntity<EntityModel<SaleOrder>> cancel(@Valid @RequestBody SaleOrderDTO saleOrder){
        EntityModel<SaleOrder> entityModel = assembler.toModel(service.cancel(modelMapper.map(saleOrder, SaleOrder.class)).orElseThrow(()->new ResourceNotFoundException(SaleOrder.class.getSimpleName(),"id",saleOrder.getId())));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping
    ResponseEntity<EntityModel<SaleOrder>> delete(@Valid @RequestBody SaleOrderDTO saleOrder){
        service.delete(modelMapper.map(saleOrder, SaleOrder.class));
        return ResponseEntity.noContent().build();
    }
}
