<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign Up - Lifetime Fitness</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/signUP2.css">
</head>
<body>
<div class="signup-container">
    <div class="signup-form-section">
        <img src="${pageContext.request.contextPath}/images/LogoWhite.png" alt="Lifetime Fitness" class="signup-logo">
        <h2 class="signup-heading">Sign Up</h2>
        <form action="${pageContext.request.contextPath}/signUp2" method="post">
            <div class="signup-form-group">
                <input type="text" name="firstName" placeholder="Enter your first name" class="signup-input" required>
                <input type="text" name="lastName" placeholder="Enter your last name" class="signup-input" required>
                <input type="email" name="email" placeholder="Enter your email" class="signup-input" required>
                <input type="password" name="password" placeholder="Enter your password (At least 8 characters long)" class="signup-input" required>
                <input type="password" name="confirmPassword" placeholder="Confirm your password" class="signup-input" required>
                <div class="signup-terms">
                    <input type="checkbox" id="terms" name="terms" required>
                    <label for="terms"><span class="normal-text">I agree to the  </span><a href="/termsAndConditions">terms and conditions</a></label>
                </div>
            </div>
            <button type="submit" class="signup-button">Sign Up</button>
        </form>
        <div class="signup-footer">
            <p class="signup-login">Already have an account? <a href="/testView?page=login">Log in</a></p>
        </div>
    </div>
    <div class="signup-image-section" style="background-image: url('${pageContext.request.contextPath}/images/ClientSignUpFormImg.jpg')">
    </div>
</div>
</body>
</html>
