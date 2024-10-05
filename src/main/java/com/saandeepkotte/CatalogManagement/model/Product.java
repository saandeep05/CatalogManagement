package com.saandeepkotte.CatalogManagement.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @NonNull
    private String name;
    private String category;
    private String longDescription;
    @NonNull
    private String shortDescription;
    @NonNull
    private int price;
}
