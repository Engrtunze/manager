package com.sales.manager.kafka;

import com.sales.manager.config.KafkaConfig;
import com.sales.manager.dto.OrderDto;
import com.sales.manager.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaConfig kafkaConfig;

    @Value("${kafka.topic}")
    private String topic;

    public void publishOrder(OrderDto order) {
        kafkaConfig.kafkaTemplate().send(topic,order);
    }



}
