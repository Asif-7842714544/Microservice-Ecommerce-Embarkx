package com.ecommerce.order.controller;

import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.model.CartItem;
import com.ecommerce.order.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;


    @PostMapping
    public ResponseEntity<String> addToCart(
            @RequestHeader("X-user-id") Long userId,
            @RequestBody CartItemRequest request
    ) {
        if (!cartService.addToCart(userId, request)) {
            return ResponseEntity.badRequest().body("Failed to add item to cart. Please check the product Service and UserService OR Product quantity.");
        }
        return ResponseEntity.accepted().body("Item added to cart successfully.");
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<String> removeFromCart(
            @RequestHeader("X-user-id") Long userId, @PathVariable Long productId) {
        boolean deleted = cartService.deleteItemFromCart(userId, productId);
        return deleted ? ResponseEntity.ok("Item removed from cart successfully.") :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found in cart or failed to remove.");
    }

    @GetMapping("/items")
    public ResponseEntity<List<CartItem>> getCartItems(@RequestHeader("X-user-id") Long userId) {
        return ResponseEntity.ok(cartService.getCartItems(userId));
    }

}

