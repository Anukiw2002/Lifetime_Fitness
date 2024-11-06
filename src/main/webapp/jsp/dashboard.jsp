<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Dashboard</title>
</head>
<body>
<h2>Welcome, <%= ((org.example.demo2.model.User) session.getAttribute("user")).getEmail() %></h2>
<p>You have successfully logged in!</p>
<form action="LogoutServlet" method="post">
  <input type="submit" value="Logout">
</form>
</body>
</html>
