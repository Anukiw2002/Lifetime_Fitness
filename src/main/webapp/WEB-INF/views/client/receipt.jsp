<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Payment Receipt</title>
    <style type="text/css">
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            color: #333;
            margin: 0;
            padding: 0;
        }
        .container {
            width: 60%;
            margin: 80px auto;
            padding: 30px;
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            text-align: center;
        }
        table {
            margin: 20px auto;
            border-collapse: collapse;
        }
        table td {
            padding: 10px 20px;
            text-align: left;
        }
        h1 {
            color: #27ae60;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Payment Successful 🎉</h1>
    <p>Thank you for purchasing our products!</p>

    <h2>Receipt Details</h2>
    <table>
        <tr>
            <td><strong>Merchant:</strong></td>
            <td>Company ABC Ltd.</td>
        </tr>
        <tr>
            <td><strong>Payer:</strong></td>
            <td>${payer.firstName} ${payer.lastName}</td>
        </tr>
        <tr>
            <td><strong>Email:</strong></td>
            <td>${payer.email}</td>
        </tr>
        <tr>
            <td><strong>Description:</strong></td>
            <td>${transaction.description}</td>
        </tr>
        <tr>
            <td><strong>Subtotal:</strong></td>
            <td>${transaction.amount.details.subtotal} USD</td>
        </tr>
        </tr>
        <tr>
            <td><strong>Total:</strong></td>
            <td><strong>${transaction.amount.total} USD</strong></td>
        </tr>
    </table>
</div>
</body>
</html>
