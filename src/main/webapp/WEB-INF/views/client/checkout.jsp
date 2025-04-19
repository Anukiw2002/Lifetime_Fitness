<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Check Out</title>
    <style type="text/css">
        table { border: 0; }
        table td { padding: 10px; }
    </style>
</head>
<body>
<div align="center">
    <h1>Check Out</h1>
    <br/>
    <form action="AuthorizePayment" method="post">
        <table>
            <tr>
                <td>Product/Service:</td>
                <td><input type="text" name="product" value="<%= request.getAttribute("product") != null ? request.getAttribute("product") : "Next iPhone" %>" /></td>
            </tr>
            <tr>
                <td>Sub Total:</td>
                <td><input type="text" name="subtotal" value="<%= request.getAttribute("subtotal") != null ? request.getAttribute("subtotal") : "100" %>" /></td>
            </tr>
            <tr>
                <td>Shipping:</td>
                <td><input type="text" name="shipping" value="<%= request.getAttribute("shipping") != null ? request.getAttribute("shipping") : "10" %>" /></td>
            </tr>
            <tr>
                <td>Tax:</td>
                <td><input type="text" name="tax" value="<%= request.getAttribute("tax") != null ? request.getAttribute("tax") : "10" %>" /></td>
            </tr>
            <tr>
                <td>Total Amount:</td>
                <td><input type="text" name="total" value="<%= request.getAttribute("total") != null ? request.getAttribute("total") : "120" %>" /></td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="Checkout" />
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
