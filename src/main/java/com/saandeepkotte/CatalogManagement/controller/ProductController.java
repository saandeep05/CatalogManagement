package com.saandeepkotte.CatalogManagement.controller;

import com.saandeepkotte.CatalogManagement.dto.ProductPayload;
import com.saandeepkotte.CatalogManagement.exceptions.InvalidIdException;
import com.saandeepkotte.CatalogManagement.exceptions.InvalidSearchException;
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
    public Product createNewProduct(
            @RequestBody ProductPayload payload,
            @PathVariable int catalogId
    ) throws InvalidIdException {
        Product product = payload.toProduct();
        return productService.addProduct(product, catalogId);
    }

    @GetMapping("/search/{keyword}")
    public List<Product> searchProduct(@PathVariable String keyword) throws InvalidSearchException {
        if(keyword.length() < 3) throw new InvalidSearchException("Search keyword must be at least 3 characters long");
        return productService.searchProduct(keyword);
    }

    @GetMapping("/{catalogId}")
    public List<Product> getProductsByCatalogId(@PathVariable int catalogId) throws InvalidIdException {
        return productService.getProductByCatalogId(catalogId);
    }

    @DeleteMapping("/{id}")
    public void softDeleteProduct(@PathVariable int id) throws InvalidIdException {
        productService.softDeleteProduct(id);
    }
}
