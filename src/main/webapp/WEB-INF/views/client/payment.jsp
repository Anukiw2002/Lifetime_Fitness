<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Payment Page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/payment.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/library/button.css">
</head>
<body>
<div class="payment-container">
    <h2 class="payment-title">Make your payment here</h2>
    <p class="payment-info">Please select your payment method</p>

    <div class="payment-methods">
        <div class="payment-methods-icons">
            <img src="${pageContext.request.contextPath}/images/paypal.png" alt="PayPal" class="payment-logo">
            <img src="${pageContext.request.contextPath}/images/visa.png" alt="Visa" class="payment-logo">
            <img src="${pageContext.request.contextPath}/images/mastercard.png" alt="MasterCard" class="payment-logo">
        </div>

    </div>

    <form action="processPayment" method="post" class="payment-form">
        <div class="form-group">
            <label for="cardNumber">Card Number *</label>
            <input type="text" id="cardNumber" name="cardNumber" required>
        </div>

        <div class="form-group">
            <%--@declare id="expirydate"--%><%--@declare id="expirydate"--%><label for="expiryDate">Expiry Date *</label>
            <select id="expiryMonth" name="expiryMonth" required>
                <option value="">Month</option>
                <option value="01">01</option>
                <option value="02">02</option>
                <!-- Add remaining months -->
            </select>
            <select id="expiryYear" name="expiryYear" required>
                <option value="">Year</option>
                <option value="2024">2024</option>
                <option value="2025">2025</option>
                <!-- Add more years -->
            </select>
        </div>

        <div class="form-group">
            <label for="securityCode">Security Code *</label>
            <input type="text" id="securityCode" name="securityCode" required>
        </div>

        <div class="button-group">
            <button type="submit" class="gym-button-1-primary">Checkout</button>
        </div>
    </form>
</div>
</body>
</html>
