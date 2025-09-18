package com.zosh.treading.service;

import com.zosh.treading.domain.VerificationType;
import com.zosh.treading.model.User;
import com.zosh.treading.model.VerificationCode;
import com.zosh.treading.repository.VerificationCodeRepo;
import com.zosh.treading.utils.OtpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationCodeServiceImpl implements VerificationCodeService{

    private final VerificationCodeRepo verificationCodeRepo;

    @Override
    public VerificationCode sendVerificationCode(User user, VerificationType verificationType) {
        VerificationCode verificationCode1 = new VerificationCode();
        verificationCode1.setOtp(OtpUtils.generateOTP());
        verificationCode1.setVerificationType(verificationType);
        verificationCode1.setUser(user);
        return verificationCodeRepo.save(verificationCode1);
    }

    @Override
    public VerificationCode getVerificationCodeById(Long id) throws Exception {
      return verificationCodeRepo.findById(id).orElseThrow(
                ()-> new Exception("Verification code not found")
        );
    }

    @Override
    public VerificationCode getVerificationCodeByUser(Long userId) throws Exception {
        return verificationCodeRepo.findByUserId(userId).orElseThrow(
                ()-> new Exception("Verification code not found")
        );
    }

    @Override
    public void deleteVerificationCode(Long id) throws Exception {
       VerificationCode verificationCode = verificationCodeRepo.findById(id).orElseThrow(
               ()-> new Exception("Verification code not found")
       );
       verificationCodeRepo.delete(verificationCode);
    }
}
