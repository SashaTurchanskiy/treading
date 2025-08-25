package com.zosh.treading.controller;

import com.zosh.treading.domain.Role;
import com.zosh.treading.model.User;
import com.zosh.treading.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepo userRepo;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user){
        User savedUser = User.builder()
                .email(user.getEmail())
                .fullName(user.getFullName())
                .password(user.getPassword())
                .role(Role.ROLE_CUSTOMER)
                .build();

        return ResponseEntity.ok(userRepo.save(savedUser));
    }


}
