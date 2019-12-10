package com.app.events.serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.app.events.service.PayPalService;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PayPalServiceImpl implements PayPalService {

    @Value("${paypal.mode}")
    private String clientMode;

    @Value("${paypal.client.app}")
    private String clientId;

    @Value("${paypal.client.secret}")
    private String clientSecret;

    @Override
    public Payment createPaymentObject(long TicketId, double price) {
        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal(String.valueOf(price));

        Item item = new Item();
        item.setDescription("KTSNVT - Reservation");
        item.setName(String.valueOf(TicketId));
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.getItemList().getItems().add(item);
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        return payment;
    }

    public Map<String, Object> startPayment(long TicketId, double price)
    {
        Payment payment = createPaymentObject(TicketId, price);
        Map<String, Object> response = new HashMap<String, Object>();
        Payment createdPayment;
        try {
            String redirectUrl = "";
            APIContext context = new APIContext(clientId, clientSecret, clientMode);
            createdPayment = payment.create(context);
            if(createdPayment!=null){
                List<Links> links = createdPayment.getLinks();
                for (Links link:links) {
                    if(link.getRel().equals("approval_url")){
                        redirectUrl = link.getHref();
                        break;
                    }
                }
                response.put("status", "success");
                response.put("redirect_url", redirectUrl);
            }
        } catch (PayPalRESTException e) {
            System.out.println("Error happened during payment creation!");
        }
        return response;
    }

    @Override
    public boolean completedPayment(String paymentId, String payerId) throws PayPalRESTException{
        
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);
        
        APIContext context = new APIContext(clientId, clientSecret, clientMode);
        Payment createdPayment = payment.execute(context, paymentExecution);
        if(createdPayment!=null){
            return true;
        }
        return false;
    }

    
}