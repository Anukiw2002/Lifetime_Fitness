<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Upload Video</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/uploadVideo.css" />
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400&display=swap" rel="stylesheet">
</head>
<body>
<jsp:include page="../common/verticalNavBar.jsp" />
<div class="upload-container">
    <h2>Upload a Video</h2>
    <form action="${pageContext.request.contextPath}/uploadVideo" method="post" class="payment-form">
        <label for="videoName">Video Name:</label>
        <input type="text" id="videoName" name="videoName" required />

        <label for="videoLink">YouTube Video Link:</label>
        <input type="url" id="videoLink" name="videoLink" placeholder="https://youtube.com/..." required />

        <label for="videoDescription">Description:</label>
        <textarea id="videoDescription" name="videoDescription" rows="5" required></textarea>

        <button type="submit" class="submit-button">Upload Video</button>
    </form>
</div>
</body>
</html>
