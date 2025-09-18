package com.zosh.treading.service;

import com.zosh.treading.config.JwtProvider;
import com.zosh.treading.domain.VerificationType;
import com.zosh.treading.model.TwoFactorAuth;
import com.zosh.treading.model.User;
import com.zosh.treading.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Override
    public User findUserProfileByJwt(String jwt) throws Exception {
        if (jwt == null || jwt.isBlank()) {
            throw new IllegalArgumentException("JWT token is missing");
        }
        if (jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7);
        }
        String email;
        try {
            email = JwtProvider.getEmailFromToken(jwt);
        } catch (Exception e) {
            throw new Exception("Invalid JWT token");
        }
        return userRepo.findByEmail(email).orElseThrow(() -> new Exception("User not found"));

    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is missing");
        }
        return userRepo.findByEmail(email).orElseThrow(() -> new Exception("User not found"));
    }

    @Override
    public User findUserById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("User ID is missing");
        }
        return userRepo.findById(id).orElseThrow(()-> new IllegalArgumentException("User not found"));
    }

    @Override
    public User enableTwoFactorAuthentication(VerificationType verificationType, String sendTo, User user) {

        TwoFactorAuth twoFactorAuth = new TwoFactorAuth();
        twoFactorAuth.setEnabled(true);
        twoFactorAuth.setSendTo(verificationType);
        user.setTwoFactorAuth(twoFactorAuth);

        return userRepo.save(user);
    }

    @Override
    public User updatePassword(User user, String newPassword) {
        if (user == null) {
            throw new IllegalArgumentException("User is missing");
        }
        if (newPassword == null || newPassword.isBlank()) {
            throw new IllegalArgumentException("New password is missing");
        }
        user.setPassword(newPassword);
        return userRepo.save(user);
    }
}
