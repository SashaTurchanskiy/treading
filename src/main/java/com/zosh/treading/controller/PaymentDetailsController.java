package com.zosh.treading.controller;

import com.zosh.treading.model.PaymentDetails;
import com.zosh.treading.service.PaymentDetailsService;
import com.zosh.treading.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment-details")
@RequiredArgsConstructor
public class PaymentDetailsController {

    private final PaymentDetailsService paymentDetailsService;
    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity<PaymentDetails> addPaymentDetails(@RequestBody PaymentDetails paymentDetails,
                                                            @RequestHeader("Authorization")String jwt) throws Exception {
        var user = userService.findUserProfileByJwt(jwt);
        var savedPaymentDetails = paymentDetailsService.addPaymentDetails(
                paymentDetails.getAccountNumber(),
                paymentDetails.getAccountHolderName(),
                paymentDetails.getIfsc(),
                paymentDetails.getBankName(),
                user
        );
        return new ResponseEntity<>(paymentDetails,HttpStatus.CREATED);
    }
    @GetMapping("/details")
    public ResponseEntity<PaymentDetails> getPaymentDetails(@RequestHeader("Authorization")String jwt) throws Exception {
        var user = userService.findUserProfileByJwt(jwt);
        var paymentDetails = paymentDetailsService.getPaymentDetailsByUser(user);
        return new ResponseEntity<>(paymentDetails, HttpStatus.OK);
    }
}
