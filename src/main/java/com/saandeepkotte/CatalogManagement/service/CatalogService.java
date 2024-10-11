package com.saandeepkotte.CatalogManagement.service;

import com.saandeepkotte.CatalogManagement.model.Catalog;
import com.saandeepkotte.CatalogManagement.repository.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CatalogService {
    @Autowired
    private CatalogRepository catalogRepository;

    public List<Catalog> getAllCatalogs() {
        return catalogRepository.findAll();
    }

    public Catalog addCatalog(Catalog catalog) {
        return catalogRepository.save(catalog);
    }

    public List<Catalog> strictSearchCatalog(Catalog catalog) {
        return catalogRepository.findAllByNameAndActiveDateGreaterThanEqualAndTotalItems(
                catalog.getName(),
                catalog.getActiveDate(),
                catalog.getTotalItems()
        );
    }

    public List<Catalog> casualSearchCatalog(Catalog catalog) {
        String name = catalog.getName();
        LocalDateTime activeDate = catalog.getActiveDate();
        int totalItems = catalog.getTotalItems();

        if(activeDate == null && totalItems == 0) {
            return catalogRepository.findAllByName(name);
        }
        else if(totalItems == 0) {
            return catalogRepository.findAllByNameAndActiveDateGreaterThanEqual(name, activeDate);
        }
        return catalogRepository.findAllByNameOrActiveDateGreaterThanEqualOrTotalItems(
                name,
                activeDate,
                totalItems
        );
    }

    public List<Catalog> separateSearch(Catalog catalog) {
        String name = catalog.getName();
        LocalDateTime activeDate = catalog.getActiveDate();
        int totalItems = catalog.getTotalItems();

        if(name == null && activeDate == null) {
            return catalogRepository.findAllByTotalItems(totalItems);
        } else if (activeDate == null && totalItems == 0) {
            return catalogRepository.findAllByName(name);
        } else {
            return catalogRepository.findAllByActiveDateGreaterThanEqual(activeDate);
        }
    }
}