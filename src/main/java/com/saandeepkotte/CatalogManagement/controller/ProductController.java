package com.saandeepkotte.CatalogManagement.controller;

import com.saandeepkotte.CatalogManagement.model.Product;
import com.saandeepkotte.CatalogManagement.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getProducts() {
        return productService.getAllProducts();
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
}
