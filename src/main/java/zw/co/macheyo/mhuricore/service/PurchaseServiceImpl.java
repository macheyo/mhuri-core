package zw.co.macheyo.mhuricore.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import zw.co.macheyo.mhuricore.exception.ResourceNotFoundException;
import zw.co.macheyo.mhuricore.model.Product;
import zw.co.macheyo.mhuricore.model.Purchase;
import zw.co.macheyo.mhuricore.modelAssembler.ProductModelAssembler;
import zw.co.macheyo.mhuricore.modelAssembler.PurchaseModelAssembler;
import zw.co.macheyo.mhuricore.repository.PurchaseRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseServiceImpl implements PurchaseService{
    @Autowired
    PurchaseRepository purchaseRepository;
    @Autowired
    PurchaseModelAssembler assembler;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public Purchase save(Purchase purchase, HttpServletRequest httpServletRequest) {
        purchase.setCreatedBy(httpServletRequest.getUserPrincipal().getName());
        purchase.setLastModifiedBy(httpServletRequest.getUserPrincipal().getName());
        return purchaseRepository.save(modelMapper.map(purchase, Purchase.class));
    }

    @Override
    public Purchase update(Long id, Purchase purchase, HttpServletRequest httpServletRequest) {
        return purchaseRepository.findById(id).map(p->{
            p.setLastModifiedBy(httpServletRequest.getUserPrincipal().getName());
            p.setLastModifiedDate(LocalDateTime.now());
            return purchaseRepository.save(p);})
                .orElseThrow(()->new ResourceNotFoundException("purchase","id",id));
    }

    @Override
    public List<EntityModel<Purchase>> findAll() {
        return purchaseRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Purchase findById(Long id) {
        return purchaseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("purchase","id",id));
    }
}
