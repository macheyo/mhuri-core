package zw.co.macheyo.mhuricore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import zw.co.macheyo.mhuricore.exception.ResourceNotFoundException;
import zw.co.macheyo.mhuricore.model.Item;
import zw.co.macheyo.mhuricore.modelAssembler.ItemModelAssembler;
import zw.co.macheyo.mhuricore.repository.ItemRepository;
import zw.co.macheyo.mhuricore.repository.OrderRepository;
import zw.co.macheyo.mhuricore.repository.ProductRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService{
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ItemModelAssembler assembler;
    @Override
    public Item save(Long orderId, Item item, HttpServletRequest httpServletRequest) {
        productRepository.findById(item.getProduct().getId()).orElseThrow(()->new ResourceNotFoundException("product","id",item.getProduct().getId()
        ));
        return orderRepository.findById(orderId).map(order -> {
            item.setCreatedBy(httpServletRequest.getUserPrincipal().getName());
            item.setLastModifiedBy(httpServletRequest.getUserPrincipal().getName());
            item.setOrder(order);
            return itemRepository.save(item);
        }).orElseThrow(()->new ResourceNotFoundException("order","id",orderId));
    }

    @Override
    public List<EntityModel<Item>> findAll() {
        return itemRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Item findById(Long id) {
        return itemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("item","id",id));
    }

    @Override
    public Item update(Long orderId, Long itemId, Item item, HttpServletRequest httpServletRequest) {
        orderRepository.findById(orderId).orElseThrow(()->new ResourceNotFoundException("order","id",orderId));
        return itemRepository.findById(itemId).map(itemUpdate -> {
            itemUpdate.setLastModifiedBy(httpServletRequest.getUserPrincipal().getName());
            itemUpdate.setLastModifiedDate(LocalDateTime.now());
            itemUpdate.setQuantity(item.getQuantity());
            return itemRepository.save(itemUpdate);
        }).orElseThrow(() -> new ResourceNotFoundException("item","id",itemId));
    }
}
