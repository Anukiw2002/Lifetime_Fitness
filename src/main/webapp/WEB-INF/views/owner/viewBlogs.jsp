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
    <jsp:include page="../common/verticalNavBar.jsp" />

    <div class="container">
        <div class="top-bar">
            <h2>All Blogs</h2>

            <form action="${pageContext.request.contextPath}/UploadBlog" method="get">
                <button type="submit" class="btn btn-upload">Upload Blog</button>
            </form>
        </div>

        <input
                type="text"
                id="searchInput"
                placeholder="Search blogs by name..."
                class="search-input"
        />

        <c:if test="${not empty blogs}">
            <div class="blog-grid">
                <c:forEach var="blog" items="${blogs}">
                    <div class="blog-card">
                        <div class="blog-image">
                            <img src="${pageContext.request.contextPath}/image?id=${blog.id}" width="200" height="auto" />
                        </div>
                        <div class="blog-content">
                            <h3>${blog.name}</h3>
                            <p>${blog.description}</p>
                            <div class="blog-actions">
                                <form action="${pageContext.request.contextPath}/ViewEachBlog" method="get">
                                    <input type="hidden" name="id" value="${blog.id}" />
                                    <button type="submit" class="btn btn-secondary">View</button>
                                </form>
                                <form action="${pageContext.request.contextPath}/UpdateBlog" method="get">
                                    <input type="hidden" name="id" value="${blog.id}" />
                                    <button type="submit" class="btn btn-primary">Update</button>
                                </form>
                                <form action="${pageContext.request.contextPath}/DeleteBlog" method="post" onsubmit="return confirm('Are you sure you want to delete this blog?');">
                                    <input type="hidden" name="id" value="${blog.id}" />
                                    <button type="submit" class="btn btn-danger">Delete</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:if>

        <c:if test="${empty blogs}">
            <p class="no-blogs">No blogs uploaded yet.</p>
        </c:if>
    </div>
</div>


<script>
    document.getElementById("searchInput").addEventListener("input", function () {
        const searchValue = this.value.toLowerCase();
        const blogCards = document.querySelectorAll(".blog-card");

        blogCards.forEach(card => {
            const title = card.querySelector(".blog-title").textContent.toLowerCase();
            card.style.display = title.includes(searchValue) ? "block" : "none";
        });
    });
</script>

</body>
</html>
