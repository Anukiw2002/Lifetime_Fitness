<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Lifetime Fitness</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/signUP2.css">
</head>
<body>
<div class="signup-container">
    <div class="signup-form-section">
        <img src="${pageContext.request.contextPath}/images/LogoWhite.png" alt="Lifetime Fitness" class="signup-logo">
        <h2 class="signup-heading">Log in</h2>
        <form>
            <div class="signup-form-group">
                <input type="email" placeholder="Enter your email" class="signup-input" required>
                <input type="password" placeholder="Enter your password" class="signup-input" required>
                <p class="signup-login">Forgot your password? <a href="/forgot-password">Reset it here</a></p>
            </div>
            <button type="submit" class="signup-button">Log in</button>
            </form>
        <div class="signup-footer">
            <p class="signup-login">Don't have an account? <a href="/testView?page=page1">Sign Up</a></p>
        </div>
    </div>
    <div class="signup-image-section" style="background-image: url('${pageContext.request.contextPath}/images/ClientSignUpFormImg.jpg')">
    </div>
</div>

</body>
</html>