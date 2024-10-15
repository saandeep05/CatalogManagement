package com.saandeepkotte.CatalogManagement.service;

import com.saandeepkotte.CatalogManagement.exceptions.InvalidIdException;
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

    public Product addProduct(Product product, int catalogId) throws InvalidIdException {
        Optional<Catalog> catalog = catalogRepository.findById(catalogId);
        if(catalog.isEmpty()) {
            throw new InvalidIdException(catalogId);
        }
        catalog.ifPresent(c -> {
            product.setCatalog(c);
            c.setTotalItems(c.getTotalItems()+1);
            catalogRepository.save(c);
        });
        return productRepository.save(product);
    }

    public List<Product> searchProduct(String keyword) {
        return productRepository.findByNameStartingWith(keyword);
    }

    public List<Product> getProductByCatalogId(int catalogId) throws InvalidIdException {
        Optional<Catalog> catalog = catalogRepository.findById(catalogId);
        if(catalog.isEmpty()) {
            throw new InvalidIdException(catalogId);
        }
        return productRepository.findByCatalogId(catalogId);
    }
    public void softDeleteProduct(int id) throws InvalidIdException {
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()) {
            throw new InvalidIdException(id);
        }
        product.ifPresent(p -> {
            p.setDeletedAt(LocalDateTime.now());
            productRepository.save(p);
            Optional<Catalog> catalog = catalogRepository.findById(p.getCatalog().getId());
            catalog.ifPresent(c -> {
                c.setTotalItems(c.getTotalItems()-1);
                catalogRepository.save(c);
            });
        });
    }

    public List<Product> getDeletedProducts() {
        return productRepository.findByDeletedAtIsNotNull();
    }

    public List<Product> getUndeletedProducts() {
        return productRepository.findByDeletedAtIsNull();
    }

    public void updateProduct(int id, Product product) throws InvalidIdException {
        Optional<Product> productFetched = productRepository.findById(id);
        if(productFetched.isEmpty()) {
            throw new InvalidIdException(id);
        }
        productFetched.ifPresent(p -> {
            product.setId(id);
            product.setCatalog(p.getCatalog());
            productRepository.save(product);
        });
    }
}
