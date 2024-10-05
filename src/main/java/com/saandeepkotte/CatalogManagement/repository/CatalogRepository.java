package com.saandeepkotte.CatalogManagement.repository;

import com.saandeepkotte.CatalogManagement.model.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogRepository extends JpaRepository<Catalog, Integer> {
}
