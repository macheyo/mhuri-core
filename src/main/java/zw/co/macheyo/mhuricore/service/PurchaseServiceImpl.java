package zw.co.macheyo.mhuricore.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.co.macheyo.mhuricore.exception.MethodNotAllowedException;
import zw.co.macheyo.mhuricore.exception.ResourceNotFoundException;
import zw.co.macheyo.mhuricore.model.*;
import zw.co.macheyo.mhuricore.modelAssembler.PurchaseModelAssembler;
import zw.co.macheyo.mhuricore.repository.PurchaseRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PurchaseServiceImpl implements PurchaseService{
    @Autowired
    PurchaseRepository purchaseRepository;
    @Autowired
    DoubleEntryService doubleEntryService;
    @Autowired
    PurchaseModelAssembler assembler;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public Purchase save(Purchase purchase) {
        return purchaseRepository.save(modelMapper.map(purchase, Purchase.class));
    }

    @Override
    public Purchase update(Long id, Purchase purchase) {
        return purchaseRepository.findById(id).map(p->{

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

    @Override
    @Transactional
    public Purchase complete(Long id) {
        return purchaseRepository.findById(id).map(p->{
            if(p.getStatus()==Status.IN_PROGRESS){
                p.setStatus(Status.COMPLETE);
                EntityModel<Purchase> entityModel = assembler.toModel(findById(p.getId()));
                doubleEntryService.record(
                        "purchases",
                        "cash",
                        entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri().toString(),
                        purchaseTotalPrice(p.getProducts())
                );
                return purchaseRepository.save(p);
            }else throw new MethodNotAllowedException("purchase","complete",p.getStatus());
        }).orElseThrow(()->new ResourceNotFoundException("purchase","id",id));
    }

    @Override
    public Purchase cancel(Long id) {
        return purchaseRepository.findById(id).map(p->{
            if(p.getStatus()==Status.IN_PROGRESS) {
                p.setStatus(Status.CANCELLED);
                return purchaseRepository.save(p);
            }
            else throw new MethodNotAllowedException("purchase","cancel",p.getStatus());
        }).orElseThrow(()->new ResourceNotFoundException("purchase","id",id));
    }

    @Override
    public void deleteById(Long id) {
        purchaseRepository.deleteById(id);
    }



    private Double purchaseTotalPrice(Set<Inventory> products) {
        double totalPurchasePrice = 0;
        for(Inventory product: products){
            totalPurchasePrice += product.getPurchasePrice()*product.getQuantity();
        }
        return totalPurchasePrice;
    }
}
