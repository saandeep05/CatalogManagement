package com.saandeepkotte.CatalogManagement.service;

import com.saandeepkotte.CatalogManagement.model.Product;
import com.saandeepkotte.CatalogManagement.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }
}
