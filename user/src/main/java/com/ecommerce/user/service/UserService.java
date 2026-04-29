package com.ecommerce.user.service;

import com.ecommerce.user.exception.UserNotFoundException;
import com.ecommerce.user.model.User;
import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.reporsitory.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    public List<UserResponse> fetchAllUsers() {
        return userRepo.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public UserResponse addUser(User user) {
        return mapToUserResponse(userRepo.save(user));
    }

    public UserResponse getUserbyId(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("user not found with userId " + userId));
        return mapToUserResponse(user);
    }

    public User updateUser(Long userId, UserRequest updateUser) {
        User existingUser = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with userId " + userId));

        if (updateUser.getFirstName() != null) existingUser.setFirstName(updateUser.getFirstName());
        if (updateUser.getLastName() != null) existingUser.setLastName(updateUser.getLastName());
        if (updateUser.getPhone() != null) existingUser.setPhone(updateUser.getPhone());

        if (updateUser.getAddress() != null) {
            if (updateUser.getAddress().getCity() != null)
                existingUser.getAddress().setCity(updateUser.getAddress().getCity());
            if (updateUser.getAddress().getCountry() != null)
                existingUser.getAddress().setCountry(updateUser.getAddress().getCountry());
            if (updateUser.getAddress().getStreet() != null)
                existingUser.getAddress().setStreet(updateUser.getAddress().getStreet());

            if (updateUser.getAddress().getState() != null)
                existingUser.getAddress().setState(updateUser.getAddress().getState());
            if (updateUser.getAddress().getZipCode() != null)
                existingUser.getAddress().setZipCode(updateUser.getAddress().getZipCode());
        }
        return userRepo.save(existingUser);
    }

    private UserResponse mapToUserResponse(User user) {
        UserResponse.UserResponseBuilder builder = UserResponse.builder()
                .email(user.getEmail())
                .phone(user.getPhone())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userId(String.valueOf(user.getUserId()))
                .role(user.getRole());

//        if (user.getAddress() != null) {
//            builder.address(
//                    AddressDto.builder()
//                            .street(user.getAddress().getStreet())
//                            .city(user.getAddress().getCity())
//                            .state(user.getAddress().getState())
//                            .country(user.getAddress().getCountry())
//                            .zipCode(user.getAddress().getZipCode())
//                            .build()
//            );
//        }

        return builder.build();
    }
}
