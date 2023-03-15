package com.sales.manager.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class OrderRequest {
    @NotNull(message = "Quantity cannot be let blank")
    private int quantity;
    @NotBlank(message = "Customer name is required")
    private String customerName;
    @NotBlank(message = "Customer number is required")
    private String customerPhoneNumber;
    private UUID productId;
}
