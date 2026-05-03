package com.asif.ApiGateWay.cionfig;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
        return builder.routes()

                .route("USER-SERVICE", r -> r
                        .path("/api/users/**")
                        .uri("lb://USER-SERVICE")
                )

                .route("ORDER-SERVICE", r -> r
                        .path("/api/orders/**", "/api/cart/**")
                        .uri("lb://ORDER-SERVICE")
                )

                .route("PRODUCT-SERVICE", r -> r
                        .path("/api/product/**")
                        .filters(f -> f.circuitBreaker(config -> config
                                .setName("ecomBreaker")
                                .setFallbackUri("forward:/fallback/products")
                        ))
                        .uri("lb://PRODUCT-SERVICE")
                )

                .build();
    }
}