package com.sales.manager.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sales.manager.dto.OrderDto;
import org.apache.kafka.common.serialization.Serializer;

public class CustomSerializer implements Serializer<OrderDto> {

    @Override
    public byte[] serialize(String topic, OrderDto data) {
        ObjectMapper mapper = new ObjectMapper();
        byte[] serializedBytes = null;
        try {
            serializedBytes = mapper.writeValueAsBytes(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serializedBytes;
    }
}
