package com.saandeepkotte.CatalogManagement.service;

import com.saandeepkotte.CatalogManagement.controller.CatalogSearch;
import com.saandeepkotte.CatalogManagement.model.Catalog;
import com.saandeepkotte.CatalogManagement.repository.CatalogRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CatalogService {
    @Autowired
    private CatalogRepository catalogRepository;
    @Autowired
    private EntityManager entityManager;

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

    public List<Catalog> separateSearch(CatalogSearch payload) {
        String name = payload.getName();
        String startDate = payload.getStartDate();
        String endDate = payload.getEndDate();

        if(name == null || name.equals("")) {

            return catalogRepository.findByActiveDateGreaterThanEqualAndLessThanEqual(extractDateTime(startDate), extractDateTime(endDate));
        }
        else if(endDate.equals(startDate)) {
            return catalogRepository.findAllByName(name);
        } else {
            return catalogRepository.findByNameAndActiveDateGreaterThanEqualAndLessThanEqual(name, extractDateTime(startDate), extractDateTime(endDate));
//            return catalogRepository.findByActiveDateGreaterThanEqualAndLessThanEqual(extractDateTime(startDate), extractDateTime(endDate));
        }
    }

    private LocalDateTime extractDateTime(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateString, formatter);
        LocalDateTime dateTime = date.atStartOfDay();
        return dateTime;
    }

    public void updateCatalog(Catalog payload) {
        catalogRepository.updateCatalog(payload.getId(), payload.getName());
    }
}