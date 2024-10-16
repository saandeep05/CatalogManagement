package com.saandeepkotte.CatalogManagement.dto;

import com.saandeepkotte.CatalogManagement.model.Product;
import com.saandeepkotte.CatalogManagement.utils.Currency;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductPayload {
    @NotBlank(message = "Product name cannot be blank")
    private String name;
    @NotBlank(message = "category cannot be blank")
    private String category;
    @Min(value = 1, message = "price cannot be less than 1")
    private int price;
    @NotNull(message = "currency is required")
    private Currency currency;
    private String longDescription;
    private String shortDescription;

    public Product toProduct() {
        Product product = new Product();
        product.setName(name);
        product.setCategory(category);
        product.setPrice(price);
        product.setCurrency(currency);
        product.setLongDescription(longDescription);
        product.setShortDescription(shortDescription);
        return product;
    }
}
