package com.zosh.treading.service;

import com.zosh.treading.model.PaymentDetails;
import com.zosh.treading.model.User;
import com.zosh.treading.repository.PaymentDetailsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentDetailsServiceImpl implements PaymentDetailsService {

    private final PaymentDetailsRepo paymentDetailsRepo;

    @Override
    public PaymentDetails addPaymentDetails(String accountNumber, String accountHolderName, String ifsc, String bankName, User user) {
        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setAccountNumber(accountNumber);
        paymentDetails.setAccountHolderName(accountHolderName);
        paymentDetails.setIfsc(ifsc);
        paymentDetails.setBankName(bankName);
        paymentDetails.setUser(user);
        return paymentDetailsRepo.save(paymentDetails);
    }

    @Override
    public PaymentDetails getPaymentDetailsByUser(User user) throws Exception {
        var paymentDetails = paymentDetailsRepo.findByUserId(user.getId());
        if (paymentDetails == null){
            throw new Exception("Payment details not found for user with id: " + user.getId());
        }
        return paymentDetails;
    }
}
