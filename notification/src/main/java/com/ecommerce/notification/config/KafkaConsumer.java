package com.ecommerce.notification.config;

import com.ecommerce.notification.payLoad.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = "${spring.kafka.topic.order-events}")
    public void listenRiderLocation(OrderEvent order) {
        log.info("📦 Order Event Received: {}", order);
    }
}