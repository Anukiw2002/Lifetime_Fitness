<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Review Payment</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reviewPayment.css">
</head>
<body>
<div align="center">
    <div class="logo-container">
        <img src="${pageContext.request.contextPath}/images/LogoWhite.png" alt="Lifetime Fitness" class="logo">
    </div>
    <h1>Please Review Before Paying</h1>

    <!-- Pay Now Form -->
    <form action="ExecutePayment" method="post">
        <input type="hidden" name="paymentId" value="${param.paymentId}" />
        <input type="hidden" name="PayerID" value="${param.PayerID}" />
        <input type="hidden" name="durationId" value="${durationId}" />

        <table>
            <tr>
                <td colspan="2"><strong>Transaction Details:</strong></td>
            </tr>
            <tr>
                <td>Description:</td>
                <td>${transaction.description}</td>
            </tr>
            <tr>
                <td>Subtotal:</td>
                <td>${transaction.amount.details.subtotal} LKR</td>
            </tr>
            <tr>
                <td><strong>Total:</strong></td>
                <td><strong>${transaction.amount.total} LKR</strong></td>
            </tr>

            <tr><td colspan="2"><br/><strong>Payer Information:</strong></td></tr>
            <tr>
                <td>First Name:</td>
                <td>${payer.firstName}</td>
            </tr>
            <tr>
                <td>Last Name:</td>
                <td>${payer.lastName}</td>
            </tr>
            <tr>
                <td>Email:</td>
                <td>${payer.email}</td>
            </tr>

            <tr>
                <td colspan="2" align="center">
                    <br/>
                    <input type="submit" value="Pay Now" />
                </td>
            </tr>
        </table>
    </form>

    <!-- Cancel Payment Form -->
    <form action="CancelPayment" method="post">
        <input type="hidden" name="paymentId" value="${param.paymentId}" />
        <input type="hidden" name="PayerID" value="${param.PayerID}" />
        <div style="margin-top: 10px;">
            <input type="submit" value="Cancel Payment" />
        </div>
    </form>
</div>
</body>
</html>
