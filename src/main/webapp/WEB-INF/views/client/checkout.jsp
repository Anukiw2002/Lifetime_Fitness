<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Checkout</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/checkout.css">
</head>
<body>
<div align="center">

    <div class="logo-container">
        <img src="${pageContext.request.contextPath}/images/LogoWhite.png" alt="Lifetime Fitness" class="logo">
    </div>

    <h1>Checkout</h1>
    <br/>
    <form action="AuthorizePayment" method="post">
        <input type="hidden" name="durationId" value="${param.durationId}" />

        <table>
            <tr>
                <td>Product/Service:</td>
                <td>
                    <input type="text" name="product" value="<%= request.getParameter("product") != null ? request.getParameter("product") : "Next iPhone" %>"readonly />
                </td>
            </tr>
            <tr>
                <td>Sub Total:</td>
                <td>
                    <input type="text" name="subtotal" value="<%= request.getParameter("subtotal") != null ? request.getParameter("subtotal") : "100" %>" readonly />
                </td>
            </tr>
            <tr>
                <td>Total Amount:</td>
                <td>
                    <input type="text" name="total" value="<%= request.getParameter("subtotal") != null ? request.getParameter("subtotal") : "100"%>" readonly />
                </td>
            </tr>

            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="Checkout" />
                    <input type="button" value="Cancel" onclick="window.location.href='${pageContext.request.contextPath}/CancelPayment'" style="margin-left: 1rem;" />
                </td>
            </tr>
        </table>

    </form>
</div>
</body>
</html>
