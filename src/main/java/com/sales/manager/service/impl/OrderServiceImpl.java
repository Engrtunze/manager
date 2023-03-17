package com.sales.manager.service.impl;

import com.sales.manager.dto.OrderDetailsRequest;
import com.sales.manager.dto.OrderDto;
import com.sales.manager.dto.OrderRequest;
import com.sales.manager.exceptions.BadRequestException;
import com.sales.manager.exceptions.NotFoundException;
import com.sales.manager.kafka.KafkaProducer;
import com.sales.manager.model.Customer;
import com.sales.manager.model.Order;
import com.sales.manager.model.ProductPrice;
import com.sales.manager.repository.CustomerRepository;
import com.sales.manager.repository.OrderRepository;
import com.sales.manager.service.OrderService;
import com.sales.manager.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductService productService;
    private final KafkaProducer kafkaProducer;

    @Transactional
    @Override
    public List<OrderDto> placeOrder(OrderRequest request) {
        var customer = Customer.builder()
                .customerPhoneNumber(request.getCustomerDetailsRequest().getCustomerPhoneNumber())
                .customerName(request.getCustomerDetailsRequest().getCustomerName())
                .build();
        customerRepository.save(customer);

        List<OrderDto> orderDtos = new ArrayList<>();

        for (OrderDetailsRequest detailsRequest : request.getOrderDetailsRequests()) {
            var product = productService.findById(detailsRequest.getProductId())
                    .orElseThrow(() -> new NotFoundException("Product not in stock or does not exist"));

            var currentPrice = product.getProductPrices().stream()
                    .max(Comparator.comparing(ProductPrice::getCreatedAt))
                    .orElseThrow(() -> new NotFoundException("No current price found for product"));

            if (currentPrice.getCurrentPrice().compareTo(BigDecimal.ZERO) <= 0) {
                throw new NotFoundException("Current price not set for product");
            }
            if (product.getProductQuantity() < detailsRequest.getQuantity()) {
                throw new BadRequestException("Product out of stock");
            }

            var totalCost = currentPrice.getCurrentPrice().multiply(BigDecimal.valueOf(detailsRequest.getQuantity()));

            product.setProductQuantity(product.getProductQuantity() - detailsRequest.getQuantity());
            productService.saveProduct(product);

            var order = Order.builder()
                    .unitPriceAtOrder(currentPrice.getCurrentPrice())
                    .quantity(detailsRequest.getQuantity())
                    .product(product)
                    .customer(customer)
                    .totalUnitPriceAtOrder(totalCost)
                    .build();

            orderRepository.save(order);

            var orderDto = OrderDto.builder()
                    .customerName(order.getCustomer().getCustomerName())
                    .customerPhoneNumber(order.getCustomer().getCustomerName())
                    .unitPriceAtOrder(order.getUnitPriceAtOrder())
                    .name(order.getProduct().getName())
                    .totalUnitPriceAtOrder(order.getTotalUnitPriceAtOrder())
                    .description(order.getProduct().getDescription())
                    .quantity(order.getQuantity())
                    .build();

            orderDtos.add(orderDto);

            kafkaProducer.publishOrder(orderDto);
        }

        return orderDtos;
    }
}
