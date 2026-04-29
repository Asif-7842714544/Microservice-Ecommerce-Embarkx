package com.ecommerce.user.controller;

import com.ecommerce.user.model.User;
import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping()
    public ResponseEntity<List<UserResponse>> getAllUsers()
    {
        
       return  ResponseEntity.ok(userService.fetchAllUsers());
    }

    @PostMapping()
    public ResponseEntity<UserResponse> createUser(@RequestBody User user)
    {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.addUser(user));
    }

    @GetMapping("/{userId}")
    public UserResponse getUserById(@PathVariable Long userId)
    {
        return userService.getUserbyId(userId);
   }

   @PutMapping("/{userId}")
   public ResponseEntity<User> updateUser(@PathVariable Long userId,@RequestBody UserRequest user){
        return ResponseEntity.ok(userService.updateUser(userId,user));
   }


}
