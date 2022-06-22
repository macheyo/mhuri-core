package zw.co.macheyo.mhuricore.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import zw.co.macheyo.mhuricore.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    ProductRepository repository;
    @Autowired
    ProductModelAssembler assembler;


    @Override
    public Optional<Product> findById(Object id) {
        return repository.findById((Long) id);
    }

    @Override
    public List<EntityModel<Product>> findAll() {
        return repository.findAll()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Product> create(Product product) {
        return Optional.of(repository.save(product));
    }

    @Override
    public Optional<Product> update(Product product) {
        return repository.findById(product.getId()).map(p ->{
            p.setName(product.getName());
            p.setPrice(product.getPrice());
            return repository.save(p);
        });
    }

    @Override
    public void delete(Product product) {
        repository.delete(product);
    }
}
