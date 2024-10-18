package com.saandeepkotte.CatalogManagement.controller;

import com.saandeepkotte.CatalogManagement.dto.CatalogPayload;
import com.saandeepkotte.CatalogManagement.dto.CatalogSearch;
import com.saandeepkotte.CatalogManagement.exceptions.InvalidIdException;
import com.saandeepkotte.CatalogManagement.model.Catalog;
import com.saandeepkotte.CatalogManagement.service.CatalogService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger logger = LogManager.getLogger(CatalogController.class);

    @GetMapping
    public List<Catalog> getCatalogs(@RequestParam(required = false) Optional<Boolean> deleted) {
        return deleted.map(d -> {
            if(d) {
                logger.debug("getting deleted catalogs...");
                return catalogService.getDeletedCatalogs();
            } else {
                logger.debug("getting undeleted catalogs...");
                return catalogService.getUndeletedCatalogs();
            }
        }).orElse(catalogService.getAllCatalogs());
    }

    @PostMapping
    public Catalog createNewCatalog(@Valid @RequestBody CatalogPayload payload) {
        logger.debug("received payload " + payload.toString());
        Catalog catalog = payload.toCatalog();
        catalog.setActiveDate(LocalDateTime.now());
        catalog.setTotalItems(0);
        logger.debug("creating catalog " + catalog.toString());
        return catalogService.addCatalog(catalog);
    }

    @PostMapping("/search")
    public List<Catalog> searchCatalog(@RequestBody CatalogSearch payload) {
        logger.debug("searching by....." + payload.toString());
        return catalogService.separateSearch(payload);
    }

    @PutMapping("/{id}")
    public void updateCatalog(@PathVariable int id, @Valid @RequestBody CatalogPayload payload) throws InvalidIdException {
        logger.debug("received payload " + payload.toString());
        Catalog catalog = payload.toCatalog();
        catalogService.updateCatalog(id, catalog);
        logger.info("successfully updated catalog with id = " + id);
    }

    @DeleteMapping("/{id}")
    public void softDeleteCatalog(@PathVariable int id) throws InvalidIdException {
        catalogService.softDeleteCatalog(id);
        logger.info("deleted catalog with id = "+ id);
    }
}
