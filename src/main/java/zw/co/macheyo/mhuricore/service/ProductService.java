package zw.co.macheyo.mhuricore.service;

import org.springframework.hateoas.EntityModel;
import zw.co.macheyo.mhuricore.model.Product;

import java.util.List;

public interface ProductService {
    Product save(Product product);

    Product update(Long id, Product product);

    List<EntityModel<Product>> findAll();

    Product findById(Long id);


}
