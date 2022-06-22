package zw.co.macheyo.mhuricore.service.inventory;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import zw.co.macheyo.mhuricore.exception.ResourceNotFoundException;
import zw.co.macheyo.mhuricore.model.Inventory;
import zw.co.macheyo.mhuricore.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InventoryServiceImpl implements InventoryService{
    @Autowired
    InventoryRepository repository;
    @Autowired
    InventoryModelAssembler assembler;
    @Autowired
    ModelMapper modelMapper;


    @Override
    public Optional<Inventory> findById(Object id) {
        return repository.findById((Long) id);
    }

    @Override
    public List<EntityModel<Inventory>> findAll() {
        return repository.findAll()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Inventory> create(Inventory inventory) {
        return Optional.of(repository.save(inventory));
    }

    @Override
    public Optional<Inventory> update(Inventory inventory) {
        return repository.findById(inventory.getId()).map(i->{
            i.setInventoryStatus(inventory.getInventoryStatus());
            i.setQuantity(inventory.getQuantity());
            i.setProduct(inventory.getProduct());
            return repository.save(i);
        });
    }

    @Override
    public void delete(Inventory inventory) {
        repository.delete(inventory);
    }

    @Override
    public Optional<Inventory> findByProduct(Product product) {
        return repository.findByProduct(product);
    }

    @Override
    public boolean isAvailable(Product product, Double quantity) {
        Inventory inventory = repository.findByProduct(product).orElseThrow(() -> new ResourceNotFoundException("Inventory", "product name", product.getName()));
        return inventory.getQuantity() >= quantity;
    }
}
