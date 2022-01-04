package zw.co.macheyo.mhuricore.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.co.macheyo.mhuricore.exception.ResourceNotFoundException;
import zw.co.macheyo.mhuricore.model.*;
import zw.co.macheyo.mhuricore.modelAssembler.OrderModelAssembler;
import zw.co.macheyo.mhuricore.repository.AccountRepository;
import zw.co.macheyo.mhuricore.repository.ContactRepository;
import zw.co.macheyo.mhuricore.repository.OrderRepository;
import zw.co.macheyo.mhuricore.repository.TransactionRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ContactRepository contactRepository;
    @Autowired
    OrderModelAssembler assembler;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    DoubleEntryService doubleEntryService;

    @Override
    public Order save(Order order, HttpServletRequest httpServletRequest) {
        contactRepository.findById(order.getContact().getId()).orElseThrow(()->new ResourceNotFoundException("contact","id",order.getContact().getId()
        ));
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
    @Transactional
    public Order close(Long id, HttpServletRequest httpServletRequest) {
        return orderRepository.findById(id).map(o->{
                    o.setLastModifiedBy(httpServletRequest.getUserPrincipal().getName());
                    o.setLastModifiedDate(LocalDateTime.now());
                    o.setStatus(Status.CLOSED);
                    doubleEntryService.record(
                            "cash",
                            "sales",
                            "reference",
                            orderTotalPrice(o.getItems()),
                            httpServletRequest
                            );
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

    private double orderTotalPrice(Set<Item> items){
        double totalPrice=0;
        for(Item item:items){
            double quantity = item.getQuantity();
            double price = item.getProduct().getPrice();
            totalPrice+=quantity*price;
        }
        return totalPrice;
    }
}
