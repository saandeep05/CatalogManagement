package com.saandeepkotte.CatalogManagement.controller;

import com.saandeepkotte.CatalogManagement.model.Product;
import com.saandeepkotte.CatalogManagement.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = "*")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getProducts(@RequestParam(required = false) Optional<Boolean> deleted) {
        return deleted.map(d -> {
            if(d) {
                return productService.getDeletedProducts();
            }
            else {
                return productService.getUndeletedProducts();
            }
        }).orElse(productService.getAllProducts());
    }

    @PostMapping("/{catalogId}")
    public Product createNewProduct(@RequestBody Product product, @PathVariable int catalogId) {
        return productService.addProduct(product, catalogId);
    }

    @GetMapping("/search/{keyword}")
    public List<Product> searchProduct(@PathVariable String keyword) throws Exception {
        if(keyword.length() < 3) throw new Exception("Search keyword must have length > 3");
        return productService.searchProduct(keyword);
    }

    @GetMapping("/{catalogId}")
    public List<Product> getProductsByCatalogId(@PathVariable int catalogId) {
        return productService.getProductByCatalogId(catalogId);
    }

    @DeleteMapping("/{id}")
    public void softDeleteProduct(@PathVariable int id) {
        productService.softDeleteProduct(id);
    }
}
