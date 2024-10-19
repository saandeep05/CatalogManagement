package com.saandeepkotte.CatalogManagement.service;

import com.saandeepkotte.CatalogManagement.exceptions.InvalidIdException;
import com.saandeepkotte.CatalogManagement.model.Catalog;
import com.saandeepkotte.CatalogManagement.model.Product;
import com.saandeepkotte.CatalogManagement.repository.CatalogRepository;
import com.saandeepkotte.CatalogManagement.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger logger = LogManager.getLogger(ProductService.class);

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional
    public Product addProduct(Product product, int catalogId) throws InvalidIdException {
        logger.debug("adding product...");
        Optional<Catalog> catalog = catalogRepository.findById(catalogId);
        if(catalog.isEmpty()) {
            throw new InvalidIdException(catalogId);
        }
        catalog.ifPresent(c -> {
            logger.info("catalog found");
            product.setCatalog(c);
            c.setTotalItems(c.getTotalItems()+1);
            catalogRepository.save(c);
            logger.debug("catalog saved with updated totalItems " + c);
        });
        Product savedProduct = productRepository.save(product);
        logger.debug("product saved " + product);
        return savedProduct;
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

    @Transactional
    public void softDeleteProduct(int id) throws InvalidIdException {
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()) {
            throw new InvalidIdException(id);
        }
        product.ifPresent(p -> {
            p.setDeletedAt(LocalDateTime.now());
            productRepository.save(p);
            logger.debug("deleted product " + p);
            Optional<Catalog> catalog = catalogRepository.findById(p.getCatalog().getId());
            catalog.ifPresent(c -> {
                c.setTotalItems(c.getTotalItems()-1);
                catalogRepository.save(c);
                logger.debug("reduced totalItems of catalog " + c);
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
            logger.debug("updated product " + product);
        });
    }
}
