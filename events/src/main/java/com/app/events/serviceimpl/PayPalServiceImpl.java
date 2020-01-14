package com.app.events.serviceimpl;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.app.events.service.PayPalService;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PayPalServiceImpl implements PayPalService {

    public static String CURRENCY = "USD";
    public static String QUANTITY = "1";

    @Value("${paypal.mode}")
    private String clientMode;

    @Value("${paypal.client.app}")
    private String clientId;

    @Value("${paypal.client.secret}")
    private String clientSecret;

    @Value("${paypal.client.route.success}")
    private String frontEndPayPalSuccessRoute;

    @Value("${paypal.client.route.fail}")
    private String frontEndPayPalFailRoute;

    @Override
    public Payment createPaymentObject(double price, String currency) {
        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(String.valueOf(price));

        // generate item name
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        System.out.println(generatedString);
        //

        Item item = new Item(generatedString, QUANTITY, String.valueOf(price), currency);
        item.setDescription("KTSNVT - Reservation");
        
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);

        transaction.setItemList(new ItemList());
        transaction.getItemList().setItems(new ArrayList<Item>());

        System.out.println(transaction.getItemList());
        System.out.println(transaction.getItemList().getItems());
        System.out.println(item);

        transaction.getItemList().getItems().add(item);
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(frontEndPayPalFailRoute);
        redirectUrls.setReturnUrl(frontEndPayPalSuccessRoute);
        payment.setRedirectUrls(redirectUrls);

        return payment;
    }

    public Map<String, Object> startPayment(double price)
    {
        Payment payment = createPaymentObject(price, CURRENCY);
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
                // return url with token for paypal payment..
                
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