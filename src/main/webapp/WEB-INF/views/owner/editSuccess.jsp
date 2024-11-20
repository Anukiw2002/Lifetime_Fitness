<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Success</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/editSuccess.css">
</head>
<body>
<jsp:include page="../common/verticalNavBar.jsp" />
<div class="edit-success-container">
    <div class="success-message">
        <h1>Edit Successful!</h1>
        <p>Your changes have been saved successfully.</p>
        <button class="back-to-dashboard-button" onclick="window.location.href = '/dashboard';">Back to Dashboard</button>
    </div>
</div>
</body>
</html>
