package com.sales.manager.service;

import com.sales.manager.dto.CreateProductDto;
import com.sales.manager.model.Product;

public interface ProductService {
    Product createProduct(CreateProductDto createProductDto);
}
