<!-- paymentError.jsp -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Payment Error</title>
</head>
<body>
<h1>Payment Failed</h1>
<p><%= request.getAttribute("error") %></p>
<a href="payment.jsp">Go back and try again</a>
</body>
</html>
