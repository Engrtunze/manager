package com.sales.manager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


@Data
public class CreateProductRequest {
    @NotNull
    private String name;
    @NotNull
    private BigDecimal price;
    private String description;
    @NotNull(message = "quantity cannot be blank")
    private int productQuantity;
}
