package com.saandeepkotte.CatalogManagement.controller;

import com.saandeepkotte.CatalogManagement.dto.CatalogPayload;
import com.saandeepkotte.CatalogManagement.dto.CatalogSearch;
import com.saandeepkotte.CatalogManagement.exceptions.InvalidIdException;
import com.saandeepkotte.CatalogManagement.model.Catalog;
import com.saandeepkotte.CatalogManagement.service.CatalogService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/catalog")
@CrossOrigin(origins = "*")
public class CatalogController {
    @Autowired
    private CatalogService catalogService;

    @GetMapping
    public List<Catalog> getCatalogs(@RequestParam(required = false) Optional<Boolean> deleted) {
        return deleted.map(d -> {
            if(d) {
                return catalogService.getDeletedCatalogs();
            } else {
                return catalogService.getUndeletedCatalogs();
            }
        }).orElse(catalogService.getAllCatalogs());
    }

    @PostMapping
    public Catalog createNewCatalog(@Valid @RequestBody CatalogPayload payload) {
        Catalog catalog = payload.toCatalog();
        catalog.setActiveDate(LocalDateTime.now());
        catalog.setTotalItems(0);
        return catalogService.addCatalog(catalog);
    }

    @PostMapping("/search")
    public List<Catalog> searchCatalog(@RequestBody CatalogSearch payload) {
        return catalogService.separateSearch(payload);
    }

    @PutMapping("/{id}")
    public void updateCatalog(@PathVariable int id, @Valid @RequestBody CatalogPayload payload) throws InvalidIdException {
        Catalog catalog = payload.toCatalog();
        catalogService.updateCatalog(id, catalog);
    }

    @DeleteMapping("/{id}")
    public void softDeleteCatalog(@PathVariable int id) throws InvalidIdException {
        catalogService.softDeleteCatalog(id);
    }
}
