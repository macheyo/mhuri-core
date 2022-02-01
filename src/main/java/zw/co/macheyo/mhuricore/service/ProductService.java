package zw.co.macheyo.mhuricore.service;

import org.springframework.hateoas.EntityModel;
import zw.co.macheyo.mhuricore.model.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ProductService {

    Product save(Product product, HttpServletRequest httpServletRequest);

    Product update(Long id, Product product, HttpServletRequest httpServletRequest);

    List<EntityModel<Product>> findAll();

    Product findById(Long id);

    void deleteById(Long id);
}
