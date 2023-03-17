package com.sales.manager.service;

import com.sales.manager.dto.CreateProductPriceDto;
import com.sales.manager.dto.CreateProductRequest;
import com.sales.manager.dto.ProductDto;
import com.sales.manager.dto.ProductPriceDto;
import com.sales.manager.dto.UpdateProductPriceRequest;
import com.sales.manager.dto.UpdateProductRequest;
import com.sales.manager.model.Product;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {
    CreateProductPriceDto createProduct(CreateProductRequest createProductRequest);
    ProductPriceDto updateProduct(UUID id, UpdateProductRequest request);
    CreateProductPriceDto updateProductPrice(UUID id, UpdateProductPriceRequest request);
    List<ProductDto> getAllAvailableProduct();
    @NotNull Optional<Product> findById(UUID id);
    Product saveProduct (Product product);
}
