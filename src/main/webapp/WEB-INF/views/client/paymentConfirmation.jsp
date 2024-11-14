<!-- paymentConfirmation.jsp -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Payment Confirmation</title>
</head>
<body>
<h1>Payment Successful!</h1>
<p>Your payment has been processed successfully.</p>
<p>Card Number: <%= request.getAttribute("cardNumber") %></p>
<p>Expiry Date: <%= request.getAttribute("expiryDate") %></p>
<a href="home.jsp">Go back to home</a>
</body>
</html>
