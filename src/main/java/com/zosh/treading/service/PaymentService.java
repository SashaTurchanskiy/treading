package com.zosh.treading.service;

import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import com.zosh.treading.domain.PaymentMethod;
import com.zosh.treading.model.PaymentOrder;
import com.zosh.treading.model.User;
import com.zosh.treading.response.PaymentResponse;

public interface PaymentService {
    PaymentOrder createOrder(User user, Long amount, PaymentMethod paymentMethod);
    PaymentOrder getPaymentOrderById(Long id) throws Exception;
    Boolean ProcessPaymentOrder(PaymentOrder paymentOrder, String paymentId) throws RazorpayException;
    PaymentResponse createRazorpayPaymentLink(User user, Long amount) throws RazorpayException;
    PaymentResponse createStripePaymentLink(User user, Long amount, Long orderId) throws StripeException;
}
