package com.ecommerce.order.exception;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Configuration
public class FeignErrorDecoder implements ErrorDecoder {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {

        try {
            String body = Util.toString(
                    response.body().asReader(StandardCharsets.UTF_8)
            );

            Map<String, Object> error =
                    mapper.readValue(body, new TypeReference<>() {});

            String message = (String) error.get("message");

            return switch (response.status()) {

                case 404 -> {
                    if (methodKey.contains("ProductClient")) {
                        yield new ProductNotFoundException(message);
                    } else if (methodKey.contains("UserClient")) {
                        yield new UserNotFoundException(message);
                    }
                    yield new RuntimeException(message);
                }

                default -> new RuntimeException("Service error: " + message);
            };

        } catch (Exception e) {
            return new RuntimeException("Unknown Feign error");
        }
    }
}