package com.sales.manager.controller;

import com.sales.manager.dto.CreateProductRequest;
import com.sales.manager.model.Product;
import com.sales.manager.service.ProductService;
import lombok.RequiredArgsConstructor;
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

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> createProduct (@Validated @RequestBody CreateProductRequest productDto){
        return ResponseEntity.ok(productService.createProduct(productDto));
    }
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> updateProduct (@PathVariable UUID id, @RequestBody @Validated CreateProductRequest productDto){
        return ResponseEntity.ok(productService.updateProduct(id, productDto));
    }
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> getAllAvailableProduct (){
        return ResponseEntity.ok(productService.getAllAvailableProduct());
    }
}
