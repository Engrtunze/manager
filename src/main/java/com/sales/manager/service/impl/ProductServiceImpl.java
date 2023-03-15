package com.sales.manager.service.impl;

import com.sales.manager.dto.CreateProductDto;
import com.sales.manager.exceptions.DuplicateRecordException;
import com.sales.manager.model.Product;
import com.sales.manager.repository.ProductRepository;
import com.sales.manager.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Product createProduct(CreateProductDto createProductDto) {
        Optional<Product> productName = productRepository.findByName(createProductDto.getName());
        if(productName.isPresent()){
            throw new DuplicateRecordException(String.format("A createProductDto with the name %s exist", createProductDto.getName()));
        }

        var product = new Product();
        product.setName(createProductDto.getName());
        product.setPrice(createProductDto.getPrice());
        product.setProductQuantity(createProductDto.getProductQuantity());
        product.setDescription(createProductDto.getDescription());
        return productRepository.save(product);
    }
}
