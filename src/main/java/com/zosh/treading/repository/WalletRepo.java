package com.zosh.treading.repository;

import com.zosh.treading.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepo extends JpaRepository<Wallet, Long> {
    Wallet findByUserId(Long userId);
}
