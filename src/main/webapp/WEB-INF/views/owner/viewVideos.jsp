<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>View Videos</title>

  <!-- Link to external CSS -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/viewBlogs.css" />
</head>
<body>

<div class="main-content">
  <jsp:include page="../common/verticalNavBar.jsp" />
  <div class="container">
    <!-- Header Section -->
    <div class="flex justify-between items-center mb-4">
      <h2>All Videos</h2>
      <form action="${pageContext.request.contextPath}/uploadVideo" method="get">
        <button type="submit" class="btn btn-primary">Add Video</button>
      </form>
    </div>

    <!-- Videos Content Section -->
    <div class="card">
      <c:if test="${not empty videos}">
        <table class="video-table">
          <thead>
          <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Watch Video</th>
            <th>Update</th>
            <th>Delete</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach var="video" items="${videos}">
            <tr>
              <td>${video.name}</td>
              <td>${video.description}</td>
              <td>
                <a href="${video.url}" target="_blank" class="btn btn-secondary">Watch</a>
              </td>
              <td>
                <form action="${pageContext.request.contextPath}/updateVideo" method="get">
                  <input type="hidden" name="id" value="${video.id}" />
                  <button type="submit" class="btn btn-primary">Update</button>
                </form>
              </td>
              <td>
                <form action="${pageContext.request.contextPath}/deleteVideo" method="post">
                  <input type="hidden" name="id" value="${video.id}" />
                  <button type="submit" class="btn btn-danger">Delete</button>
                </form>
              </td>
            </tr>
          </c:forEach>
          </tbody>
        </table>
      </c:if>

      <c:if test="${empty videos}">
        <p class="no-videos">No videos uploaded yet.</p>
      </c:if>
    </div>
  </div>
</div>

</body>
</html>
