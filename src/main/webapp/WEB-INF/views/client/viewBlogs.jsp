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

    <style>
        .search-input {
            padding: 8px 12px;
            margin-bottom: 20px;
            width: 100%;
            max-width: 400px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 16px;
        }
    </style>
</head>
<body>

<div class="main-content">
    <jsp:include page="../client/clientVerticalNavbar.jsp" />

    <div class="container">

        <div class="flex justify-between items-center mb-4">
            <h2>All Blogs</h2>
        </div>

        <!-- 🔍 Search Bar -->
        <input
                type="text"
                id="searchInput"
                placeholder="Search blogs by name..."
                class="search-input"
        />
        <!--<button id="searchBtn" class="btn btn-primary" style="padding: 8px 16px;">Search</button>-->

        <!-- 📄 Blog Content Section -->
        <div class="card">
            <c:if test="${not empty blogs}">
                <table class="blog-table">
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Description</th>
                        <th>View Blog</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="blog" items="${blogs}">
                        <tr>
                            <td>${blog.name}</td>
                            <td>${blog.description}</td>
                            <td>
                                <form action="${pageContext.request.contextPath}/ViewEachBlogClient" method="get">
                                    <input type="hidden" name="id" value="${blog.id}" />
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

<!-- 🔧 JavaScript to filter blogs by name -->
<script>
    document.getElementById("searchInput").addEventListener("keyup", function () {
        const filter = this.value.toLowerCase();
        const rows = document.querySelectorAll(".blog-table tbody tr");

        rows.forEach(row => {
            const blogName = row.cells[0].textContent.toLowerCase();
            row.style.display = blogName.includes(filter) ? "" : "none";
        });
    });
</script>

</body>
</html>


