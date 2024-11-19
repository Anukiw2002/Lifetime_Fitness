<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reset Password Form</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/signUP2.css">
</head>
<body>
<div class="signup-container">
    <div class="signup-form-section">
        <img src="${pageContext.request.contextPath}/images/LogoWhite.png" alt="Lifetime Fitness" class="signup-logo">
        <h2 class="signup-heading">Reset Form</h2>
        <h4>Reset your password</h4>
        <br>
        <form action="${pageContext.request.contextPath}/resetPassword" method="post">
            <div class="signup-form-group">
                <input type="number" name="resetCode" placeholder="Enter your code" class="signup-input" required>
                <input type="password" name="newPassword" placeholder="Enter new password" class="signup-input" required>
                <input type="password" name="confirmPassword" placeholder="Confirm password" class="signup-input" required>
            </div>
            <button type="submit" class="signup-button">Submit</button>
        </form>
        <!-- Display error messages -->
        <c:if test="${not empty message}">
            <p class="error-message">${message}</p>
        </c:if>
    </div>
    <div class="signup-image-section" style="background-image: url('${pageContext.request.contextPath}/images/ClientSignUpFormImg.jpg')">
    </div>
</div>
</body>
</html>
