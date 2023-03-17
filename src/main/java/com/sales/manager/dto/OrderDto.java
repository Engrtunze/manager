package com.sales.manager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
@JsonSerialize
public class OrderDto{
    private String customerName;
    private String customerPhoneNumber;
    private int quantity;
    private BigDecimal unitPriceAtOrder;
    private BigDecimal totalUnitPriceAtOrder;
    private String name;
    private String description;

}
