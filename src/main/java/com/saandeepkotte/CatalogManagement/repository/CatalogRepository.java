package com.saandeepkotte.CatalogManagement.repository;

import com.saandeepkotte.CatalogManagement.model.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CatalogRepository extends JpaRepository<Catalog, Integer> {
    public List<Catalog> findAllByName(String name);
    public List<Catalog> findAllByActiveDateGreaterThanEqual(LocalDateTime date);
    public List<Catalog> findAllByTotalItems(int totalItems);

    public List<Catalog> findAllByNameAndActiveDateGreaterThanEqual(String name, LocalDateTime date);

    public List<Catalog> findAllByNameAndActiveDateGreaterThanEqualAndTotalItems(String name, LocalDateTime activeDate, int totalItems);

    List<Catalog> findAllByNameOrActiveDateGreaterThanEqualOrTotalItems(String name, LocalDateTime activeDate, int totalItems);
}