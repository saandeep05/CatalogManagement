package com.saandeepkotte.CatalogManagement.repository;

import com.saandeepkotte.CatalogManagement.model.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface CatalogRepository extends JpaRepository<Catalog, Integer> {
    public List<Catalog> findAllByName(String name);
    public List<Catalog> findAllByActiveDateGreaterThanEqual(Date date);
    public List<Catalog> findAllByTotalItems(int totalItems);

    public List<Catalog> findAllByNameAndActiveDateGreaterThanEqual(String name, Date date);

    public List<Catalog> findAllByNameAndActiveDateGreaterThanEqualAndTotalItems(String name, Date activeDate, int totalItems);

    List<Catalog> findAllByNameOrActiveDateGreaterThanEqualOrTotalItems(String name, Date activeDate, int totalItems);
}