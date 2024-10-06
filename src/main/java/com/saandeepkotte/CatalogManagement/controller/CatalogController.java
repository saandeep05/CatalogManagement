package com.saandeepkotte.CatalogManagement.controller;

import com.saandeepkotte.CatalogManagement.model.Catalog;
import com.saandeepkotte.CatalogManagement.model.Product;
import com.saandeepkotte.CatalogManagement.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/api/catalog")
public class CatalogController {
    @Autowired
    private CatalogService catalogService;

    @GetMapping
    public List<Catalog> getCatalogs() {
        return catalogService.getAllCatalogs();
    }

    @PostMapping
    public Catalog createNewCatalog(@RequestBody Catalog catalog) {
        catalog.setActiveDate(new Date());
        catalog.setTotalItems(0);
        return catalogService.addCatalog(catalog);
    }
}
