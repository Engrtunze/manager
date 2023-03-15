package com.sales.manager.service.impl;

import com.sales.manager.dto.OrderRequest;
import com.sales.manager.exceptions.BadRequestException;
import com.sales.manager.exceptions.NotFoundException;
import com.sales.manager.model.Order;
import com.sales.manager.repository.OrderRepository;
import com.sales.manager.service.OrderService;
import com.sales.manager.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;

    @Override
    public Order placeOrder(OrderRequest orderRequest) {
        var product = productService.findById(orderRequest.getProductId())
                .orElseThrow(()-> new NotFoundException("Product not in stock or does not exist"));
        if(product.getProductQuantity() < orderRequest.getQuantity()){
            throw new BadRequestException("Product out of stock");
        }
        product.setProductQuantity(product.getProductQuantity() - orderRequest.getQuantity());
        productService.saveProduct(product);

        var order = Order.builder()
                .customerName(orderRequest.getCustomerName())
                .customerPhoneNumber(orderRequest.getCustomerPhoneNumber())
                .product(product)
                .quantity(orderRequest.getQuantity())
                .build();
      return orderRepository.save(order);
    }
}
