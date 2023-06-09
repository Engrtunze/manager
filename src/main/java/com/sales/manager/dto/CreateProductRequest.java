package com.sales.manager.dto;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class CreateProductRequest {
    private String name;
    private BigDecimal currentPrice;
    private String description;
    private int productQuantity;
}
