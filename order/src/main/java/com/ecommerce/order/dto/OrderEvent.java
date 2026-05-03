package com.ecommerce.order.dto;

import com.ecommerce.order.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderEvent {

    private Long orderId;
    private Long userId;
    private OrderStatus orderStatus;
    private BigDecimal totalAmount;
    private List<OrderItemDto> items;
    private LocalDateTime createdAt;
}
