<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Upload Blog</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/uploadBlog.css" />
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400&display=swap" rel="stylesheet">
</head>
<body>
<jsp:include page="../common/verticalNavBar.jsp" />
<div class="upload-container">
    <h2>Upload a Blog</h2>
    <form action="${pageContext.request.contextPath}/uploadBlog" method="post">
        <!-- Blog Title -->
        <label for="blogTitle">Blog Title:</label>
        <input type="text" id="blogTitle" name="blogTitle" placeholder="Enter the blog title" required />

        <!-- Blog Link -->
        <label for="blogLink">Link to the Blog:</label>
        <input type="url" id="blogLink" name="blogLink" placeholder="https://example.com/blog" required />

        <!-- Blog Description -->
        <label for="blogDescription">Blog Description:</label>
        <textarea id="blogDescription" name="blogDescription" rows="6" placeholder="Enter a brief description of the blog" required></textarea>

        <!-- Submit Button -->
        <button type="submit" class="submit-button">Upload Blog</button>
    </form>
</div>
</body>
</html>
