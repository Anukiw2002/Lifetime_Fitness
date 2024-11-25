<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Upload Blog</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/uploadBlog.css" />
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400&display=swap" rel="stylesheet">

    <!-- Include jQuery for easier DOM manipulation (optional) -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<jsp:include page="../common/verticalNavBar.jsp" />

<div class="upload-container">
    <h2>Upload a Blog</h2>

    <!-- Form for uploading a blog -->
    <form action="uploadBlog" method="post" id="uploadBlogForm">
        <!-- Blog Title -->
        <label for="name">Blog Title:</label>
        <input type="text" id="name" name="name" placeholder="Enter the blog title" required />


        <!-- Blog Description -->
        <label for="description">Blog Description:</label>
        <textarea id="description" name="description" rows="6" placeholder="Enter a brief description of the blog" required></textarea>
        <!-- Blog Link -->


        <label for="link">Link to the Blog:</label>
        <input type="url" id="link" name="link" placeholder="https://example.com/blog" required />



        <!-- Submit Button -->
        <button type="submit" class="submit-button">Upload Blog</button>
    </form>

</div>



</body>
</html>
