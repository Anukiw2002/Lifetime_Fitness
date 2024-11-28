<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Blogs</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/viewBlogs.css" />
</head>
<body>

<div class="main-content">
    <jsp:include page="clientVerticalNavbar.jsp" />

    <div class="container">
        <!-- Header Section -->
        <div class="flex justify-between items-center mb-4">
            <h2>All Blogs</h2>
        </div>

        <!-- Blog Content Section -->
        <div class="card">
            <c:if test="${not empty blogs}">
                <table class="blog-table">
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Link</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="blog" items="${blogs}">
                        <tr>
                            <td>${blog.name}</td>
                            <td>${blog.description}</td>
                            <td>
                                <form action="${blog.link}" method="get" target="_blank">
                                    <button type="submit" class="btn btn-secondary">View</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>

            <c:if test="${empty blogs}">
                <p class="no-blogs">No blogs available at the moment.</p>
            </c:if>
        </div>
    </div>
</div>

</body>
</html>