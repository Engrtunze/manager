package com.sales.manager.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateProductDto {
    private String name;
    private BigDecimal price;
    private String description;
    private int productQuantity;
}
