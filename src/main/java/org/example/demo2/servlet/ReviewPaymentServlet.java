/**
 * ReviewPaymentServlet class - show review payment page.
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
            throw new ServletException("Error retrieving payment details from PayPal", e);
        }

        PayerInfo payerInfo = payment.getPayer().getPayerInfo();
        Transaction transaction = payment.getTransactions().get(0);
        ShippingAddress shippingAddress = transaction.getItemList().getShippingAddress();

        // Set all necessary attributes
        request.setAttribute("payer", payerInfo);
        request.setAttribute("transaction", transaction);
        request.setAttribute("shippingAddress", shippingAddress);
        request.setAttribute("paymentId", paymentId);
        request.setAttribute("PayerID", payerId);

        // Forward to JSP (make sure the JSP is in /WEB-INF/views/client/)
        request.getRequestDispatcher("/WEB-INF/views/client/reviewPayment.jsp")
                .forward(request, response);
    }
}
