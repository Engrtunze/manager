package com.sales.manager.model;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@Table(name = "products")
public class Product extends AbstractAuditable {
    @Column(name = "name", nullable = false)
    private String name;
    private String description;
    @Column(name = "quantity_in_stock", nullable = false)
    private int productQuantity;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductPrice> productPrices;

    @Override
    public String toString() {
        return "Product{" +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", productPrices=" + productPrices.stream().map(ProductPrice::getCurrentPrice).toList() + // only log the list of prices
                ", productQuantity=" + productQuantity +
                '}';
    }
}
