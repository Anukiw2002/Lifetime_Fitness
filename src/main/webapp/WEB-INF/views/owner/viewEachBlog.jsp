<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Blog Details</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/viewBlogs.css" />
</head>
<body>

<div class="main-content">
    <jsp:include page="../common/verticalNavBar.jsp" />

    <div class="container">
        <!-- Blog Header -->
        <div class="blog-header mb-4">
            <h2>${name}</h2>
            <p><strong>Description:</strong> ${description}</p>
            <hr/>
        </div>

        <!-- Blog Content -->
        <div class="blog-content">
            <h3>Blog Content</h3>
            <p>${content}</p>
        </div>

        <!-- Back Button -->
        <div class="mt-4">
            <a href="${pageContext.request.contextPath}/GetAllBlogs" class="btn btn-upload">Back to Blogs</a>
        </div>
    </div>
</div>
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
