<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Upload Blog</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/viewBlogs.css" />
</head>
<body>
<div class="main-content">
    <jsp:include page="../common/verticalNavBar.jsp" />

    <div class="container">
        <div class="card">
            <h2 class="text-center mb-4">Upload a Blog</h2>

            <!-- Display error message if any -->
            <c:if test="${not empty errorMessage}">
                <div class="error-message">${errorMessage}</div>
            </c:if>

            <!-- Form for uploading a blog -->
            <form action="UploadBlog" method="post" id="uploadBlogForm">
                <!-- Blog Title -->
                <div class="form-group">
                    <label class="form-label" for="name">Blog Title:</label>
                    <input type="text"
                           id="name"
                           name="name"
                           class="form-control"
                           placeholder="Enter the blog title"
                           required />
                </div>

                <!-- Blog Description -->
                <div class="form-group">
                    <label class="form-label" for="description">Blog Description:</label>
                    <textarea id="description"
                              name="description"
                              class="form-control"
                              placeholder="Enter a short description"
                              required></textarea>
                </div>

                <!-- Blog Content -->
                <div class="form-group">
                    <label class="form-label" for="content">Blog Content:</label>
                    <textarea id="content"
                              name="content"
                              class="form-control"
                              placeholder="Write your full blog content here..."
                              rows="10"
                              required></textarea>
                </div>

                <!-- Submit Button -->
                <div class="flex justify-center mt-4">
                    <button type="submit" class="btn btn-primary">Upload Blog</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
