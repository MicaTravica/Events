package com.app.events.service;

import java.util.Map;
import com.paypal.api.payments.Payment;

/**
 * PayPalService
 */
public interface PayPalService {

    Payment createPaymentObject(double price, String currency);

    public Map<String, Object> startPayment(double price);
    
    public boolean completedPayment(String paymentId, String payerId) throws Exception;

}