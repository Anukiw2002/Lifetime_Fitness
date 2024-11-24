<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Blog</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/updateBlog.css" />
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400&display=swap" rel="stylesheet">

    <!-- Include jQuery for easier DOM manipulation (optional) -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<jsp:include page="../common/verticalNavBar.jsp" />

<div class="update-container">
    <h2>Update Blog</h2>

    <!-- Form for updating a blog -->
    <form action="updateBlog" method="post" id="updateBlogForm">
        <!-- Blog ID (hidden field) -->
        <input type="hidden" name="id" value="${blog.id}" />

        <!-- Blog Title -->
        <label for="name">Blog Title:</label>
        <input type="text" id="name" name="name" value="${blog.name}" placeholder="Enter the blog title" required />

        <!-- Blog Description -->
        <label for="description">Blog Description:</label>
        <textarea id="description" name="description" rows="6" placeholder="Enter a brief description of the blog" required>${blog.description}</textarea>

        <!-- Blog Link -->
        <label for="link">Link to the Blog:</label>
        <input type="url" id="link" name="link" value="${blog.link}" placeholder="https://example.com/blog" required />

        <!-- Submit Button -->
        <button type="submit" class="submit-button">Update Blog</button>
    </form>

</div>

</body>
</html>
