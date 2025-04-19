<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Check Out</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/checkout.css">

</head>
<body>
<div align="center">
    <h1>Check Out</h1>
    <br/>
    <form action="AuthorizePayment" method="post">
        <table>
            <tr>
                <td>Product/Service:</td>
                <td>
                    <input type="text" name="product" value="<%= request.getParameter("product") != null ? request.getParameter("product") : "Next iPhone" %>" />
                </td>
            </tr>
            <tr>
                <td>Sub Total:</td>
                <td>
                    <input type="text" name="subtotal" value="<%= request.getParameter("subtotal") != null ? request.getParameter("subtotal") : "100" %>" />
                </td>
            </tr>
            <tr>
                <td>Total Amount:</td>
                <td>
                    <input type="text" name="total" value="<%= request.getParameter("total") != null ? request.getParameter("total") : "120" %>" />
                </td>
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
