<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Upload Success</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/uploadSuccess.css">
</head>
<body style="background-color: #2E2E2E; color: white; font-family: 'Inter', sans-serif;">
<jsp:include page="../common/verticalNavBar.jsp" />
<div class="upload-success-container">
    <div class="success-message">
        <h1>Upload Successful!</h1>
        <p>Your file has been uploaded cxbxmf;lsuccessfully.</p>
        <button class="back-to-dashboard-button" onclick="window.location.href = '/dashboard';">Back to Dashboard</button>
    </div>
</div>
</body>
</html>
