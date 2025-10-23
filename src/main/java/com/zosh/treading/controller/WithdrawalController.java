package com.zosh.treading.controller;

import com.zosh.treading.model.Wallet;
import com.zosh.treading.model.Withdrawal;
import com.zosh.treading.service.UserService;
import com.zosh.treading.service.WalletService;
import com.zosh.treading.service.WithdrawalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/withdrawals")
@RequiredArgsConstructor
public class WithdrawalController {

    private final WithdrawalService withdrawalService;
    private final WalletService walletService;
    private final UserService userService;

    @PostMapping("/api.withdraw/{amount}")
    public ResponseEntity<?> withdrawalRequest(
            @PathVariable Long amount,
            @RequestHeader("Authorization") String jwt) throws Exception {

        var user = userService.findUserProfileByJwt(jwt);
        var userWallet = walletService.getUserWallet(user);

        Withdrawal withdrawal = withdrawalService.requestWithdrawal(amount, user);
        walletService.addBalance(userWallet, -withdrawal.getAmount());
        return ResponseEntity.ok(withdrawal);
    }
    @PatchMapping("/api/admin/withdrawal/{id}/proceed/{accept}")
    public ResponseEntity<?> proceedWithdrawal(
            @PathVariable Long id,
            @PathVariable boolean accept,
            @RequestHeader("Authorization") String jwt) throws Exception {

        var user = userService.findUserProfileByJwt(jwt);

        Withdrawal withdrawal = withdrawalService.processWithdrawal(id, accept);
        Wallet userWallet = walletService.getUserWallet(user);
        if (!accept){
            walletService.addBalance(userWallet, withdrawal.getAmount());
        }
        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }
    @GetMapping("/api/withdrawals")
    public ResponseEntity<List<Withdrawal>> getWithdrawalHistory(
            @RequestHeader("Authorization") String jwt) throws Exception {
        var user = userService.findUserProfileByJwt(jwt);
        List<Withdrawal> withdrawals = withdrawalService.getUserWithdrawalHistory(user);
        return new ResponseEntity<>(withdrawals, HttpStatus.OK);
    }
    @GetMapping("/api/admin/withdrawals")
    public ResponseEntity<List<Withdrawal>>getAllWithdrawalRequest(
            @RequestHeader("Authorization") String jwt) throws Exception {
        var user = userService.findUserProfileByJwt(jwt);
        List<Withdrawal> withdrawals = withdrawalService.getAllWithdrawalRequests();
        return new ResponseEntity<>(withdrawals, HttpStatus.OK);
    }
}
