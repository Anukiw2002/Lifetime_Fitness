<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Send Notification</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ownerCreateNotification.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
    <script>
        // Validate the message input before submitting
        function validateAndSubmit(form, recipientType) {
            const message = document.getElementById('message').value.trim();
            if (message === "") {
                alert("Please enter a notification message.");
                return false;
            }
            document.getElementById('recipientType').value = recipientType;
            form.submit();
        }
    </script>
</head>
<body>
<div class="container">
    <h1>Send a Notification</h1>

    <form action="${pageContext.request.contextPath}/createNotificationRedirection" method="POST">
        <input type="text" name="notificationTitle" placeholder="Enter notification title" required />
        <!-- Notification Message -->
        <textarea id="message" name="notificationMessage" placeholder="Type your message here..." required></textarea>
        <br><br>

        <!-- Hidden Field for Recipient Type -->
        <input type="hidden" id="recipientType" name="targetGroup" value="">

        <!-- Action Buttons -->
        <div class="buttons">
            <button class="btn btn-primary" type="button" onclick="validateAndSubmit(this.form, 'customer')">Send to Customers</button>
            <button class="btn btn-primary" type="button" onclick="validateAndSubmit(this.form, 'instructor')">Send to Instructors</button>
            <button class="btn btn-primary" type="button" onclick="validateAndSubmit(this.form, 'both')">Send to Both</button>
        </div>
    </form>
</div>
</body>
</html>
