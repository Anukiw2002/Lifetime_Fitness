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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ownerCreateNotification.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
    <script>
        function setRecipientType(type){
            document.getElementById('recipientType').value = type;
        }
    </script>

</head>
<body>
<div class="container">
    <h1>Send a Message</h1>
    <form action="createNotifications.jsp" method="POST">
        <textarea id="message" name="message" placeholder="Type your message here..."></textarea>
        <br>
        <br>
        <input type="hidden" id="recipientType" name="recipientType" value="">
        <div class="buttons">
            <button class="btn btn-primary" type="submit" onclick="setRecipientType('client'); this.form.sumbit();">Send to Customers</button>
            <button class="btn btn-primary" type="submit" onclick="setRecipientType('instructor'); this.form.sumbit();">Send to Instructors</button>
            <button class="btn btn-primary" type="submit" onclick="setRecipientType('both'); this.form.submit();">Send to Both</button>
        </div>
    </form>
</div>

</body>
</html>
