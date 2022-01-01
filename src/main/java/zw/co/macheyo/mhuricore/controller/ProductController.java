package zw.co.macheyo.mhuricore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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
    public EntityModel<Product> create(@Valid @RequestBody Product product, HttpServletRequest httpServletRequest) {
        return assembler.toModel(productService.save(product, httpServletRequest));
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
    public EntityModel<Product> update(@PathVariable Long id, @Valid @RequestBody Product product, HttpServletRequest httpServletRequest){
        Product updatedProduct = productService.update(id, product, httpServletRequest);
        return assembler.toModel(updatedProduct);
    }
}
