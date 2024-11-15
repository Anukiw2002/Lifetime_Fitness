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
    <form action="${pageContext.request.contextPath}/UploadBlogServlet" method="post">
        <label for="blogTitle">Blog Title:</label>
        <input type="text" id="blogTitle" name="blogTitle" required />

        <label for="blogContent">Content:</label>
        <textarea id="blogContent" name="blogContent" rows="10" required></textarea>

        <label for="blogImage">Image URL (Optional):</label>
        <input type="url" id="blogImage" name="blogImage" placeholder="https://example.com/image.jpg" />

        <button type="submit" class="submit-button">Upload Blog</button>
    </form>
</div>
</body>
</html>
