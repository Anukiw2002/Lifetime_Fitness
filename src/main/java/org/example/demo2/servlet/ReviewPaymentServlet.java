/**
 * ReviewPaymentServlet class - show review payment page.
 * @author Nam Ha Minh
 * @source https://codeJava.net
 */
package org.example.demo2.servlet;

import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.ShippingAddress;
import com.paypal.api.payments.Transaction;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import com.paypal.base.rest.PayPalRESTException;
import org.example.demo2.dao.PaymentServicesDAO;

@WebServlet("/ReviewPayment")
public class ReviewPaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ReviewPaymentServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String paymentId = request.getParameter("paymentId");
        String payerId = request.getParameter("PayerID");

        PaymentServicesDAO paymentServices = new PaymentServicesDAO();
        Payment payment = null;
        try {
            payment = paymentServices.getPaymentDetails(paymentId);
        } catch (PayPalRESTException e) {
            throw new RuntimeException(e);
        }

        PayerInfo payerInfo = payment.getPayer().getPayerInfo();
        Transaction transaction = payment.getTransactions().get(0);
        ShippingAddress shippingAddress = transaction.getItemList().getShippingAddress();

        request.setAttribute("payer", payerInfo);
        request.setAttribute("transaction", transaction);
        request.setAttribute("shippingAddress", shippingAddress);

        // Forward to review JSP (make sure this file exists in WebContent)
        String url = "reviewPayment.jsp?paymentId=" + paymentId + "&PayerID=" + payerId;
        request.getRequestDispatcher(url).forward(request, response);

    }
}
