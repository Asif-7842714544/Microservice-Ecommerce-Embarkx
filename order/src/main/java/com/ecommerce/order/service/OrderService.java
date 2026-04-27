package com.ecommerce.order.service;


import com.ecommerce.order.dto.OrderItemDto;
import com.ecommerce.order.dto.OrderResponse;
import com.ecommerce.order.model.CartItem;
import com.ecommerce.order.model.Order;
import com.ecommerce.order.model.OrderItem;
import com.ecommerce.order.model.OrderStatus;
import com.ecommerce.order.reporsitory.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartService cartService;
    private final OrderRepo orderRepo;

    public OrderResponse createOrder(Long userId) {
        //validate for cart Item
        List<CartItem> cartItems = cartService.getCartItems(userId);

        //validatev for user
//        User user = userRepo.findById(Long.valueOf(userId))
//                .orElseThrow(() -> new RuntimeException("User not found with userId " + userId));
        //calculate total price
        BigDecimal totalPrice = cartItems.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal::add)
                .orElseThrow(() -> new RuntimeException("Cart is empty. Cannot create order."));
        //create order
        Order order = new Order();
        order.setUserId(userId);
        order.setTotalAmount(totalPrice);
        order.setOrderStatus(OrderStatus.CONFIRMED);
        List<OrderItem> orderItems = cartItems.stream().map(item -> new OrderItem(
                null,
                item.getProductId(),
                item.getQuantity(),
                item.getPrice(),
                order
        )).toList();
        order.setItems(orderItems);
        Order savedOrder = orderRepo.save(order);
        //clear cart
        cartService.clearCart(String.valueOf(userId));

        return Optional.of(maptoOrderResponse(savedOrder))
                .orElseThrow(() -> new RuntimeException("Failed to create order."));
    }

    private OrderResponse maptoOrderResponse(Order savedOrder) {
        return new OrderResponse(
                savedOrder.getOrderId(),
                savedOrder.getTotalAmount(),
                savedOrder.getOrderStatus(),
                savedOrder.getItems().stream().map(item -> new OrderItemDto(
                        item.getId(),
                        item.getProductId(),
                        item.getQuantity(),
                        item.getPrice(),
                        item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())
                        ))).toList(),
                savedOrder.getCreatedAt()
        );
    }
}
