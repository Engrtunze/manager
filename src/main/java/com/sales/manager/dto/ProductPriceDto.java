package com.sales.manager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sales.manager.model.ProductPrice;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class ProductPriceDto {
    private UUID id;
    private BigDecimal currentPrice;
    private String name;
    private String description;
    private int productQuantity;

    public static ProductPriceDto fromProductPrice(final  @NotNull ProductPrice price){
        return ProductPriceDto.builder()
                .id(price.getProduct().getId())
                .currentPrice(price.getCurrentPrice())
                .name(price.getProduct().getName())
                .description(price.getProduct().getDescription())
                .productQuantity(price.getProduct().getProductQuantity())
                .build();

    }
}
