<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Blogs</title>

    <!-- Link to external CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/viewBlogs.css" />

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400&display=swap" rel="stylesheet">
</head>
<body style="font-family: 'Inter', sans-serif;">



<div class="learning-content-customer">
    <jsp:include page="../common/verticalNavBar.jsp" />
    <div class="content-section">
        <!-- Header with "All Blogs" title and Upload Blog button -->
        <div class="header-section">
            <h2 class="section-title">All Blogs</h2>
            <form action="${pageContext.request.contextPath}/uploadBlog" method="get" style="display: inline;">
                <button type="submit" class="upload-btn">Upload Blog</button>
            </form>
        </div>

        <!-- Display blogs table if blogs list is not empty -->
        <div class="blog-section">
            <c:if test="${not empty blogs}">
                <table border="1" cellpadding="10" class="blog-table">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Link</th>
                        <th>Update a Blog</th>
                        <th>Delete a Blog</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="blog" items="${blogs}">
                        <tr>
                            <td>${blog.id}</td>
                            <td>${blog.name}</td>
                            <td>${blog.description}</td>
                            <td>
                                <!-- View button styled as a button -->
                                <form action="${blog.link}" method="get" style="display: inline;" target="_blank">
                                    <button type="submit" class="view-btn">View</button>
                                </form>
                            </td>
                            <td>
                                <!-- Update button -->
                                <form action="${pageContext.request.contextPath}/updateBlog" method="get" style="display: inline;">
                                    <input type="hidden" name="id" value="${blog.id}" />
                                    <button type="submit" class="update-btn">Update</button>
                                </form>
                            </td>
                            <td>
                                <!-- Delete button -->
                                <form action="${pageContext.request.contextPath}/deleteBlog" method="post" style="display: inline;">
                                    <input type="hidden" name="id" value="${blog.id}" />
                                    <button type="submit" class="delete-btn">Delete</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>

            <!-- If no blogs available -->
            <c:if test="${empty blogs}">
                <p>No blogs uploaded yet.</p>
            </c:if>
        </div>
    </div>
</div>

</body>
</html>
