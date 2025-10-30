package com.zosh.treading.service;

import com.zosh.treading.domain.PaymentMethod;
import com.zosh.treading.model.PaymentOrder;
import com.zosh.treading.model.User;
import com.zosh.treading.response.PaymentResponse;

public interface PaymentService {
    PaymentOrder createOrder(User user, Long amount, PaymentMethod paymentMethod);
    PaymentOrder getPaymentOrderById(Long id);
    Boolean ProcessPaymentOrder(PaymentOrder paymentOrder, String paymentId);
    PaymentResponse createRazorpayPaymentLink(User user, Long amount);
    PaymentResponse createStripePaymentLink(User user, Long amount, Long orderId);
}
