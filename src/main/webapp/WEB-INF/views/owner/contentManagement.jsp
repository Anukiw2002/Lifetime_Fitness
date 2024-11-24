<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Content Management</title>

    <!-- Link to external CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/contentManagementOwner.css" />

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400&display=swap" rel="stylesheet">
</head>
<body style="font-family: 'Inter', sans-serif;">
<jsp:include page="../common/verticalNavBar.jsp" />

<div class="learning-content-customer">
    <div class="content-section">
        <!-- Upload Video Button -->
        <div class="button-container1">
            <button class="action-button" onclick="location.href='${pageContext.request.contextPath}/uploadVideo'">Upload Video</button>
        </div>

        <h2 class="section-title">Checkout Our Videos</h2>
        <div class="video-section">
            <!-- Dynamically loaded videos -->
            <c:if test="${not empty videoList}">
                <c:forEach var="video" items="${videoList}">
                    <div class="video-box">
                        <p>${video.title}</p>
                        <button class="small-action-button" onclick="location.href='editVideo?id=${video.id}'">Edit</button>
                        <button class="small-action-button" onclick="location.href='deleteVideo?id=${video.id}'">Delete</button>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test="${empty videoList}">
                <p>No videos uploaded yet.</p>
            </c:if>
        </div>

        <!-- Upload Blog Button -->
        <div class="button-container2">
            <button class="action-button" onclick="location.href='${pageContext.request.contextPath}/uploadBlog'">Upload Blog</button>
        </div>

        <h2 class="section-title">Read Our Blog</h2>
        <div class="blog-section">
            <!-- Dynamically loaded blogs -->
            <c:if test="${not empty blogList}">
                <c:forEach var="blog" items="${blogList}">
                    <div class="blog-box">
                        <p>${blog.title}</p>
                        <button class="small-action-button" onclick="location.href='editBlog?id=${blog.id}'">Edit</button>
                        <button class="small-action-button" onclick="location.href='deleteBlog?id=${blog.id}'">Delete</button>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test="${empty blogList}">
                <p>No blogs uploaded yet.</p>
            </c:if>
        </div>
    </div>
</div>

</body>
</html>
