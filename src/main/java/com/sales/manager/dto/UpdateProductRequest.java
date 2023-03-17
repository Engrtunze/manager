package com.sales.manager.dto;

import lombok.Data;


@Data
public class UpdateProductRequest {
    private String name;
    private String description;
    private int productQuantity;
}
