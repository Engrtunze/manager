package com.sales.manager.service.impl;

import com.sales.manager.dto.CreateProductPriceDto;
import com.sales.manager.dto.CreateProductRequest;
import com.sales.manager.dto.ProductDto;
import com.sales.manager.dto.ProductPriceDto;
import com.sales.manager.dto.UpdateProductPriceRequest;
import com.sales.manager.dto.UpdateProductRequest;
import com.sales.manager.exceptions.DuplicateRecordException;
import com.sales.manager.exceptions.NotFoundException;
import com.sales.manager.model.Product;
import com.sales.manager.model.ProductPrice;
import com.sales.manager.repository.ProductPriceRepository;
import com.sales.manager.repository.ProductRepository;
import com.sales.manager.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductPriceRepository productPriceRepository;

    @Override
    public @NotNull Optional<Product> findById(UUID id) {
        return productRepository.findById(id);

    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
    @Transactional
    @Override
    public CreateProductPriceDto createProduct(CreateProductRequest createProductRequest) {
        Optional<ProductPrice> productName = productPriceRepository.findByCurrentPriceAndProductName(createProductRequest.getCurrentPrice(), createProductRequest.getName());
        if (productName.isPresent()) {
            throw new DuplicateRecordException(String.format("A product with the name %s and price %s exist already", createProductRequest.getName(), createProductRequest.getCurrentPrice()));
        }

        var product = Product.builder()
                .name(createProductRequest.getName())
                .productQuantity(createProductRequest.getProductQuantity())
                .description(createProductRequest.getDescription())
                .build();
      productRepository.save(product);
        var productPrice = ProductPrice.builder()
                        .currentPrice(createProductRequest.getCurrentPrice())
                                .latestPrice(createProductRequest.getCurrentPrice())
                                        .oldPrice(createProductRequest.getCurrentPrice())
                .product(product)
                                                .build();

        productPriceRepository.save(productPrice);
        return CreateProductPriceDto.builder()
                .id(product.getId())
                .productQuantity(product.getProductQuantity())
                .name(product.getName())
                .oldPrice(productPrice.getOldPrice())
                .latestPrice(productPrice.getLatestPrice())
                .description(product.getDescription())
                .currentPrice(productPrice.getCurrentPrice())
                .build();
    }
    @Transactional
    @Override
    public ProductPriceDto updateProduct(UUID id, UpdateProductRequest request) {
        var existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product does not exist"));
        existingProduct.setName(request.getName());
        existingProduct.setProductQuantity(request.getProductQuantity());
        existingProduct.setDescription(request.getDescription());
         productRepository.save(existingProduct);
         return   ProductPriceDto.builder()
                  .id(existingProduct.getId())
                  .productQuantity(existingProduct.getProductQuantity())
                  .name(existingProduct.getName())
                  .description(existingProduct.getDescription())
                  .build();
    }

    @Override
    public CreateProductPriceDto updateProductPrice(UUID id, UpdateProductPriceRequest request) {
        var product = productPriceRepository.findAllByProduct_Id(id).stream().findFirst().orElseThrow(()-> new NotFoundException("The product does not exist"));


        var productPrice = ProductPrice.builder()
                        .product(product.getProduct())
                                .latestPrice(request.getLatestPrice())
                                        .oldPrice(request.getOldPrice())
                                                .currentPrice(request.getLatestPrice())
                .build();
        productPriceRepository.save(productPrice);

        return CreateProductPriceDto.builder()
                .id(product.getId())
                .productQuantity(product.getProduct().getProductQuantity())
                .name(product.getProduct().getName())
                .oldPrice(productPrice.getOldPrice())
                .latestPrice(productPrice.getLatestPrice())
                .description(product.getProduct().getDescription())
                .currentPrice(productPrice.getCurrentPrice())
                .build();


    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductDto> getAllAvailableProduct() {
    var products =    productRepository.findAllByProductQuantityGreaterThan(0);
    return products.stream().map(ProductDto::fromProduct).toList();
    }
}
