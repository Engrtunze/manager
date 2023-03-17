package com.sales.manager.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private CustomerDetailsRequest customerDetailsRequest;
    private List<OrderDetailsRequest> orderDetailsRequests;
}
