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

    @PostMapping("/search/{isStrict}")
    public List<Catalog> searchCatalog(@RequestBody Catalog catalog,
                                       @PathVariable boolean isStrict) throws Exception {
        if(isStrict) {
            if(catalog.getActiveDate() == null ||
                    catalog.getTotalItems() == 0) {
                throw new Exception("Strict Mode: One or more fields are empty");
            }
            return catalogService.strictSearchCatalog(catalog);
        }
        return catalogService.separateSearch(catalog);
    }
}
