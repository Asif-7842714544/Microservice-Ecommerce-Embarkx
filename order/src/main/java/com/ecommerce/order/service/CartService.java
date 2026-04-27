package com.ecommerce.order.service;

import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.model.CartItem;
import com.ecommerce.order.reporsitory.CartRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepo cartRepo;

    public boolean addToCart(Long userId, CartItemRequest request) {
        // Retrieve the product
//        Product product = productRepo.findById(request.getProductId())
//                .orElseThrow(() -> new RuntimeException("Product not found"));
//
//        // Check if sufficient stock is available
//        if (product.getStockQuantity() < request.getQuantity()) {
//            throw new RuntimeException("Insufficient stock for the product");
//        }
//
//        // Retrieve the user
//        User user = userRepo.findById(Long.valueOf(userId))
//                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if the item already exists in the cart
        CartItem existingCartItem = cartRepo.findByUserIdAndProductId(userId, request.getProductId());

        if (existingCartItem != null) {
            // Update the quantity and total price for the existing cart item
            int newQuantity = existingCartItem.getQuantity() + request.getQuantity();
            existingCartItem.setQuantity(newQuantity);
            existingCartItem.setPrice(BigDecimal.valueOf(100.00));
            cartRepo.save(existingCartItem);
        } else {
            // Create a new cart item
            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(request.getProductId());
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(BigDecimal.valueOf(100.00));
            cartRepo.save(cartItem);
        }
        return true;
    }

    public boolean deleteItemfromCart(Long userId, Long productId) {

//        User user = userRepo.findById(Long.valueOf(userId))
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        Product product = productRepo.findById(productId)
//                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = cartRepo.findByUserIdAndProductId(userId, productId);
        if (cartItem != null) {
            cartRepo.delete(cartItem);
            return true;
        }
        return false;
    }

    public List<CartItem> getCartItems(Long userId) {
//        User user = userRepo.findById(Long.valueOf(userId))
//                .orElseThrow(() -> new RuntimeException("User not found"));
        return cartRepo.findByUserId(userId);
    }

    public void clearCart(String userId) {
            cartRepo.deleteAllByUserId(userId);

    }
}
