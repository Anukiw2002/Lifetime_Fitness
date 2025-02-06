<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Payment Page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/payment.css">
</head>
<body>
<div class="main-content">
    <jsp:include page="../client/clientVerticalNavbar.jsp" />

    <div class="container">
        <div class="card">
            <h2 class="text-center mb-2">Complete Your Payment</h2>
            <p class="text-center text-muted mb-4">Choose your preferred payment method and proceed with the details.</p>

            <div class="flex justify-center mb-4">
                <div class="flex items-center">
                    <img src="${pageContext.request.contextPath}/images/paypal.png" alt="PayPal" class="payment-logo" title="Pay via PayPal">
                    <img src="${pageContext.request.contextPath}/images/visa.png" alt="Visa" class="payment-logo" title="Pay via Visa">
                    <img src="${pageContext.request.contextPath}/images/mastercard.png" alt="MasterCard" class="payment-logo" title="Pay via MasterCard">
                </div>
            </div>

            <form action="${pageContext.request.contextPath}/processPayment" method="post">
                <div class="form-group">
                    <label class="form-label" for="cardNumber">Card Number *</label>
                    <input type="text" id="cardNumber" name="cardNumber" class="form-control" placeholder="1234 5678 9012 3456" required>
                </div>

                <div class="form-group expiry-group">
                    <div>
                        <label class="form-label" for="expiryMonth">Expiry Month *</label>
                        <select id="expiryMonth" name="expiryMonth" class="form-control" required>
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
                        <label class="form-label" for="expiryYear">Expiry Year *</label>
                        <select id="expiryYear" name="expiryYear" class="form-control" required>
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
                    <label class="form-label" for="securityCode">Security Code *</label>
                    <input type="text" id="securityCode" name="securityCode" class="form-control" placeholder="123" required>
                </div>

                <div class="flex justify-center mt-4">
                    <a href="/processPayment" class="btn btn-primary">Make Payment</a>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>