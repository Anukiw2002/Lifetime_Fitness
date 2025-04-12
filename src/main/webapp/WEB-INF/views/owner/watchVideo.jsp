<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>${video.name}</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            padding: 20px;
            background: #f5f5f5;
        }

        .video-container {
            max-width: 800px;
            margin: auto;
            background: white;
            padding: 20px;
            border-radius: 10px;
        }

        iframe {
            width: 100%;
            height: 400px;
            border: none;
            margin-top: 20px;
        }
    </style>
</head>
<body>
<div class="video-container">
    <h2>${video.name}</h2>
    <p>${video.description}</p>

    <c:if test="${not empty video.url}">
        <iframe src="${video.url}" allowfullscreen></iframe>
    </c:if>

    <br/>
    <a href="${pageContext.request.contextPath}/viewVideos" class="btn btn-primary">← Back to All Videos</a>
</div>
</body>
</html>
