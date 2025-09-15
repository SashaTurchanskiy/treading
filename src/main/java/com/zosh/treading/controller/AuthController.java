package com.zosh.treading.controller;

import com.zosh.treading.config.JwtProvider;
import com.zosh.treading.domain.Role;
import com.zosh.treading.model.User;
import com.zosh.treading.repository.UserRepo;
import com.zosh.treading.response.AuthResponse;
import com.zosh.treading.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepo userRepo;
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody User user) throws Exception {
        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
            throw new Exception("Email already exists");
        }

        User savedUser = User.builder()
                .email(user.getEmail())
                .fullName(user.getFullName())
                .password(passwordEncoder.encode(user.getPassword())) // кодуємо пароль
                .role(Role.ROLE_CUSTOMER)
                .build();

        userRepo.save(savedUser); // зберігаємо в БД

        Authentication auth = new UsernamePasswordAuthenticationToken(
                savedUser.getEmail(), user.getPassword()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = JwtProvider.generateToken(auth);

        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("User registered successfully");

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody User user){
        String userName = user.getEmail();
        String password = user.getPassword();

        Authentication auth = authenticate(userName, password);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = JwtProvider.generateToken(auth);
        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("User logged in successfully");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    private Authentication authenticate(String userName, String password) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username or password");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
    }
}
