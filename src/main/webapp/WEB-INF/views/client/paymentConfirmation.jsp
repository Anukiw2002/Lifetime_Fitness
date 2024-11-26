<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Payment Confirmation</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/paymentConfirmation.css">
</head>
<body>
<div class="main-content">
    <div class="container">
        <div class="card text-center">
            <div class="card-body">
                <div class="success-icon">
                    <div class="checkmark"></div>
                </div>
                <h1 class="mb-4">Payment Successful!</h1>
                <p class="text-muted mb-4">Your payment has been processed successfully.</p>

                <div class="payment-detail">
                    <div class="payment-label">Card Number</div>
                    <div class="payment-value"><%= request.getAttribute("cardNumber") %></div>
                </div>

                <div class="payment-detail">
                    <div class="payment-label">Expiry Date</div>
                    <div class="payment-value"><%= request.getAttribute("expiryDate") %></div>
                </div>

                <div class="mt-4">
                    <a href="home.jsp" class="btn btn-primary">Back to Home</a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>