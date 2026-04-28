package com.ecommerce.order.client;

import lombok.Builder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ProductServiceClientConfig {


    @Bean
    @LoadBalanced
    public RestClient restClient() {
        return RestClient.builder().build();
    }

//    @Bean
//    public ProductServiceClient restClientInterface(RestClient.Builder restClientBuilder) {
//        RestClient restClient = restClientBuilder.baseUrl("http://PRODUCT-SERVICE").build();
//        RestClientAdapter adapter = RestClientAdapter.create(restClient);
//        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
//        ProductServiceClient productServiceClient = factory.createClient(ProductServiceClient.class);
//        return productServiceClient;
//    }

}
