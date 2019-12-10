package com.app.events.service;

import java.util.Map;
import com.paypal.api.payments.Payment;

/**
 * PayPalService
 */
public interface PayPalService {

    Payment createPaymentObject(long TicketId, double price);

    public Map<String, Object> startPayment(long TicketId, double price);
    
    public boolean completedPayment(String paymentId, String payerId) throws Exception;

}