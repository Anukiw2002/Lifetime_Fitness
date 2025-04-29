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
  <jsp:include page="../common/verticalNavBar.jsp" />

  <div class="container">
    <div class="top-bar">
      <h2>All Videos</h2>

      <form action="${pageContext.request.contextPath}/UploadVideo" method="get">
        <button type="submit" class="btn btn-upload">Upload Video</button>
      </form>
    </div>

    <input
            type="text"
            id="searchInput"
            placeholder="Search videos by name..."
            class="search-input"
    />

    <c:if test="${not empty videos}">
      <div class="video-grid">
        <c:forEach var="video" items="${videos}">
          <div class="video-card">
            <div class="video-image">
              <img src="${pageContext.request.contextPath}/image2?id=${video.id}" width="200" height="auto" />
            </div>
            <div class="video-content">
              <h3 class="video-title">${video.name}</h3>
              <p>${video.description}</p>
              <div class="video-actions">
                <form action="${video.url}" method="get" target="_blank">
                  <button type="submit" class="btn btn-secondary">Watch</button>
                </form>
                <form action="${pageContext.request.contextPath}/UpdateVideo" method="get">
                  <input type="hidden" name="id" value="${video.id}" />
                  <button type="submit" class="btn btn-primary">Update</button>
                </form>
                <form action="${pageContext.request.contextPath}/DeleteVideo" method="post" onsubmit="return confirm('Are you sure you want to delete this video?');">
                  <input type="hidden" name="id" value="${video.id}" />
                  <button type="submit" class="btn btn-danger">Delete</button>
                </form>
              </div>
            </div>
          </div>
        </c:forEach>
      </div>
    </c:if>

    <c:if test="${empty videos}">
      <p class="no-videos">No videos uploaded yet.</p>
    </c:if>
  </div>
</div>

<script>
  document.getElementById("searchInput").addEventListener("input", function () {
    const searchValue = this.value.toLowerCase();
    const videoCards = document.querySelectorAll(".video-card");

    videoCards.forEach(card => {
      const title = card.querySelector(".video-title").textContent.toLowerCase();
      card.style.display = title.includes(searchValue) ? "block" : "none";
    });
  });
</script>

</body>
</html>
