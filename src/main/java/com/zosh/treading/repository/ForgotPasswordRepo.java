package com.zosh.treading.repository;

import com.zosh.treading.model.ForgotPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ForgotPasswordRepo extends JpaRepository<ForgotPasswordToken, String> {
    Optional<ForgotPasswordToken> findByUserId(Long userId);
    Optional<ForgotPasswordToken> findById(String id);
}
