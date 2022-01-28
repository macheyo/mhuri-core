package zw.co.macheyo.mhuricore.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.co.macheyo.mhuricore.exception.InventoryNotEnoughException;
import zw.co.macheyo.mhuricore.exception.ResourceNotFoundException;
import zw.co.macheyo.mhuricore.model.*;
import zw.co.macheyo.mhuricore.repository.ProductRepository;
import zw.co.macheyo.mhuricore.repository.PurchaseRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InventoryServiceImpl implements InventoryService{
    @Autowired
    PurchaseRepository purchaseRepository;
    @Autowired
    ProductRepository productRepository;
    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public Inventory record(Long purchaseId,Long productId, InventoryDto inventoryDto, HttpServletRequest httpServletRequest) {
        Product product = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("product","id",productId));
        Purchase purchase = purchaseRepository.findById(purchaseId).orElseThrow(()->new ResourceNotFoundException("purchase","id",purchaseId));
        Inventory inventory = new Inventory(purchase,product,inventoryDto.getSellingPrice(),inventoryDto.getPurchasePrice(),inventoryDto.getQuantity());
        entityManager.persist(inventory);
        entityManager.close();
        return inventory;
    }

    @Override
    public void adjust(Product product, double quantity) {
        if(isInventoryAvailable(product, quantity));
        {
            List<Inventory> inventoryList = product.getOrder_runs().stream().sorted(Comparator.comparing(Inventory::getCreatedDate)).collect(Collectors.toList());
            ListIterator<Inventory> inventory = inventoryList.listIterator();
            while (inventory.hasNext()) {
                Inventory i = inventory.next();
                if (i.getAvailableQuantity() > 0) {
                    if (i.getAvailableQuantity() < quantity) {
                        quantity -= i.getAvailableQuantity();
                        i.setAvailableQuantity(0);
                    } else {
                        i.setAvailableQuantity(i.getAvailableQuantity() - quantity);
                        break;
                    }
                }
            }
        }
    }

    private boolean isInventoryAvailable(Product product, double quantity){
        List<Inventory> inventoryList = product.getOrder_runs().stream().sorted(Comparator.comparing(Inventory::getCreatedDate)).collect(Collectors.toList());
        ListIterator<Inventory> inventory = inventoryList.listIterator();
        double availableInventory = 0;
        while (inventory.hasNext()){
            Inventory i = inventory.next();
            availableInventory += i.getAvailableQuantity();
        }
        if(availableInventory<=quantity){
            throw new InventoryNotEnoughException(product.getName(),availableInventory);
        }
        return true;
    }

}
