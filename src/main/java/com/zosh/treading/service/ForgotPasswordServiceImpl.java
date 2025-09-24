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
        ForgotPasswordToken token = new ForgotPasswordToken();
        token.setId(id);
        token.setUser(user);
        token.setOtp(otp);
        token.setVerificationType(verificationType);
        token.setSendTo(sendTo);
        return forgotPasswordRepo.save(token);
    }

    @Override
    public ForgotPasswordToken findById(String id) {
        if (id == null){
            throw new IllegalArgumentException("Token id cannot be null");
        }
        return forgotPasswordRepo.findById(id).orElseThrow(()-> new IllegalArgumentException("Token not found"));
    }

    @Override
    public ForgotPasswordToken findByUserId(Long userId) {
        if (userId == null){
            throw new IllegalArgumentException("User id cannot be null");
        }
        return forgotPasswordRepo.findByUserId(userId).orElseThrow(()-> new IllegalArgumentException("Token not found"));
    }

    @Override
    public void deleteToken(ForgotPasswordToken token) {
        if (token == null || token.getId() == null){
            throw new IllegalArgumentException("Token or Token id cannot be null");
        }
        if (!forgotPasswordRepo.existsById(token.getId())){
            throw new IllegalArgumentException("Token does not exist");
        }
        var existing = forgotPasswordRepo.findById(token.getId()).get();
        if (token.getUser() != null && existing.getUser() !=null
        && !existing.getUser().getId().equals(token.getUser().getId())){
            throw new SecurityException("Not allowed to delete this token");
        }
        forgotPasswordRepo.delete(token);
        }
}

