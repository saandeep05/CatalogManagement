package com.saandeepkotte.CatalogManagement.controller;

import com.saandeepkotte.CatalogManagement.model.Catalog;
import com.saandeepkotte.CatalogManagement.model.Product;
import com.saandeepkotte.CatalogManagement.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api/catalog")
@CrossOrigin(origins = "*")
public class CatalogController {
    @Autowired
    private CatalogService catalogService;

    @GetMapping
    public List<Catalog> getCatalogs() {
        return catalogService.getAllCatalogs();
    }

    @PostMapping
    public Catalog createNewCatalog(@RequestBody Catalog catalog) {
        catalog.setActiveDate(LocalDateTime.now());
        catalog.setTotalItems(0);
        return catalogService.addCatalog(catalog);
    }

    @PostMapping("/search")
    public List<Catalog> searchCatalog(@RequestBody CatalogSearch payload) {
        return catalogService.separateSearch(payload);
    }

    @PutMapping
    public void updateCatalog(@RequestBody Catalog payload) {
        catalogService.updateCatalog(payload);
    }
}
