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
        payerInfo.setFirstName("William")
                .setLastName("Peterson")
                .setEmail("william.peterson@company.com");

        payer.setPayerInfo(payerInfo);
        return payer;
    }

    private static RedirectUrls getRedirectURLs() {
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://localhost:8080/PaypalTest/cancel.html");
        redirectUrls.setReturnUrl("http://localhost:8080/WEB-INF/views/client/reviewPayment.jsp");

        return redirectUrls;
    }

    private static List<Transaction> getTransactionInformation(OrderDetails orderDetail) {
        Details details = new Details();
        details.setShipping(String.format("%.2f", orderDetail.getShipping()));
        details.setSubtotal(String.format("%.2f", orderDetail.getSubtotal()));
        details.setTax(String.format("%.2f", orderDetail.getTax()));

        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal(String.format("%.2f", orderDetail.getTotal()));
        amount.setDetails(details);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription(orderDetail.getProductName());

        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<>();

        Item item = new Item();
        item.setCurrency("USD");
        item.setName(orderDetail.getProductName());
        item.setPrice(String.format("%.2f", orderDetail.getSubtotal()));
        item.setTax(String.format("%.2f", orderDetail.getTax()));
        item.setQuantity("1");

        items.add(item);
        itemList.setItems(items);
        transaction.setItemList(itemList);

        List<Transaction> listTransaction = new ArrayList<>();
        listTransaction.add(transaction);

        return listTransaction;
    }

    private static String getApprovalLink(Payment approvedPayment) {
        List<Links> links = approvedPayment.getLinks();
        String approvalLink = null;

        for (Links link : links) {
            if (link.getRel().equalsIgnoreCase("approval_url")) {
                approvalLink = link.getHref();
                break;
            }
        }

        return approvalLink;
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
