package zw.co.macheyo.mhuricore.service.product;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.macheyo.mhuricore.exception.BusinessException;
import zw.co.macheyo.mhuricore.exception.ResourceNotFoundException;
import zw.co.macheyo.mhuricore.model.Product;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService service;
    @Autowired
    ProductModelAssembler assembler;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<EntityModel<Product>> create(@Valid @RequestBody ProductDTO product) {
        EntityModel<Product> entityModel = assembler.toModel(service.create(modelMapper.map(product, Product.class)).orElseThrow(()->new BusinessException("An error occurred creating product")));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/list")
    public CollectionModel<EntityModel<Product>> list() {
        return CollectionModel.of(service.findAll(), linkTo(methodOn(ProductController.class).list()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Product>> getById(@PathVariable Long id) {
        EntityModel<Product> entityModel = assembler.toModel(service.findById(id).orElseThrow(()->new ResourceNotFoundException("Product","id",id)));
        return ResponseEntity
                .ok(entityModel);

    }

    @PutMapping
    public ResponseEntity<EntityModel<Product>> update(@Valid @RequestBody ProductDTO product){
        Product updatedProduct = service.update(modelMapper.map(product,Product.class)).orElseThrow(()->new ResourceNotFoundException("Product","id",product.getId()));
        EntityModel<Product> entityModel = assembler.toModel(updatedProduct);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping
    ResponseEntity<EntityModel<Product>> delete(@Valid @RequestBody ProductDTO product){
        service.delete(modelMapper.map(product,Product.class));
        return ResponseEntity.noContent().build();
    }
}
