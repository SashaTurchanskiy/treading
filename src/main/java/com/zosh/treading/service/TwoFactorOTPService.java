package com.zosh.treading.service;

import com.zosh.treading.model.TwoFactorOTP;
import com.zosh.treading.model.User;

public interface TwoFactorOTPService {

    TwoFactorOTP createTwoFactorOTP(User user, String otp, String jwt);
    TwoFactorOTP findByUser(Long userId);
    TwoFactorOTP findById(String id);
    void deleteTwoFactorOTP(TwoFactorOTP twoFactorOTP, String otp);
    boolean verifyTwoFactorOTP(TwoFactorOTP twoFactorOTP, String otp);
}
