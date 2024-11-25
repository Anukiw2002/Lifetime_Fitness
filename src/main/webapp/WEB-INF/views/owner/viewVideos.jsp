<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>All Videos</title>
  <link rel="stylesheet" href="<c:url value='/css/style.css' />"> <!-- Link to a CSS file -->
</head>
<body>
<div class="container">
  <h1>All Videos</h1>

  <!-- Display error message if any -->
  <c:if test="${not empty errorMessage}">
    <div class="error-message">
      <p>${errorMessage}</p>
    </div>
  </c:if>

  <!-- Check if there are videos to display -->
  <c:if test="${empty videos}">
    <p>No videos found.</p>
  </c:if>

  <!-- Table to display videos -->
  <c:if test="${not empty videos}">
    <table border="1" class="videos-table">
      <thead>
      <tr>
        <th>#</th>
        <th>Title</th>
        <th>Description</th>
        <th>Actions</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="video" items="${videos}" varStatus="status">
        <tr>
          <td>${status.index + 1}</td>
          <td>${video.name}</td>
          <td>${video.description}</td>
          <td>
            <!-- Link to open the video player in a new tab -->
            <a href="playVideo?id=${video.id}" target="_blank">Watch Video</a>
          </td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </c:if>
</div>
</body>
</html>
