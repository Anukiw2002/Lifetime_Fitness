<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>View Blogs</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/viewBlogs.css" />
</head>
<body>

<div class="main-content">
    <jsp:include page="../instructor/instructorVerticalNavbar.jsp" />

    <div class="container">
        <h2>All Blogs</h2>

        <input
                type="text"
                id="searchInput"
                placeholder="Search blogs by name..."
                class="search-input"
        />

        <c:if test="${not empty blogs}">
            <div class="blog-grid" id="blogGrid">
                <c:forEach var="blog" items="${blogs}">
                    <div class="blog-card">
                        <div class="blog-image">
                            <img src="${pageContext.request.contextPath}/image?id=${blog.id}" alt="Blog Image" />
                        </div>
                        <div class="blog-content">
                            <h3 class="blog-title">${blog.name}</h3>
                            <p>${blog.description}</p>
                            <form action="${pageContext.request.contextPath}/ViewEachBlogClient" method="get">
                                <input type="hidden" name="id" value="${blog.id}" />
                                <button type="submit" class="btn btn-secondary">View</button>
                            </form>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:if>

        <c:if test="${empty blogs}">
            <p class="no-blogs">No blogs available at the moment.</p>
        </c:if>
    </div>
</div>

<script>
    document.getElementById("searchInput").addEventListener("input", function () {
        const searchValue = this.value.toLowerCase();
        const blogCards = document.querySelectorAll(".blog-card");

        blogCards.forEach(card => {
            const blogTitle = card.querySelector(".blog-title").textContent.toLowerCase();
            card.style.display = blogTitle.includes(searchValue) ? "block" : "none";
        });
    });
</script>

</body>
</html>
