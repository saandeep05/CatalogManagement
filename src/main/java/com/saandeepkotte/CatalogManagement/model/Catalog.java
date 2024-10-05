package com.saandeepkotte.CatalogManagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "catalog")
public class Catalog {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @NonNull
    private String name;
    private Date activeDate;
    private int totalItems;
    @OneToMany(mappedBy = "catalog",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            targetEntity = Product.class)
    private List<Product> products;
}
