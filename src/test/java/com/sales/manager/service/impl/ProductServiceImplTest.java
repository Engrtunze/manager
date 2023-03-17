package com.sales.manager.service.impl;


import com.sales.manager.dto.CreateProductPriceDto;
import com.sales.manager.dto.CreateProductRequest;
import com.sales.manager.dto.UpdateProductRequest;
import com.sales.manager.exceptions.DuplicateRecordException;
import com.sales.manager.exceptions.NotFoundException;
import com.sales.manager.model.Product;
import com.sales.manager.model.ProductPrice;
import com.sales.manager.repository.ProductPriceRepository;
import com.sales.manager.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductPriceRepository productPriceRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testFindById() {
        UUID productId = UUID.randomUUID();
        var product = new Product();
        product.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Optional<Product> result = productService.findById(productId);

        assertEquals(productId, result.get().getId());
    }

    @Test
    void testSaveProduct() {
        Product product = new Product();

        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.saveProduct(product);

        assertEquals(product, result);
    }

    @Test
    void testCreateProduct_duplicate() {
        String productName = "testProduct";
        BigDecimal productPrice = new BigDecimal("100.00");

        CreateProductRequest request = new CreateProductRequest();
        request.setName(productName);
        request.setCurrentPrice(productPrice);

        when(productPriceRepository.findByCurrentPriceAndProductName(productPrice, productName)).thenReturn(Optional.of(new ProductPrice()));

        assertThrows(DuplicateRecordException.class, () -> productService.createProduct(request));
    }


    @Test
    void testCreateProduct() {
        String productName = "testProduct";
        BigDecimal productPrice = new BigDecimal("100.00");

        CreateProductRequest request = new CreateProductRequest();
        request.setName(productName);
        request.setCurrentPrice(productPrice);

        when(productPriceRepository.findByCurrentPriceAndProductName(productPrice, productName)).thenReturn(Optional.empty());

        var product = new Product();
        product.setName(productName);
        product.setProductQuantity(0);

        when(productRepository.save(any())).thenReturn(product);

        var productPriceEntity = new ProductPrice();
        productPriceEntity.setProduct(product);
        productPriceEntity.setCurrentPrice(productPrice);
        productPriceEntity.setOldPrice(productPrice);
        productPriceEntity.setLatestPrice(productPrice);

        when(productPriceRepository.save(any())).thenReturn(productPriceEntity);

        CreateProductPriceDto result = productService.createProduct(request);

        assertEquals(product.getId(), result.getId());
        assertEquals(productName, result.getName());
        assertEquals(product.getProductQuantity(), result.getProductQuantity());
        assertEquals(productPrice, result.getCurrentPrice());
        assertEquals(productPrice, result.getOldPrice());
        assertEquals(productPrice, result.getLatestPrice());
        assertNull(result.getDescription());
    }

    @Test
    void testUpdateProduct_notFound() {
        UUID productId = UUID.randomUUID();
        UpdateProductRequest request = new UpdateProductRequest();

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            productService.updateProduct(productId, request);
        });
    }

    @Test
    void testUpdateProduct() {
        UUID productId = UUID.randomUUID();
        String productName = "testProduct";
        String productDescription = "testDescription";
        int productQuantity = 10;

        UpdateProductRequest request = new UpdateProductRequest();
        request.setName(productName);
        request.setDescription(productDescription);
        request.setProductQuantity(productQuantity);

        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setName("oldName");
        existingProduct.setDescription("oldDescription");
        existingProduct.setProductQuantity(1);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any())).thenReturn(existingProduct);

        var result = productService.updateProduct(productId, request);

        assertEquals(productId, result.getId());
        assertEquals(productName, result.getName());
        assertEquals(productDescription, result.getDescription());
        assertEquals(productQuantity, result.getProductQuantity());
    }
}
