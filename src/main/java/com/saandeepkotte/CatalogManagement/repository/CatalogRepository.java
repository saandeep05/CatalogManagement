package com.saandeepkotte.CatalogManagement.repository;

import com.saandeepkotte.CatalogManagement.model.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CatalogRepository extends JpaRepository<Catalog, Integer> {
    public List<Catalog> findAllByName(String name);

    @Query("SELECT c FROM Catalog c WHERE c.activeDate BETWEEN :startDate AND :endDate")
    public List<Catalog> findByActiveDateGreaterThanEqualAndLessThanEqual(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT c FROM Catalog c WHERE c.name = :name AND (c.activeDate BETWEEN :startDate AND :endDate)")
    public List<Catalog> findByNameAndActiveDateGreaterThanEqualAndLessThanEqual(@Param("name") String name, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    public List<Catalog> findAllByNameAndActiveDateGreaterThanEqual(String name, LocalDateTime date);

    public List<Catalog> findAllByNameAndActiveDateGreaterThanEqualAndTotalItems(String name, LocalDateTime activeDate, int totalItems);

    List<Catalog> findAllByNameOrActiveDateGreaterThanEqualOrTotalItems(String name, LocalDateTime activeDate, int totalItems);
}