package com.zosh.treading.repository;

import com.zosh.treading.model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationCodeRepo extends JpaRepository<VerificationCode, Long> {
   Optional<VerificationCode> findByUserId(Long userId);
}
