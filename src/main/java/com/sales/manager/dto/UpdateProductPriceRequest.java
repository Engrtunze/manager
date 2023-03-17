package com.sales.manager.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductPriceRequest {
    private BigDecimal oldPrice;
    private BigDecimal latestPrice;
}
