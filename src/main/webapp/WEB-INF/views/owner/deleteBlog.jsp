<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Delete Blog</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/deleteBlog.css" />
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400&display=swap" rel="stylesheet">

    <!-- Include jQuery for easier DOM manipulation (optional) -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<jsp:include page="../common/verticalNavBar.jsp" />

<div class="delete-container">
    <h2>Delete Blog</h2>

    <!-- Form for confirming blog deletion -->
    <form action="deleteBlog" method="post" id="deleteBlogForm">
        <!-- Blog ID (hidden field) -->
        <input type="hidden" name="id" value="${blog.id}" />

        <p>Are you sure you want to delete the blog titled <strong>${blog.name}</strong>?</p>
        <p>This action cannot be undone.</p>

        <!-- Confirm and Cancel buttons -->
        <button type="submit" class="confirm-button">Yes, Delete</button>
        <a href="${pageContext.request.contextPath}/viewBlogs.jsp" class="cancel-button">Cancel</a>
    </form>

</div>

</body>
</html>
