package com.zosh.treading.model;

import com.zosh.treading.domain.WalletTransactionType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class WalletTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Wallet wallet;

    private WalletTransactionType type;
    private LocalDateTime date;
    private String transferId;
    private String purpose;
    private Long amount;
}
