package com.ecommerce.notification.config;

import com.ecommerce.notification.payLoad.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderEventConsumer {

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void handleOrderEvent(OrderEvent orderEvent) {
        log.info("Received order event {}", orderEvent);
    }


}
