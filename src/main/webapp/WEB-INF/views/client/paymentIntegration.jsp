<!DOCTYPE html>
<html>
<head>
    <title>PayHere Sandbox Payment</title>
    <script src="https://www.payhere.lk/lib/payhere.js"></script>
</head>
<body style="background: #1e1e2f; color: white; text-align: center; padding: 40px; font-family: sans-serif;">
<h2>PayHere Sandbox Demo</h2>
<button id="payButton" style="padding: 12px 20px; background-color: #007bff; color: white; border: none; border-radius: 6px; font-size: 18px; cursor: pointer;">
    Pay Now
</button>

<script>
    payhere.merchantId = "1230076";
    payhere.sandbox = true;

    payhere.onCompleted = function(orderId) {
        alert("Payment successful! Order ID: " + orderId);
    };

    payhere.onDismissed = function() {
        alert("Payment dismissed by user.");
    };

    payhere.onError = function(error) {
        alert("Error: " + error);
    };

    document.getElementById("payButton").onclick = function () {
        var payment = {
            sandbox: true,
            merchant_id: "YOUR_MERCHANT_ID_HERE",
            return_url: "http://localhost:8080/paymentSuccess",
            cancel_url: "http://localhost:8080/paymentCancelled",
            notify_url: "http://localhost:8080/paymentNotify",
            order_id: "ORDER123456",
            items: "Demo Product",
            amount: "1000.00",
            currency: "LKR",
            first_name: "John",
            last_name: "Doe",
            email: "john@example.com",
            phone: "0771234567",
            address: "123, Main Street",
            city: "Colombo",
            country: "Sri Lanka"
        };

        payhere.startPayment(payment);
    };
</script>
</body>
</html>
