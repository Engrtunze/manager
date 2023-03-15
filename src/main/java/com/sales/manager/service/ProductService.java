package com.sales.manager.service;

import com.sales.manager.dto.CreateProductRequest;
import com.sales.manager.model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    Product createProduct(CreateProductRequest createProductRequest);
    Product updateProduct(UUID id, CreateProductRequest createProductRequest);
    List<Product> getAllAvailableProduct();
}
