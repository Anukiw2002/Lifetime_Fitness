<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Delete Video</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/deleteBlog.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400&display=swap" rel="stylesheet">
    <script>
        function confirmDeletion(event) {
            const isConfirmed = confirm("Are you sure you want to delete this video?");
            if (!isConfirmed) {
                event.preventDefault(); // Prevent form submission if user cancels
            }
        }
    </script>
</head>
<body>
<jsp:include page="../common/verticalNavBar.jsp" />
<div class="delete-container">
    <h2>Delete Video</h2>
    <form action="${pageContext.request.contextPath}/deleteVideo" method="POST" onsubmit="confirmDeletion(event)">
        <label for="videoId">Enter Video ID:</label>
        <input type="text" id="videoId" name="videoId" placeholder="Enter Video ID" required>
        <button type="submit" class="delete-button">Delete Video</button>
    </form>
</div>
</body>
</html>
