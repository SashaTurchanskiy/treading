package com.zosh.treading.controller;

import com.zosh.treading.config.JwtProvider;
import com.zosh.treading.domain.Role;
import com.zosh.treading.model.TwoFactorOTP;
import com.zosh.treading.model.User;
import com.zosh.treading.repository.UserRepo;
import com.zosh.treading.response.AuthResponse;
import com.zosh.treading.service.CustomUserDetailsService;
import com.zosh.treading.service.EmailService;
import com.zosh.treading.service.TwoFactorOTPService;
import com.zosh.treading.utils.OtpUtils;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepo userRepo;
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final TwoFactorOTPService twoFactorOTPService;
    private final EmailService emailService;


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
    public ResponseEntity<AuthResponse> login(@RequestBody User user) throws MessagingException {
        String userName = user.getEmail();
        String password = user.getPassword();

        Authentication auth = authenticate(userName, password);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = JwtProvider.generateToken(auth);
        Optional<User> authUser = userRepo.findByEmail(userName);

        if (user.getTwoFactorAuth().isEnabled()){
            AuthResponse res = new AuthResponse();
            res.setMessage("Two-factor auth is enabled");
            res.setTwoFactorAuthEnabled(true);
            String otp = OtpUtils.generateOTP();

            TwoFactorOTP oldTwoFactorOtp = twoFactorOTPService.findByUser(authUser.get().getId());
            if (oldTwoFactorOtp != null) {
                twoFactorOTPService.deleteTwoFactorOTP(oldTwoFactorOtp, oldTwoFactorOtp.getOtp());
            }
            TwoFactorOTP newTwoFactorOTP = twoFactorOTPService.createTwoFactorOTP(authUser.get(), otp, jwt);
            emailService.sendVerificationOtpEmail(otp, userName);
            res.setSession(new TwoFactorOTP().getId());
            return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
        }

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
    @PostMapping("/verify-otp/{otp}")
    public ResponseEntity<AuthResponse> verifySigningOtp(@PathVariable String otp, @RequestParam String id){
        TwoFactorOTP twoFactorOTP = twoFactorOTPService.findById(id);
        if (twoFactorOTPService.verifyTwoFactorOTP(twoFactorOTP, otp)){
            AuthResponse res = new AuthResponse();
            res.setMessage("OTP verified successfully");
            res.setTwoFactorAuthEnabled(true);
            res.setJwt(twoFactorOTP.getJwt());
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        throw new BadCredentialsException("Invalid OTP");
    }
}
