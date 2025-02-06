<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reset Password Form</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/signUP2.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body>
<div class="signup-container">
    <div class="signup-form-section">
        <img src="${pageContext.request.contextPath}/images/LogoWhite.png" alt="Lifetime Fitness" class="signup-logo">
        <h2 class="signup-heading">Reset your password</h2>
        <br>
        <form action="${pageContext.request.contextPath}/resetPassword" method="post">
            <div class="signup-form-group">
                <input type="number" name="resetCode" placeholder="Enter your code" class="signup-input" required>

                <div class="input-wrapper password-wrapper">
                    <input type="password" name="newPassword" id="newPassword" placeholder="Enter new password" class="signup-input" required>
                    <button type="button" class="password-toggle" onclick="togglePassword('newPassword', 'toggleIcon1')">
                        <i class="fa-solid fa-eye" id="toggleIcon1"></i>
                    </button>
                </div>

                <div class="input-wrapper password-wrapper">
                    <input type="password" name="confirmPassword" id="confirmPassword" placeholder="Confirm password" class="signup-input" required>
                    <button type="button" class="password-toggle" onclick="togglePassword('confirmPassword', 'toggleIcon2')">
                        <i class="fa-solid fa-eye" id="toggleIcon2"></i>
                    </button>
                </div>
            </div>
            <div class="signup-button-container">
                <button type="submit" class="signup-button">Submit</button>
            </div>
        </form>
        <!-- Display error messages -->
        <c:if test="${not empty message}">
            <p class="error-message">${message}</p>
        </c:if>
    </div>
    <div class="signup-image-section" style="background-image: url('${pageContext.request.contextPath}/images/ClientSignUpFormImg.jpg')">
    </div>
</div>
<script>
    function togglePassword(inputId, iconId) {
        const passwordInput = document.getElementById(inputId);
        const toggleIcon = document.getElementById(iconId);

        if (passwordInput.type === 'password') {
            passwordInput.type = 'text';
            toggleIcon.className = 'fa-solid fa-eye-slash';
        } else {
            passwordInput.type = 'password';
            toggleIcon.className = 'fa-solid fa-eye';
        }
    }
</script>
</body>
</html>
