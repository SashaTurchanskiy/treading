package com.zosh.treading.controller;

import com.zosh.treading.model.Wallet;
import com.zosh.treading.model.WalletTransaction;
import com.zosh.treading.service.UserService;
import com.zosh.treading.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;
    private final UserService userService;

    @GetMapping("/get-wallet")
    public ResponseEntity<Wallet> getUserWallet(@RequestHeader("Authorization") String jwt) throws Exception {
        var user = userService.findUserProfileByJwt(jwt);
        var wallet = walletService.getUserWallet(user);
        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }
    @PostMapping("/wallet/{walletId}/transfer")
    public ResponseEntity<Wallet> walletToWalletTransfer(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long walletId,
            @RequestBody WalletTransaction req
            ) throws Exception {
        var senderUser = userService.findUserProfileByJwt(jwt);
        var receiverWallet = walletService.findWalletById(walletId);
        var wallet = walletService.walletToWalletTransfer(senderUser, receiverWallet, req.getAmount());
        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }
    /*@PostMapping("/order/{orderId}/pay")
    public ResponseEntity<Wallet> payOrderPayment(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long orderId) throws Exception {
        var user = userService.findUserProfileByJwt(jwt);
        var order = orderService.getOrderById(orderId);
        var wallet = walletService.payOrderPayment(order, user);
        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }*/

}
