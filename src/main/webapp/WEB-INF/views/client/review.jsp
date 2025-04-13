<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Your Review</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/review.css">
</head>
<body>
<div class="container">
  <div class="card review-form">
    <div class="card-header">
      <h3 class="text-center">Your Review</h3>
    </div>

    <div class="card-body">
      <!-- Review metadata with creation time -->
      <div class="review-meta">
        <div class="review-time">
          Posted on: <fmt:formatDate value="${review.createdAt}" pattern="MMMM d, yyyy" />
        </div>
      </div>

      <!-- Star Rating System -->
      <div class="form-group">
        <label class="form-label">Your Rating</label>
        <div class="star-rating read-only">
          <c:forEach var="i" begin="1" end="5">
            <c:choose>
              <c:when test="${i <= review.rating}">
                <span class="star filled">&#9733;</span>
              </c:when>
              <c:otherwise>
                <span class="star">&#9734;</span>
              </c:otherwise>
            </c:choose>
          </c:forEach>
        </div>
      </div>

      <!-- Review Text Area -->
      <div class="form-group">
        <label class="form-label">Your Review</label>
        <textarea readonly class="form-control">${review.review}</textarea>
      </div>

      <div class="form-actions">
        <a href="editReview" class="btn btn-primary">Edit Review</a>
      </div>
    </div>
  </div>
</div>
</body>
</html>