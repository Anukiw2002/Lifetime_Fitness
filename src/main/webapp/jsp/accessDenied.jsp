<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>404 - Page Not Found</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            flex-direction: column;
        }

        .container {
            text-align: center;
        }

        .error-message {
            font-size: 48px;
            color: #ff4d4d;
            font-weight: bold;
            margin: 20px 0;
            animation: bounceIn 1s ease-in-out infinite;
        }

        .error-submessage {
            font-size: 24px;
            color: #666;
            margin-bottom: 30px;
        }

        .home-btn {
            padding: 15px 30px;
            background-color: #007BFF;
            color: white;
            text-decoration: none;
            font-size: 18px;
            border-radius: 5px;
            transition: background-color 0.3s ease;
        }

        .home-btn:hover {
            background-color: #0056b3;
        }

        /* Bounce animation for error message */
        @keyframes bounceIn {
            0% {
                transform: translateY(-300px);
                opacity: 0;
            }
            60% {
                transform: translateY(30px);
                opacity: 1;
            }
            100% {
                transform: translateY(0);
            }
        }
    </style>
</head>
<body>

<div class="container">
    <div class="error-message">404 - Page Not Found</div>
    <div class="error-submessage">Oops! The page you are looking for doesn't exist.</div>

    <%
        String referer = request.getHeader("Referer");
        if (referer != null && !referer.isEmpty()) {
    %>
    <a href="<%= referer %>" class="home-btn">Go Back to Previous Page</a>
    <%
    } else {
    %>
    <a href="${pageContext.request.contextPath}/landingPage" class="home-btn">Go to Homepage</a>
    <%
        }
    %>

</div>

</body>
</html>
