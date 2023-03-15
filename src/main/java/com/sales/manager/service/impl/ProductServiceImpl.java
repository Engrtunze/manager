package com.sales.manager.service.impl;

import com.sales.manager.dto.CreateProductRequest;
import com.sales.manager.exceptions.DuplicateRecordException;
import com.sales.manager.exceptions.NotFoundException;
import com.sales.manager.model.Product;
import com.sales.manager.repository.ProductRepository;
import com.sales.manager.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Product createProduct(CreateProductRequest createProductRequest) {
        Optional<Product> productName = productRepository.findByNameAndPrice(createProductRequest.getName(), createProductRequest.getPrice());
        if (productName.isPresent()) {
            throw new DuplicateRecordException(String.format("A product with the name %s and price %s exist already", createProductRequest.getName(), createProductRequest.getPrice()));
        }

        var product = new Product();
        product.setName(createProductRequest.getName());
        product.setPrice(createProductRequest.getPrice());
        product.setProductQuantity(createProductRequest.getProductQuantity());
        product.setDescription(createProductRequest.getDescription());
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(UUID id, CreateProductRequest createProductRequest) {
        var existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product does not exist"));
        existingProduct.setName(createProductRequest.getName());
        existingProduct.setProductQuantity(createProductRequest.getProductQuantity());
        existingProduct.setPrice(createProductRequest.getPrice());
        existingProduct.setDescription(createProductRequest.getDescription());
        return productRepository.save(existingProduct);
    }

    @Override
    public List<Product> getAllAvailableProduct() {
       return productRepository.findAllByProductQuantityGreaterThan(0);
    }
}
