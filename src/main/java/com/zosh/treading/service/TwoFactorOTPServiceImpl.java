package com.zosh.treading.service;

import com.zosh.treading.model.TwoFactorOTP;
import com.zosh.treading.model.User;
import com.zosh.treading.repository.TwoFactorOTPRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TwoFactorOTPServiceImpl implements TwoFactorOTPService{

    private final TwoFactorOTPRepo twoFactorOTPRepo;

    @Override
    public TwoFactorOTP createTwoFactorOTP(User user, String otp, String jwt) {
        UUID uuid = UUID.randomUUID();

        String id = uuid.toString();

        TwoFactorOTP twoFactorOTP = new TwoFactorOTP();
        twoFactorOTP.setOtp(otp);
        twoFactorOTP.setJwt(jwt);
        twoFactorOTP.setId(id);
        twoFactorOTP.setUser(user);

        return twoFactorOTPRepo.save(twoFactorOTP);
    }

    @Override
    public TwoFactorOTP findByUser(Long userId) {
        return twoFactorOTPRepo.findByUserId(userId);
    }

    @Override
    public TwoFactorOTP findById(String id) {
        return twoFactorOTPRepo.findById(id).orElseThrow(()-> new RuntimeException("TwoFactorOTP not found"));
    }

    @Override
    public void deleteTwoFactorOTP(TwoFactorOTP twoFactorOTP, String otp) {
    twoFactorOTPRepo.delete(twoFactorOTP);
    }

    @Override
    public boolean verifyTwoFactorOTP(TwoFactorOTP twoFactorOTP, String otp) {
        return twoFactorOTP.getOtp().equals(otp);
    }
}
