<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Your Review</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/review.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body>
<jsp:include page="../client/clientVerticalNavbar.jsp" />
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

      <div class="form-actions flex justify-end gap-md">
        <button onclick="showDeleteModal(${userId})" class="btn btn-secondary">Delete Review</button>
        <a href="editReview" class="btn btn-primary">Edit Review</a>
      </div>
    </div>
  </div>
</div>

<!-- Delete Review Modal -->
<div id="deleteModal" class="modal">
  <div class="card-modal">
    <button type="button" class="delete-icon" onclick="closeModal()">×</button>
    <div class="card-modal-header">
      <h3><i class="fas fa-exclamation-triangle"></i> Delete Review</h3>
    </div>
    <div class="card-modal-body">
      <p>Are you sure you want to proceed?</p>
      <p class="text-muted">This action cannot be undone.</p>
    </div>
    <div class="flex justify-end gap-md">
      <button type="button" onclick="closeModal()" class="btn btn-secondary">Cancel</button>
      <button type="button" onclick="deleteReview()" class="btn btn-danger">Yes, Delete</button>
    </div>
  </div>
</div>

<script>
  let currentUserId = null;
  const modal = document.getElementById("deleteModal");

  function showDeleteModal(userId) {
    currentUserId = userId;
    modal.style.display = "block";
  }

  function closeModal() {
    modal.style.display = "none";
    currentUserId = null;
  }

  async function deleteReview() {
    if (currentUserId) {
      try {
        const response = await fetch(`${pageContext.request.contextPath}/review/delete`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({ userId: currentUserId })
        });

        const data = await response.json();

        if (response.ok && data.status === 'success') {
          window.location.href = '${pageContext.request.contextPath}/insertReview';
        } else {
          alert('Error: ' + (data.message || 'Failed to delete the review.'));
        }
      } catch (error) {
        console.error('Delete review error:', error);
        alert('An error occurred while deleting the review: ' + error.message);
      }
      closeModal();
    }
  }
</script>
</body>
</html>