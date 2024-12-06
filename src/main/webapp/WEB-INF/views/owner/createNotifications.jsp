<%--
  Created by IntelliJ IDEA.
  User: Ishan Maduranga
  Date: 12/6/2024
  Time: 3:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Message UI</title>
    link rel="stylesheet" href="${pageContext.request.contextPath}/css/ownerCreateNotification.css">
</head>
<body>
<div class="container">
    <h1>Send a Message</h1>
    <form action="createNotification" method="POST">
        <textarea id="message" name="message" placeholder="Type your message here..."></textarea>
        <div class="buttons">
            <button class="btn-primary" type="submit" value="customers">Send to Customers</button>
            <button class="btn-primary" type="submit" value="instructors">Send to Instructors</button>
            <button class="btn-primary" type="submit" value="both">Send to Both</button>
        </div>
    </form>
</div>

</body>
</html>
