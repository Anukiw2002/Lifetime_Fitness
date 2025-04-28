package org.example.demo2.dao;

import java.util.*;

import com.paypal.api.payments.*;
import com.paypal.base.rest.*;

import org.example.demo2.model.OrderDetails;

public class PaymentServicesDAO {
    private static final String CLIENT_ID = "ATfFEXwKdgs1K8EkC_qcyHjOXQ9GwhrhzDhtHR9VwWPII47qHp2yqPGpyyXzcvcQWR8HWJbtrAwnt3yH";
    private static final String CLIENT_SECRET = "EHQSfXzScuh4pvTmcSl2fEqKAMD6xjb3-nPUGjtf4f-2xj9lyxNXSEsAAtGq1aU-oGIUlP7fTia7lB-s";
    private static final String MODE = "sandbox";

    public static String authorizePayment(OrderDetails orderDetail) throws PayPalRESTException {
        Payer payer = getPayerInformation();
        RedirectUrls redirectUrls = getRedirectURLs();
        List<Transaction> listTransaction = getTransactionInformation(orderDetail);

        Payment requestPayment = new Payment();
        requestPayment.setTransactions(listTransaction);
        requestPayment.setRedirectUrls(redirectUrls);
        requestPayment.setPayer(payer);
        requestPayment.setIntent("authorize");

        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
        Payment approvedPayment = requestPayment.create(apiContext);

        return getApprovalLink(approvedPayment);
    }

    private static Payer getPayerInformation() {
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        PayerInfo payerInfo = new PayerInfo();
        payerInfo.setFirstName("Ravindi")
                .setLastName("Fernando")
                .setEmail("ravindi03@gmail.com");

        payer.setPayerInfo(payerInfo);
        return payer;
    }

    private static RedirectUrls getRedirectURLs() {
        RedirectUrls redirectUrls = new RedirectUrls();

        redirectUrls.setCancelUrl("http://localhost:8080/CancelPayment");
        redirectUrls.setReturnUrl("http://localhost:8080/ReviewPayment");

        return redirectUrls;
    }


    private static List<Transaction> getTransactionInformation(OrderDetails orderDetail) {
        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal(String.format("%.2f", orderDetail.getTotal()));

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription(orderDetail.getProductName());

        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<>();

        Item item = new Item();
        item.setCurrency("USD");
        item.setName(orderDetail.getProductName());
        item.setPrice(String.format("%.2f", orderDetail.getSubtotal()));
        item.setQuantity("1");

        items.add(item);
        itemList.setItems(items);
        transaction.setItemList(itemList);

        List<Transaction> listTransaction = new ArrayList<>();
        listTransaction.add(transaction);

        return listTransaction;
    }

    private static String getApprovalLink(Payment approvedPayment) {
        for (Links link : approvedPayment.getLinks()) {
            if (link.getRel().equalsIgnoreCase("approval_url")) {
                return link.getHref();
            }
        }
        return null;
    }

    public Payment getPaymentDetails(String paymentId) throws PayPalRESTException {
        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
        return Payment.get(apiContext, paymentId);
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        Payment payment = new Payment();
        payment.setId(paymentId);

        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
        return payment.execute(apiContext, paymentExecution);
    }
}
