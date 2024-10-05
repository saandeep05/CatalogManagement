package com.saandeepkotte.CatalogManagement.repository;

import com.saandeepkotte.CatalogManagement.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
