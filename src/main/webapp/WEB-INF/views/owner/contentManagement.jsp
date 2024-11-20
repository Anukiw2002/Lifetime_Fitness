<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Content Management</title>

    <!-- Link to existing navbar and main styles -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/contentManagementOwner.css" />

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400&display=swap" rel="stylesheet">
</head>
<body style="background-color: #2E2E2E; color: white; font-family: 'Inter', sans-serif;">
<jsp:include page="../common/verticalNavBar.jsp" />


<div class="learning-content-customer">
    <div class="content-section">
        <!-- Button container for Video Upload and Edit -->
        <div class="button-container1">
            <button class="action-button" onclick="location.href='${pageContext.request.contextPath}/uploadVideo'">Upload</button>
            <button class="action-button" onclick="location.href='${pageContext.request.contextPath}/editVideo.jsp'">Edit</button>
            <button class="action-button" onclick="location.href='${pageContext.request.contextPath}/deleteVideo'">Edit</button>
        </div>

        <h2 class="section-title">Checkout our Videos</h2>
        <div class="video-section">
            <div class="video-box">Video 1</div>
            <div class="video-box">Video 2</div>
            <div class="video-box">Video 3</div>
            <div class="video-box">Video 4</div>
            <div class="video-box">Video 5</div>
            <div class="video-box">Video 6</div>
        </div>

        <!-- Button container for Blog Upload and Edit -->
        <div class="button-container2">
            <button class="action-button" onclick="location.href='${pageContext.request.contextPath}/uploadBlog.jsp'">Upload</button>
            <button class="action-button" onclick="location.href='${pageContext.request.contextPath}/editBlog.jsp'">Edit</button>
            <button class="action-button" onclick="location.href='${pageContext.request.contextPath}/deleteBlog.jsp'">Edit</button>
        </div>

        <h2 class="section-title">Read Our Blog</h2>
        <div class="blog-section">
            <div class="blog-box">Blog 1</div>
            <div class="blog-box">Blog 2</div>
            <div class="blog-box">Blog 3</div>
            <div class="blog-box">Blog 4</div>
            <div class="blog-box">Blog 5</div>
            <div class="blog-box">Blog 6</div>
        </div>
    </div>
</div>

</body>
</html>
