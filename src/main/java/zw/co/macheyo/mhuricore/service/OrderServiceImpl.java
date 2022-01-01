package zw.co.macheyo.mhuricore.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import zw.co.macheyo.mhuricore.exception.ResourceNotFoundException;
import zw.co.macheyo.mhuricore.model.Customer;
import zw.co.macheyo.mhuricore.model.Order;
import zw.co.macheyo.mhuricore.modelAssembler.CustomerModelAssembler;
import zw.co.macheyo.mhuricore.modelAssembler.OrderModelAssembler;
import zw.co.macheyo.mhuricore.repository.OrderRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderModelAssembler assembler;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public Order save(Order order, HttpServletRequest httpServletRequest) {
        order.setCreatedBy(httpServletRequest.getUserPrincipal().getName());
        order.setLastModifiedBy(httpServletRequest.getUserPrincipal().getName());
        return orderRepository.save(modelMapper.map(order, Order.class));
    }

    @Override
    public Order update(Long id, Order order, HttpServletRequest httpServletRequest) {
        return orderRepository.findById(id).map(o->{
                    o.setLastModifiedBy(httpServletRequest.getUserPrincipal().getName());
                    o.setLastModifiedDate(LocalDateTime.now());
                    o.setStatus(order.getStatus());
                    return orderRepository.save(o);})
                .orElseThrow(()->new ResourceNotFoundException("order","id",id));
    }

    @Override
    public List<EntityModel<Order>> findAll() {
        return orderRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("order","id",id));
    }
}
