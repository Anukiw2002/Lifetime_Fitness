<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Blog</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/viewBlogs.css" />
</head>
<body>
<div class="main-content">
    <jsp:include page="../common/verticalNavBar.jsp" />

    <div class="container">
        <div class="card">
            <h2 class="text-center mb-4">Update Blog</h2>

            <!-- Show Error Message -->
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger">${errorMessage}</div>
            </c:if>

            <!-- Show Success Message -->
            <c:if test="${not empty successMessage}">
                <div class="alert alert-success">${successMessage}</div>
            </c:if>

            <!-- Form for updating a blog -->
            <form action="${pageContext.request.contextPath}/UpdateBlog" method="post" id="updateBlogForm">
                <!-- Blog ID (hidden) -->
                <input type="hidden" name="id" value="${blog.id}" />

                <!-- Blog Title -->
                <div class="form-group">
                    <label class="form-label" for="name">Blog Title:</label>
                    <input type="text"
                           id="name"
                           name="name"
                           class="form-control"
                           value="${blog.name}"
                           placeholder="Enter the blog title"
                           required />
                </div>

                <!-- Blog Description -->
                <div class="form-group">
                    <label class="form-label" for="description">Blog Description:</label>
                    <textarea id="description"
                              name="description"
                              class="form-control"
                              placeholder="Enter a brief description of the blog"
                              required>${blog.description}</textarea>
                </div>

                <!-- Blog Content -->
                <div class="form-group">
                    <label class="form-label" for="content">Blog Content:</label>
                    <textarea id="content"
                              name="content"
                              class="form-control"
                              placeholder="Enter the full blog content"
                              rows="8"
                              required>${blog.content}</textarea>
                </div>

                <!-- Submit Button -->
                <div class="flex justify-center mt-4">
                    <button type="submit" class="btn btn-primary">Update Blog</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>

