package com.sales.manager.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProductMapperDto {
    private UUID id;
    private String name;
    private String description;
    private int productQuantity;
    private BigDecimal currentPrice;
}
