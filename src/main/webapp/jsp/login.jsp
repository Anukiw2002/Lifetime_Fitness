<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<div class="login-container">
    <h2>Login</h2>
    <form action="/login" method="POST">
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" id="username" name="username" required>
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required>
        </div>
        <button type="submit">Login</button>
    </form>
    <a href="forgotPassword.jsp" class="forgot-password">Forgot Password?</a>
</div>
<%--<c:if test="${not empty alertMessage}">--%>
<%--    <script>--%>
<%--        alert("${alertMessage}");--%>
<%--    </script>--%>
<%--</c:if>--%>
</body>
</html>