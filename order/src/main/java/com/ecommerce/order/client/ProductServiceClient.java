package com.ecommerce.order.client;

import com.ecommerce.order.dto.ProductResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/api")
public interface ProductServiceClient {

    @GetExchange("/product/{productId}")
    ProductResponse getProductById(@PathVariable Long productId);

}
