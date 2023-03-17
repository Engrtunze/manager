package com.sales.manager.controller;

import com.sales.manager.dto.CreateProductPriceDto;
import com.sales.manager.dto.CreateProductRequest;
import com.sales.manager.dto.ProductDto;
import com.sales.manager.dto.ProductPriceDto;
import com.sales.manager.dto.UpdateProductPriceRequest;
import com.sales.manager.dto.UpdateProductRequest;
import com.sales.manager.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateProductPriceDto> createProduct (@Validated @RequestBody CreateProductRequest request){
        return ResponseEntity.ok(productService.createProduct(request));
    }
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductPriceDto> updateProduct (@PathVariable UUID id, @RequestBody @Validated UpdateProductRequest request){
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductDto>> getAllAvailableProduct (){
        return ResponseEntity.ok(productService.getAllAvailableProduct());
    }
    @PostMapping(value = "/update-product-price/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateProductPriceDto> createProduct (@PathVariable UUID id, @Validated @RequestBody UpdateProductPriceRequest request){
        return ResponseEntity.ok(productService.updateProductPrice(id,request ));
    }
}
