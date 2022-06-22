package zw.co.macheyo.mhuricore.service.saleorderitem;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import zw.co.macheyo.mhuricore.enums.OrderStatus;
import zw.co.macheyo.mhuricore.model.SaleOrderItem;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Kudzai.Macheyo
 */
@Component
public class SaleOrderItemModelAssembler implements RepresentationModelAssembler<SaleOrderItem, EntityModel<SaleOrderItem>> {
    @Autowired
    ModelMapper modelMapper;

    @Override
    public EntityModel<SaleOrderItem> toModel(SaleOrderItem entity) {
        EntityModel<SaleOrderItem> entityModel = EntityModel.of(entity,
                linkTo(methodOn(SaleOderItemController.class).getById(entity.getId())).withSelfRel(),
                linkTo(methodOn(SaleOderItemController.class).update(modelMapper.map(entity, SaleOrderItemDTO.class))).withRel("update"),
                linkTo(methodOn(SaleOderItemController.class).delete(modelMapper.map(entity, SaleOrderItemDTO.class))).withRel("delete"),
                linkTo(methodOn(SaleOderItemController.class).list()).withRel("list")
        );
        if(entity.getOrder().getStatus() == OrderStatus.COMPLETE && !entity.isDelivered()){
            entityModel.add(linkTo(methodOn(SaleOderItemController.class).updateDelivery(modelMapper.map(entity, SaleOrderItemDTO.class))).withRel("delivered"));
        }
        return entityModel;
    }

    @Override
    public CollectionModel<EntityModel<SaleOrderItem>> toCollectionModel(Iterable<? extends SaleOrderItem> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
