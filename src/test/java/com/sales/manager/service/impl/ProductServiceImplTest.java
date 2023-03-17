//package com.sales.manager.service.impl;
//
//import com.sales.manager.dto.CreateProductRequest;
//import com.sales.manager.exceptions.DuplicateRecordException;
//import com.sales.manager.model.Product;
//import com.sales.manager.repository.ProductRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.math.BigDecimal;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//class ProductServiceImplTest {
//    @Mock
//    ProductRepository productRepository;
//    @InjectMocks
//    ProductServiceImpl productService;
//
//    CreateProductRequest createProductRequest;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//         createProductRequest = new CreateProductRequest();
//    }
//
//    @Test
//    void testCreateProduct_whenProductNameAndPriceAlreadyExists_shouldReturnDuplicateRecordException() {
//
//        String name = "AppleWatch";
//        BigDecimal price = BigDecimal.valueOf(999.99);
//        createProductRequest.setName(name);
//        createProductRequest.setPrice(price);
//        createProductRequest.setProductQuantity(3);
//        createProductRequest.setDescription("An Apple watch");
//
//
//        when(productRepository.findByNameAndPrice(createProductRequest.getName(), createProductRequest.getPrice())).thenReturn(Optional.of(mock(Product.class)));
//
//
//        Throwable exception =
//                assertThrows(DuplicateRecordException.class, () -> productService.createProduct(createProductRequest));
//
//
//        assertThat(exception.getMessage()).isEqualTo(
//                String.format("A product with the name %s and price %s exist already", createProductRequest.getName(), createProductRequest.getPrice()));
//    }
//
//    @Test
//    void testCreateProduct_whenProductNameDoesNotExist_shouldSaveProduct() {
//
//        createProductRequest.setName("AppleWatch");
//        createProductRequest.setPrice(BigDecimal.ZERO);
//        createProductRequest.setProductQuantity(3);
//        createProductRequest.setDescription("An Apple watch");
//
//        //Mock
//        when(productRepository.findByNameAndPrice(createProductRequest.getName(), createProductRequest.getPrice())).thenReturn(Optional.empty());
//
//        //Act
//        var product = new Product();
//        product.setName(createProductRequest.getName());
//        product.setPrice(createProductRequest.getPrice());
//        product.setProductQuantity(createProductRequest.getProductQuantity());
//        product.setDescription(createProductRequest.getDescription());
//        when(productRepository.save(product)).thenReturn(product);
//
//        //Assert
//        assertThat(product.getName()).isEqualTo(createProductRequest.getName());
//        assertThat(product.getPrice()).isEqualTo(createProductRequest.getPrice());
//        assertThat(product.getProductQuantity()).isEqualTo(createProductRequest.getProductQuantity());
//        assertThat(product.getDescription()).isEqualTo(createProductRequest.getDescription());
//    }
//
//
//}
