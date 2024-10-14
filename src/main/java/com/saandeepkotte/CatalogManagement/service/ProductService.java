package com.saandeepkotte.CatalogManagement.service;

import com.saandeepkotte.CatalogManagement.model.Catalog;
import com.saandeepkotte.CatalogManagement.model.Product;
import com.saandeepkotte.CatalogManagement.repository.CatalogRepository;
import com.saandeepkotte.CatalogManagement.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CatalogRepository catalogRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product addProduct(Product product, int catalogId) {
        Catalog catalog = catalogRepository.findById(catalogId).orElseThrow();
        product.setCatalog(catalog);
        catalog.setTotalItems(catalog.getTotalItems()+1);
        catalogRepository.save(catalog);
        return productRepository.save(product);
    }

    public List<Product> searchProduct(String keyword) {
        return productRepository.findByNameStartingWith(keyword);
    }

    public List<Product> getProductByCatalogId(int catalogId) {
        return productRepository.findByCatalogId(catalogId);
    }

    public void softDeleteProduct(int id) {
        Optional<Product> product = productRepository.findById(id);
        product.ifPresent(p -> {
            p.setDeletedAt(LocalDateTime.now());
            productRepository.save(p);
        });
    }

    public List<Product> getDeletedProducts() {
        return productRepository.findByDeletedAtIsNotNull();
    }

    public List<Product> getUndeletedProducts() {
        return productRepository.findByDeletedAtIsNull();
    }
}
