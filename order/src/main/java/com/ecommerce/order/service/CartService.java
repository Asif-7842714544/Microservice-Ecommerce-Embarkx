package com.ecommerce.order.service;

import com.ecommerce.order.client.ProductClient;
import com.ecommerce.order.client.UserClient;
import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.dto.ProductResponse;
import com.ecommerce.order.dto.UserResponse;
import com.ecommerce.order.model.CartItem;
import com.ecommerce.order.reporsitory.CartRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final CartRepo cartRepo;

    private final ProductClient productClient;
    private final UserClient userClient;


    public boolean addToCart(Long userId, CartItemRequest request) {
        log.info("inside addtoCart method with UserId  {} and productId {}", userId, request.getProductId());

        // Retrieve the product
        ProductResponse product = productClient.getProductById(request.getProductId());

        log.info("product {}", product.getProductId());

//        // Check if sufficient stock is available
        if (product.getStockQuantity() < request.getQuantity()) {
            throw new RuntimeException("Insufficient stock for the product");
        }


//        // Retrieve the user
        UserResponse userResponse = userClient.getUserById(userId);

        // Check if the item already exists in the cart
        CartItem existingCartItem = cartRepo.findByUserIdAndProductId(Long.valueOf(userResponse.getUserId()), product.getProductId());

        if (existingCartItem != null) {
            // Update the quantity and total price for the existing cart item
            log.info("Cart item exists. Updating quantity and price.");
            int newQuantity = existingCartItem.getQuantity() + request.getQuantity();
            existingCartItem.setQuantity(newQuantity);
            existingCartItem.setPrice(product.getPrice()
                    .multiply(BigDecimal.valueOf(newQuantity)));
            cartRepo.save(existingCartItem);
        } else {
            // Create a new cart item
            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(request.getProductId());
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(product.getPrice()
                    .multiply(BigDecimal.valueOf(request.getQuantity())));
            cartRepo.save(cartItem);
        }
        log.info("added to cart successfully");
        return true;
    }

    public boolean deleteItemFromCart(Long userId, Long productId) {

        CartItem cartItem = cartRepo.findByUserIdAndProductId(userId, productId);
        if (cartItem != null) {
            cartRepo.delete(cartItem);
            return true;
        }
        return false;
    }

    public List<CartItem> getCartItems(Long userId) {

        return cartRepo.findByUserId(userId);
    }

    public void clearCart(String userId) {
        cartRepo.deleteAllByUserId(userId);

    }
}
