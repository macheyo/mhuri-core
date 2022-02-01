package zw.co.macheyo.mhuricore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.macheyo.mhuricore.model.Product;
import zw.co.macheyo.mhuricore.modelAssembler.ProductModelAssembler;
import zw.co.macheyo.mhuricore.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    ProductModelAssembler assembler;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@Valid @RequestBody Product product, HttpServletRequest httpServletRequest) {
        EntityModel<Product> entityModel = assembler.toModel(productService.save(product, httpServletRequest));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/list")
    public CollectionModel<EntityModel<Product>> list() {
        return CollectionModel.of(productService.findAll(), linkTo(methodOn(ProductController.class).list()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Product> getById(@PathVariable Long id) {
        Product product = productService.findById(id);
        return assembler.toModel(product);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Product product, HttpServletRequest httpServletRequest){
        Product updatedProduct = productService.update(id, product, httpServletRequest);
        EntityModel<Product> entityModel = assembler.toModel(updatedProduct);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteProduct(@PathVariable Long id){
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
