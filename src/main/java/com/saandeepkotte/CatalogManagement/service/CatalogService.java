package com.saandeepkotte.CatalogManagement.service;

import com.saandeepkotte.CatalogManagement.model.Catalog;
import com.saandeepkotte.CatalogManagement.repository.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
}
