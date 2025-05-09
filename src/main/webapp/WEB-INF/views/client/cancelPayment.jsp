<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Payment Cancelled</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/cancelPayment.css">
</head>
<body>
<div align="center">

  <div class="logo-container">
    <img src="${pageContext.request.contextPath}/images/LogoWhite.png" alt="Lifetime Fitness" class="logo">
  </div>

  <h1>Payment Cancelled ❌</h1>
  <p>We're sorry, your payment process was cancelled. If this was a mistake, you can try again.</p>

  <form action="${pageContext.request.contextPath}/Plans" method="get">
    <button type="submit" class="cancel-btn">Back to Payment Plans</button>
  </form>
</div>
</body>
</html>
