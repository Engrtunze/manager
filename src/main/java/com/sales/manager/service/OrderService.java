package com.sales.manager.service;

import com.sales.manager.dto.OrderRequest;
import com.sales.manager.model.Order;

public interface OrderService {
    Order placeOrder(OrderRequest orderRequest);
}
