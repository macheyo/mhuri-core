package zw.co.macheyo.mhuricore.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import zw.co.macheyo.mhuricore.exception.ResourceNotFoundException;
import zw.co.macheyo.mhuricore.model.Product;
import zw.co.macheyo.mhuricore.modelAssembler.ProductModelAssembler;
import zw.co.macheyo.mhuricore.repository.ProductRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductModelAssembler assembler;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public Product save(Product product, HttpServletRequest httpServletRequest) {
        product.setCreatedBy(httpServletRequest.getUserPrincipal().getName());
        product.setLastModifiedBy(httpServletRequest.getUserPrincipal().getName());
        return productRepository.save(modelMapper.map(product, Product.class));
    }

    @Override
    public Product update(Long id, Product product, HttpServletRequest httpServletRequest) {
        return productRepository.findById(id).map(p->{
            p.setName(product.getName());
            p.setLastModifiedBy(httpServletRequest.getUserPrincipal().getName());
            p.setLastModifiedDate(LocalDateTime.now());
            return productRepository.save(p);})
                .orElseThrow(()->new ResourceNotFoundException("product","id",id));
    }

    @Override
    public List<EntityModel<Product>> findAll() {
        return productRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("product","id",id));
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
