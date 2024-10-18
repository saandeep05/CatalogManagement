package com.saandeepkotte.CatalogManagement.controller;

import com.saandeepkotte.CatalogManagement.dto.ProductPayload;
import com.saandeepkotte.CatalogManagement.exceptions.InvalidIdException;
import com.saandeepkotte.CatalogManagement.exceptions.InvalidSearchException;
import com.saandeepkotte.CatalogManagement.model.Product;
import com.saandeepkotte.CatalogManagement.service.ProductService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = "*")
public class ProductController {
    @Autowired
    private ProductService productService;
    private static final Logger logger = LogManager.getLogger(ProductController.class);

    @GetMapping
    public List<Product> getProducts(@RequestParam(required = false) Optional<Boolean> deleted) {
        return deleted.map(d -> {
            if(d) {
                logger.debug("getting deleted products...");
                return productService.getDeletedProducts();
            }
            else {
                logger.debug("getting undeleted products...");
                return productService.getUndeletedProducts();
            }
        }).orElse(productService.getAllProducts());
    }

    @PostMapping("/{catalogId}")
    public List<Product> createNewProduct(
            @RequestBody List<@Valid ProductPayload> payloads,
            @PathVariable int catalogId
    ) throws InvalidIdException {
        AtomicBoolean error = new AtomicBoolean(false);
        List<Product> products = new ArrayList<>();

        payloads.stream().forEach(payload -> {
            if(error.get()) return;
            Product product = payload.toProduct();
            try {
                logger.debug("creating product " + product.toString());
                products.add(productService.addProduct(product, catalogId));
                logger.debug("successfully inserted product " + product.toString());
            } catch (InvalidIdException e) {
                logger.error("could not create product " + product.toString());
                error.set(true);
            }
        });

        if(error.get()) {
            throw new InvalidIdException(catalogId);
        }
        logger.info("Successfully created the products");
        return products;
    }

    @GetMapping("/search/{keyword}")
    public List<Product> searchProduct(@PathVariable String keyword) throws InvalidSearchException {
        if(keyword.length() < 3) {
            logger.error("keyword length less than 3");
            throw new InvalidSearchException("Search keyword must be at least 3 characters long");
        }
        return productService.searchProduct(keyword);
    }

    @GetMapping("/{catalogId}")
    public List<Product> getProductsByCatalogId(@PathVariable int catalogId) throws InvalidIdException {
        return productService.getProductByCatalogId(catalogId);
    }

    @PutMapping("/{id}")
    public void updateProduct(@PathVariable int id, @Valid @RequestBody ProductPayload payload) throws InvalidIdException {
        logger.debug("received payload " + payload.toString());
        Product product = payload.toProduct();
        this.productService.updateProduct(id, product);
        logger.info("Successfully updated product with id " + id);
    }

    @DeleteMapping("/{id}")
    public void softDeleteProduct(@PathVariable int id) throws InvalidIdException {
        productService.softDeleteProduct(id);
        logger.info("Successfully deleted product with id " + id);
    }
}
