<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Videos</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/viewVideos.css" />
</head>
<body>

<div class="main-content">
    <jsp:include page="../instructor/instructorViewBlogs.jsp" />

    <div class="container">
        <!-- Header Section -->
        <div class="flex justify-between items-center mb-4">
            <h2>All Videos</h2>
        </div>

        <input
                type="text"
                id="searchInput"
                placeholder="Search blogs by name..."
                class="search-input"
        />

        <!-- Video Content Section -->
        <div class="card">
            <c:if test="${not empty videos}">
                <table class="video-table">
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Watch Video</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="video" items="${videos}">
                        <tr>
                            <td>${video.name}</td>
                            <td>${video.description}</td>
                            <td>
                                <form action="${video.url}" method="get" target="_blank">
                                    <button type="submit" class="btn btn-secondary">Watch Video</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>

            <c:if test="${empty videos}">
                <p class="no-videos">No videos available at the moment.</p>
            </c:if>
        </div>
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
