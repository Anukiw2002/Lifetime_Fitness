<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Payment Receipt</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/receipt.css">
</head>
<body>
<div class="container">
    <div class="logo-container">
        <img src="${pageContext.request.contextPath}/images/LogoWhite.png" alt="Lifetime Fitness" class="logo">
    </div>
    <h1>Payment Successful 💪</h1>
    <p>Thank you for purchasing the workout plan!</p>

    <h2>Receipt Details</h2>

    <table>
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
        <tr>
            <td colspan="2" align="center" style="padding-top: 1rem;">
                <input type="button" value="Print Receipt" onclick="window.print()" />
            </td>
        </tr>

        <tr>
            <td colspan="2" align="center">
                <input type="button" value="Go to the Dashboard" onclick="window.location.href='${pageContext.request.contextPath}/clientDashboard'" style="margin-left: 1rem;" />
            </td>
        </tr>
    </table>
</div>
</body>
</html>
