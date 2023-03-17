package com.sales.manager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sales.manager.model.Product;
import com.sales.manager.model.ProductPrice;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class ProductDto {
    private ProductPriceDto productPrice;

    public static ProductDto fromProduct(final  @NotNull Product product){
        ProductPrice currentPrice = null;
        for(ProductPrice price : product.getProductPrices()){
            if(currentPrice == null || price.getCreatedAt().compareTo(currentPrice.getCreatedAt())>0){
                currentPrice = price;
            }
        }
        return ProductDto.builder()
                .productPrice(currentPrice != null ? ProductPriceDto.fromProductPrice(currentPrice): null)
                .build();

    }
}
