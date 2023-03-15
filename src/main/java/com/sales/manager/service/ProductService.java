package com.sales.manager.service;

import com.sales.manager.dto.CreateProductRequest;
import com.sales.manager.model.Product;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {
    Product createProduct(CreateProductRequest createProductRequest);
    Product updateProduct(UUID id, CreateProductRequest createProductRequest);
    List<Product> getAllAvailableProduct();
    @NotNull Optional<Product> findById(UUID id);
    Product saveProduct (Product product);
}
