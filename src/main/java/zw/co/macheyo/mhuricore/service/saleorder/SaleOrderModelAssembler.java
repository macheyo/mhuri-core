package zw.co.macheyo.mhuricore.service.saleorder;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import zw.co.macheyo.mhuricore.enums.OrderStatus;
import zw.co.macheyo.mhuricore.model.SaleOrder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Kudzai.Macheyo
 */
@Component
public class SaleOrderModelAssembler implements RepresentationModelAssembler<SaleOrder, EntityModel<SaleOrder>> {
    @Autowired
    ModelMapper modelMapper;
    @Override
    public EntityModel<SaleOrder> toModel(SaleOrder entity) {
        EntityModel<SaleOrder> entityModel = EntityModel.of(entity,
                linkTo(methodOn(SaleOrderController.class).getById(entity.getId())).withSelfRel(),
                linkTo(methodOn(SaleOrderController.class).update(modelMapper.map(entity,SaleOrderDTO.class))).withRel("update"),
                linkTo(methodOn(SaleOrderController.class).delete(modelMapper.map(entity, SaleOrderDTO.class))).withRel("delete"),
                linkTo(methodOn(SaleOrderController.class).list()).withRel("list"));
        if(entity.getStatus()== OrderStatus.IN_PROGRESS){
            entityModel.add(linkTo(methodOn(SaleOrderController.class).complete(modelMapper.map(entity, SaleOrderDTO.class))).withRel("complete"));
            entityModel.add(linkTo(methodOn(SaleOrderController.class).cancel(modelMapper.map(entity, SaleOrderDTO.class))).withRel("cancel"));
        }
        return entityModel;
    }

    @Override
    public CollectionModel<EntityModel<SaleOrder>> toCollectionModel(Iterable<? extends SaleOrder> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
