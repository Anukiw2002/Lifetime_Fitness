<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Blogs</title>

    <!-- Link to external CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/viewBlogs.css" />
</head>
<body>

<div class="main-content">
    <jsp:include page="../common/verticalNavBar.jsp" />
    <div class="container">
        <!-- Header Section -->
        <div class="flex justify-between items-center mb-4">
            <h2>All Blogs</h2>
            <form action="${pageContext.request.contextPath}/uploadBlog" method="get">
                <button type="submit" class="btn btn-primary">Upload Blog</button>
            </form>
        </div>

        <!-- Blog Content Section -->
        <div class="card">
            <c:if test="${not empty blogs}">
                <table class="blog-table">
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Description</th>
                        <th>View</th>
                        <th>Update</th>
                        <th>Delete</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="blog" items="${blogs}">
                        <tr>
                            <td>${blog.name}</td>
                            <td>${blog.description}</td>
                            <td>
                                <form action="${pageContext.request.contextPath}/viewEachBlog" method="get">
                                    <input type="hidden" name="id" value="${blog.id}" />
                                    <button type="submit" class="btn btn-secondary">View</button>
                                </form>
                            </td>
                            <td>
                                <form action="${pageContext.request.contextPath}/updateBlog" method="get">
                                    <input type="hidden" name="id" value="${blog.id}" />
                                    <button type="submit" class="btn btn-primary">Update</button>
                                </form>
                            </td>
                            <td>
                                <form action="${pageContext.request.contextPath}/deleteBlog" method="post">
                                    <input type="hidden" name="id" value="${blog.id}" />
                                    <button type="submit" class="btn btn-danger">Delete</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>

            <c:if test="${empty blogs}">
                <p class="no-blogs">No blogs uploaded yet.</p>
            </c:if>
        </div>
    </div>
</div>

</body>
</html>