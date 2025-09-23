package com.zosh.treading.repository;

import com.zosh.treading.model.ForgotPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForgotPasswordRepo extends JpaRepository<ForgotPasswordToken, Long> {
    ForgotPasswordToken findByUserId(Long userId);
}
