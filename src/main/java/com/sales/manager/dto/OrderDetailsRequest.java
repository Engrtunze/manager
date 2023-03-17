package com.sales.manager.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderDetailsRequest {
    private int quantity;
    private UUID productId;
}
