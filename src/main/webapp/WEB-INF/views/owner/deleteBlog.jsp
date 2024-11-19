<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Delete Blog</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/deleteBlog.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400&display=swap" rel="stylesheet">
</head>
<body>
<jsp:include page="../common/verticalNavBar.jsp" />
<div class="delete-container">
    <h2>Delete Blog</h2>
    <form action="${pageContext.request.contextPath}/deleteBlog" method="POST">
        <label for="blogId">Enter Blog ID:</label>
        <input type="text" id="blogId" name="blogId" placeholder="Enter Blog ID" required>
        <button type="submit" class="delete-button">Delete Blog</button>
    </form>
</div>
</body>
</html>
