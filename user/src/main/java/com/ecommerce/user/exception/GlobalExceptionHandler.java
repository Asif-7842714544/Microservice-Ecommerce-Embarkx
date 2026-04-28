package com.ecommerce.user.exception;

import com.ecommerce.user.dto.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(
            UserNotFoundException ex,
            HttpServletRequest request) {
        log.error("UserNotFoundException occurred: {}", ex.getMessage());
        ApiError error = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                request.getRequestURI(),
                System.currentTimeMillis()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiError> handleGeneric(Exception ex,
//                                                  HttpServletRequest request) {
//
//        ApiError error = new ApiError(
//                HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                "Something went wrong",
//                request.getRequestURI(),
//                System.currentTimeMillis()
//        );
//
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//    }
}