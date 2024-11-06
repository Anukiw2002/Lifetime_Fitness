<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Forgot Password</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<h2>Forgot Password</h2>
<form action="forgotPassword" method="post">
    <label for="email">Enter your email:</label>
    <input type="email" id="email" name="email" required><br><br>
    <button type="submit">Send Reset Link</button>
</form>

<!-- Display error or success message -->
<%
    String message = (String) request.getAttribute("message");
    if (message != null) {
%>
<div><%= message %></div>
<%
    }
%>
</body>
</html>
