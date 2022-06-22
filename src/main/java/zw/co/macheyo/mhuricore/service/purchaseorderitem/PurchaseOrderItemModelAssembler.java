package zw.co.macheyo.mhuricore.service.purchaseorderitem;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import zw.co.macheyo.mhuricore.enums.OrderStatus;
import zw.co.macheyo.mhuricore.model.PurchaseOrderItem;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Kudzai.Macheyo
 */
@Component
public class PurchaseOrderItemModelAssembler implements RepresentationModelAssembler<PurchaseOrderItem, EntityModel<PurchaseOrderItem>> {
    @Autowired
    ModelMapper modelMapper;

    @NonNull
    @Override
    public EntityModel<PurchaseOrderItem> toModel(PurchaseOrderItem entity) {
        EntityModel<PurchaseOrderItem> entityModel = EntityModel.of(entity,
                linkTo(methodOn(PurchaseOrderItemController.class).getById(entity.getId())).withSelfRel(),
                linkTo(methodOn(PurchaseOrderItemController.class).update(modelMapper.map(entity, PurchaseOrderItemDTO.class))).withRel("update"),
                linkTo(methodOn(PurchaseOrderItemController.class).delete(modelMapper.map(entity, PurchaseOrderItemDTO.class))).withRel("delete"),
                linkTo(methodOn(PurchaseOrderItemController.class).list()).withRel("list")
        );
        if(entity.getOrder().getStatus() == OrderStatus.COMPLETE && !entity.isDelivered()){
            entityModel.add(linkTo(methodOn(PurchaseOrderItemController.class).updateDelivery(modelMapper.map(entity, PurchaseOrderItemDTO.class))).withRel("delivered"));
        }
        return entityModel;

    }
    @NonNull
    @Override
    public CollectionModel<EntityModel<PurchaseOrderItem>> toCollectionModel(Iterable<? extends PurchaseOrderItem> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
