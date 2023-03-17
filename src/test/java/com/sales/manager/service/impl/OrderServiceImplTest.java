package com.sales.manager.service.impl;

import com.sales.manager.dto.CustomerDetailsRequest;
import com.sales.manager.dto.OrderDetailsRequest;
import com.sales.manager.dto.OrderDto;
import com.sales.manager.dto.OrderRequest;
import com.sales.manager.kafka.KafkaProducer;
import com.sales.manager.model.Customer;
import com.sales.manager.model.Order;
import com.sales.manager.model.Product;
import com.sales.manager.model.ProductPrice;
import com.sales.manager.repository.CustomerRepository;
import com.sales.manager.repository.OrderRepository;
import com.sales.manager.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ProductService productService;
    @Mock
    private KafkaProducer kafkaProducer;
    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testPlaceOrder() {
        var request = new OrderRequest();
        var customerDetailsRequest = new CustomerDetailsRequest();
        customerDetailsRequest.setCustomerName("John Doe");
        customerDetailsRequest.setCustomerPhoneNumber("+1-123-456-7890");
        request.setCustomerDetailsRequest(customerDetailsRequest);
        List<OrderDetailsRequest> orderDetailsRequests = new ArrayList<>();
        OrderDetailsRequest orderDetailsRequest = new OrderDetailsRequest();
        orderDetailsRequest.setProductId(UUID.randomUUID());
        orderDetailsRequest.setQuantity(2);
        orderDetailsRequests.add(orderDetailsRequest);
        request.setOrderDetailsRequests(orderDetailsRequests);

        Product product = new Product();
        product.setId(orderDetailsRequest.getProductId());
        product.setProductQuantity(10);
        ProductPrice productPrice = new ProductPrice();
        productPrice.setCurrentPrice(BigDecimal.TEN);
        productPrice.setCreatedAt(Instant.now());
        product.setProductPrices(new ArrayList<>()); // initialize product prices
        product.getProductPrices().add(productPrice);
        when(productService.findById(orderDetailsRequest.getProductId())).thenReturn(Optional.of(product));

        var customer = new Customer();
        customer.setId(UUID.randomUUID());
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        var order = new Order();
        order.setId(UUID.randomUUID());
        order.setUnitPriceAtOrder(productPrice.getCurrentPrice());
        order.setQuantity(orderDetailsRequest.getQuantity());
        order.setProduct(product);
        order.setCustomer(customer);
        order.setTotalUnitPriceAtOrder(productPrice.getCurrentPrice().multiply(BigDecimal.valueOf(orderDetailsRequest.getQuantity())));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        var orderDto = new OrderDto();
        orderDto.setCustomerName(customer.getCustomerName());
        orderDto.setCustomerPhoneNumber(customer.getCustomerPhoneNumber());
        orderDto.setName(product.getName());
        orderDto.setDescription(product.getDescription());
        orderDto.setQuantity(order.getQuantity());
        orderDto.setUnitPriceAtOrder(productPrice.getCurrentPrice());
        orderDto.setTotalUnitPriceAtOrder(order.getTotalUnitPriceAtOrder());
        doNothing().when(kafkaProducer).publishOrder(any(OrderDto.class));

        List<OrderDto> orderDtos = orderService.placeOrder(request);
        assertNotNull(orderDtos);
        assertEquals(1, orderDtos.size());
    }
}

