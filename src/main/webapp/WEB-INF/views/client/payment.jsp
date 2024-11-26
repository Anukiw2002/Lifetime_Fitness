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

    <jsp:include page="../common/verticalNavBar.jsp" />
    <h2 class="payment-title">Complete Your Payment</h2>
    <p class="payment-info">Choose your preferred payment method and proceed with the details.</p>

    <div class="payment-methods">
        <div class="payment-methods-icons">
            <img src="${pageContext.request.contextPath}/images/paypal.png" alt="PayPal" class="payment-logo" title="Pay via PayPal">
            <img src="${pageContext.request.contextPath}/images/visa.png" alt="Visa" class="payment-logo" title="Pay via Visa">
            <img src="${pageContext.request.contextPath}/images/mastercard.png" alt="MasterCard" class="payment-logo" title="Pay via MasterCard">
        </div>
    </div>

    <form action="processPayment" method="post" class="payment-form">
        <div class="form-group">
            <label for="cardNumber">Card Number *</label>
            <input type="text" id="cardNumber" name="cardNumber" placeholder="1234 5678 9012 3456" required>
        </div>

        <div class="form-group expiry-date-group">
            <div>
                <label for="expiryMonth">Expiry Month *</label>
                <select id="expiryMonth" name="expiryMonth" required>
                    <option value="">Month</option>
                    <option value="01">January</option>
                    <option value="02">February</option>
                    <option value="03">March</option>
                    <option value="04">April</option>
                    <option value="05">May</option>
                    <option value="06">June</option>
                    <option value="07">July</option>
                    <option value="08">August</option>
                    <option value="09">September</option>
                    <option value="10">October</option>
                    <option value="11">November</option>
                    <option value="12">December</option>
                </select>
            </div>
            <div>
                <label for="expiryYear">Expiry Year *</label>
                <select id="expiryYear" name="expiryYear" required>
                    <option value="">Year</option>
                    <option value="2024">2024</option>
                    <option value="2025">2025</option>
                    <option value="2026">2026</option>
                    <option value="2027">2027</option>
                    <option value="2028">2028</option>
                    <option value="2029">2029</option>
                </select>
            </div>
        </div>

        <div class="form-group">
            <label for="securityCode">Security Code *</label>
            <input type="text" id="securityCode" name="securityCode" placeholder="123" required>
        </div>

        <div class="button-group">
            <button type="submit" class="gym-button-1-primary">Make Payment</button>
        </div>
    </form>
</div>
</body>
</html>
