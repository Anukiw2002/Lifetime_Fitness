<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Review Payment</title>
    <style>

    </style>
</head>
<body>
<div align="center">
    <h1>Please Review Before Paying</h1>
    <form action="execute_payment" method="post">
        <input type="hidden" name="paymentId" value="${param.paymentId}" />
        <input type="hidden" name="PayerID" value="${param.PayerID}" />

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
                <td>${transaction.amount.details.subtotal} USD</td>
            </tr>
            <tr>
                <td><strong>Total:</strong></td>
                <td><strong>${transaction.amount.total} USD</strong></td>
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

            <tr><td colspan="2"><br/><strong>Shipping Address:</strong></td></tr>
            <tr>
                <td>Recipient Name:</td>
                <td>${shippingAddress.recipientName}</td>
            </tr>
            <tr>
                <td>Line 1:</td>
                <td>${shippingAddress.line1}</td>
            </tr>
            <tr>
                <td>City:</td>
                <td>${shippingAddress.city}</td>
            </tr>
            <tr>
                <td>State:</td>
                <td>${shippingAddress.state}</td>
            </tr>
            <tr>
                <td>Country Code:</td>
                <td>${shippingAddress.countryCode}</td>
            </tr>
            <tr>
                <td>Postal Code:</td>
                <td>${shippingAddress.postalCode}</td>
            </tr>

            <tr>
                <td colspan="2" align="center">
                    <br/>
                    <input type="submit" value="Pay Now" />
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
