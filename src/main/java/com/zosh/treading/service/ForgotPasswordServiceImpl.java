package com.zosh.treading.service;

import com.zosh.treading.domain.VerificationType;
import com.zosh.treading.model.ForgotPasswordToken;
import com.zosh.treading.model.User;
import com.zosh.treading.repository.ForgotPasswordRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

    private final ForgotPasswordRepo forgotPasswordRepo;

    @Override
    public ForgotPasswordToken createToken(User user, String id, String otp, VerificationType verificationType, String sendTo) {
        return null;
    }

    @Override
    public ForgotPasswordToken findById(String id) {
        return null;
    }

    @Override
    public ForgotPasswordToken findByUserId(Long userId) {
        return null;
    }

    @Override
    public void deleteToken(ForgotPasswordToken token) {

    }
}
