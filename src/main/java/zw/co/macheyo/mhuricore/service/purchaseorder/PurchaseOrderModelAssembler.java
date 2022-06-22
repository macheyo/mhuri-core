package zw.co.macheyo.mhuricore.service.purchaseorder;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import zw.co.macheyo.mhuricore.enums.OrderStatus;
import zw.co.macheyo.mhuricore.model.PurchaseOrder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Kudzai.Macheyo
 */
@Component
public class PurchaseOrderModelAssembler implements RepresentationModelAssembler<PurchaseOrder, EntityModel<PurchaseOrder>> {
    @Autowired
    ModelMapper modelMapper;
    @NonNull
    @Override
    public EntityModel<PurchaseOrder> toModel(PurchaseOrder entity) {
        EntityModel<PurchaseOrder> entityModel = EntityModel.of(entity,
                linkTo(methodOn(PurchaseOrderController.class).getById(entity.getId())).withSelfRel(),
                linkTo(methodOn(PurchaseOrderController.class).update(modelMapper.map(entity,PurchaseOrderDTO.class))).withRel("update"),
                linkTo(methodOn(PurchaseOrderController.class).delete(modelMapper.map(entity,PurchaseOrderDTO.class))).withRel("delete"),
                linkTo(methodOn(PurchaseOrderController.class).list()).withRel("list"));
        if(entity.getStatus()== OrderStatus.IN_PROGRESS){
            entityModel.add(linkTo(methodOn(PurchaseOrderController.class).complete(modelMapper.map(entity,PurchaseOrderDTO.class))).withRel("complete"));
            entityModel.add(linkTo(methodOn(PurchaseOrderController.class).cancel(modelMapper.map(entity,PurchaseOrderDTO.class))).withRel("cancel"));
        }
        return entityModel;

    }
    @NonNull
    @Override
    public CollectionModel<EntityModel<PurchaseOrder>> toCollectionModel(Iterable<? extends PurchaseOrder> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
