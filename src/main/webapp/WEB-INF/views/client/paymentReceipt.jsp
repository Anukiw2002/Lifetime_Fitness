<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Payment Receipt</title>
    <link rel="stylesheet" href="/demo2_war_exploded/css/payment.css">
    <link rel="stylesheet" href="/demo2_war_exploded/css/button.css">
</head>
<body>
<div class="receipt-container">
    <h1 class="receipt-title">Payment Receipt</h1>
    <p class="receipt-info">Thank you for your purchase! Here are your payment details:</p>

    <div class="receipt-details">
        <p><strong>Transaction ID:</strong> 123456789</p>
        <p><strong>Date:</strong> November 10, 2024</p>
        <p><strong>Amount:</strong> $100.00</p>
        <p><strong>Payment Method:</strong> Visa</p>
    </div>

    <div class="button-group">
        <a href="home.jsp" class="gym-button-1 gym-button-1-primary">Return to Home</a>
        <button onclick="downloadReceipt()" class="gym-button-2 gym-button-2-primary">Download Receipt</button>
    </div>
</div>

<script>
    function downloadReceipt() {
        // This function would be implemented with server-side code to enable downloading.
        alert("Receipt downloaded!");
    }
</script>
</body>
</html>
