package com.saandeepkotte.CatalogManagement.dto;

import com.saandeepkotte.CatalogManagement.model.Catalog;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CatalogPayload {
    @NotBlank(message = "Catalog name must not be blank")
    private String name;

    public Catalog toCatalog() {
        Catalog catalog = new Catalog();
        catalog.setName(name);
        return catalog;
    }
}
