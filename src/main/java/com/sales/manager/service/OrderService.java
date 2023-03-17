package com.sales.manager.service;

import com.sales.manager.dto.OrderDto;
import com.sales.manager.dto.OrderRequest;

import java.util.List;

public interface OrderService {
    List<OrderDto> placeOrder(OrderRequest request);
}
