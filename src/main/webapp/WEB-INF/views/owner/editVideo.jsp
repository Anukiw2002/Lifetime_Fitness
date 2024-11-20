<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Video</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/editBlog.css" />
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400&display=swap" rel="stylesheet">
</head>
<body>
<jsp:include page="../common/verticalNavBar.jsp" />

<div class="edit-container">
    <h2>Edit Video</h2>

    <!-- Step 1: Form to Request Video ID -->
    <c:if test="${empty video}">
        <form action="${pageContext.request.contextPath}/getVideoDetails" method="post">
            <label for="videoId">Enter Video ID:</label>
            <input type="text" id="videoId" name="videoId" placeholder="Enter Video ID" required />
            <button type="submit" class="submit-button">Fetch Video Details</button>
        </form>
    </c:if>

    <!-- Step 2: Edit Video Details -->
    <c:if test="${not empty video}">
        <form action="${pageContext.request.contextPath}/editVideo" method="post">
            <label for="videoId">Video ID:</label>
            <input type="text" id="videoId" name="videoId" value="${video.id}" readonly />

            <label for="videoTitle">Video Title:</label>
            <input type="text" id="videoTitle" name="videoTitle" value="${video.title}" required />

            <label for="videoLink">Video Link:</label>
            <input type="url" id="videoLink" name="videoLink" value="${video.link}" placeholder="https://example.com/video" required />

            <label for="videoDescription">Video Description:</label>
            <textarea id="videoDescription" name="videoDescription" rows="10" required>${video.description}</textarea>

            <button type="submit" class="submit-button">Update Video</button>
        </form>
    </c:if>
</div>

</body>
</html>
