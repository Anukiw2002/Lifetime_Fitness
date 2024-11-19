<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reset Password</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/signUP2.css">
</head>
<body>
<div class="signup-container">
    <div class="signup-form-section">
        <img src="${pageContext.request.contextPath}/images/LogoWhite.png" alt="Lifetime Fitness" class="signup-logo">
        <h2 class="signup-heading">Forgot Password</h2>
        <h4>Reset your password</h4>
        <br>
        <!-- Update the form action to point to the servlet -->
        <form action="${pageContext.request.contextPath}/forgotPassword" method="POST">
            <div class="signup-form-group">
                <input type="email" name="email" placeholder="Enter your email" class="signup-input" required>
            </div>
            <button type="submit" class="signup-button">Send Code</button>
        </form>
    </div>
    <div class="signup-image-section" style="background-image: url('${pageContext.request.contextPath}/images/ClientSignUpFormImg.jpg')">
    </div>
</div>
</body>
</html>
