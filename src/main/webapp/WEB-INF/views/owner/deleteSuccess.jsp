<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Delete Success</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/deleteSuccess.css">
</head>
<body>
<jsp:include page="../common/verticalNavBar.jsp" />
<div class="delete-success-container">
    <div class="success-message">
        <h1>Delete Successful!</h1>
        <p>The selected item has been deleted successfully.</p>
        <button class="back-to-dashboard-button" onclick="window.location.href = '/dashboard';">Back to Dashboard</button>
    </div>
</div>
</body>
</html>
