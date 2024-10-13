package com.saandeepkotte.CatalogManagement.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatalogSearch {
    String name;
    String startDate;
    String endDate;
}
