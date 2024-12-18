<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Lifetime Fitness</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/signUP2.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body>

<%
    if (request.getSession(false) != null && request.getSession(false).getAttribute("user") != null) {
        response.sendRedirect("memberProfile");
        return;
    }
%>
<div class="signup-container">
    <div class="signup-form-section">
        <img src="${pageContext.request.contextPath}/images/LogoWhite.png" alt="Lifetime Fitness" class="signup-logo">
        <h2 class="signup-heading">Log in</h2>

        <%
            String errorMessage = (String) request.getAttribute("errorMessgae");
        %>
        <% if (errorMessage != null) { %>
        <p class="error-message"><%= errorMessage %></p>
        <% } %>

        <!-- Updated form -->
        <form action="${pageContext.request.contextPath}/login" method="post">
            <div class="signup-form-group">
                <div class="input-wrapper">
                    <input type="email" name="email" placeholder="Enter your email" class="signup-input" required>
                </div>
                <div class="input-wrapper password-wrapper">
                    <input type="password" name="password" id="password" placeholder="Enter your password" class="signup-input" required>
                    <button type="button" class="password-toggle" onclick="togglePassword()">
                        <i class="fa-solid fa-eye" id="toggleIcon"></i>
                    </button>
                </div>
                <p class="signup-login">Forgot your password? <a href="/testView?page=page4">Reset it here</a></p>
            </div>
            <div class="signup-button-container">
                <button type="submit" class="signup-button">Log in</button>
            </div>
        </form>

        <div class="signup-footer">
            <p class="signup-footer-text">Don't have an account? <a href="/testView?page=page1">Sign Up</a></p>
        </div>
    </div>
    <div class="signup-image-section" style="background-image: url('${pageContext.request.contextPath}/images/ClientSignUpFormImg.jpg')">
    </div>
</div>
<script>
        function togglePassword() {
        const passwordInput = document.getElementById('password');
        const toggleIcon = document.getElementById('toggleIcon');

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