package com.ecommerce.order.exception;


import com.ecommerce.order.dto.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiError> handleProduct(ProductNotFoundException ex,
                                                  HttpServletRequest request) {

        log.error("Product error: {}", ex.getMessage());

        return buildError(ex, request);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUser(UserNotFoundException ex,
                                               HttpServletRequest request) {

        log.error("User error: {}", ex.getMessage());

        return buildError(ex, request);
    }

    private ResponseEntity<ApiError> buildError(Exception ex, HttpServletRequest request) {

        ApiError error = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                request.getRequestURI(),
                System.currentTimeMillis()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}