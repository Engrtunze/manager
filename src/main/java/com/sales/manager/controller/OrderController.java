package com.sales.manager.controller;

import com.sales.manager.dto.CreateProductRequest;
import com.sales.manager.dto.OrderRequest;
import com.sales.manager.model.Order;
import com.sales.manager.model.Product;
import com.sales.manager.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> placeOrder (@Validated @RequestBody OrderRequest order){
        return ResponseEntity.ok(orderService.placeOrder(order));
    }
}
