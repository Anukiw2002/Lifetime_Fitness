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

        if (paymentId == null || payerId == null) {
            response.sendRedirect("error.jsp");
            return;
        }

        String durationIdParam = request.getParameter("durationId");

        if (durationIdParam != null) {
            int durationId = Integer.parseInt(durationIdParam);
            request.setAttribute("durationId", durationId);
        }


        try {
            PaymentServicesDAO paymentServices = new PaymentServicesDAO();
            Payment payment = paymentServices.getPaymentDetails(paymentId);

            if (payment != null) {
                PayerInfo payerInfo = payment.getPayer().getPayerInfo();
                Transaction transaction = payment.getTransactions().get(0);
                ShippingAddress shippingAddress = transaction.getItemList().getShippingAddress();

                request.setAttribute("payer", payerInfo);
                request.setAttribute("transaction", transaction);
                request.setAttribute("paymentId", paymentId);
                request.setAttribute("PayerID", payerId);

                request.getRequestDispatcher("/WEB-INF/views/client/reviewPayment.jsp")
                        .forward(request, response);
            } else {
                response.sendRedirect("error.jsp");
            }

        } catch (PayPalRESTException e) {
            throw new ServletException("Could not retrieve payment details from PayPal.", e);
        }
    }
}
