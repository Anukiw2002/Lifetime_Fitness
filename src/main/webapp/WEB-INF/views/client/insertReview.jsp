<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Review</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/review.css">
</head>
<body>
<jsp:include page="../client/clientVerticalNavbar.jsp" />
<div class="container">
  <div class="card review-form">
    <div class="card-header">
      <h3 class="text-center">Rate and Review</h3>
    </div>

    <div class="card-body">
      <form method="POST" action="insertReview" class="form-group" id="reviewForm" novalidate>
        <!-- Star Rating System -->
        <div class="form-group">
          <label class="form-label">Your Rating</label>
          <div class="star-rating-wrapper">
            <div class="star-rating">
              <input type="radio" id="star5" name="rating" value="5" required>
              <label for="star5" title="5 stars"></label>

              <input type="radio" id="star4" name="rating" value="4">
              <label for="star4" title="4 stars"></label>

              <input type="radio" id="star3" name="rating" value="3">
              <label for="star3" title="3 stars"></label>

              <input type="radio" id="star2" name="rating" value="2">
              <label for="star2" title="2 stars"></label>

              <input type="radio" id="star1" name="rating" value="1">
              <label for="star1" title="1 star"></label>
            </div>
            <div class="error-message" id="ratingError"></div>
          </div>
        </div>

        <!-- Review Text Area -->
        <div class="form-group">
          <label for="review" class="form-label">Your Review</label>
          <textarea id="review" name="review" class="form-control" placeholder="Please share your experience" required rows="5" minlength="10" maxlength="500"></textarea>
          <div class="error-message" id="reviewError"></div>
        </div>

        <!-- Form Buttons -->
        <div class="form-actions">
          <button type="button" class="btn btn-secondary" id="cancelButton">Cancel</button>
          <button type="submit" class="btn btn-primary">Submit</button>
        </div>
      </form>
    </div>
  </div>
</div>

<script>
  document.addEventListener('DOMContentLoaded', function() {
    // Character counter for review text
    const reviewTextarea = document.getElementById('review');
    const charCountDisplay = document.getElementById('charCount');

    reviewTextarea.addEventListener('input', function() {
      const currentLength = this.value.length;
      charCountDisplay.textContent = `${currentLength}/500`;
    });

    // Form validation
    const reviewForm = document.getElementById('reviewForm');

    reviewForm.addEventListener('submit', function(event) {
      let isValid = true;

      // Clear previous error messages
      document.querySelectorAll('.error-message').forEach(el => {
        el.textContent = '';
      });

      // Validate star rating
      const starRating = document.querySelector('input[name="rating"]:checked');
      if (!starRating) {
        document.getElementById('ratingError').textContent = 'Please select a rating';
        isValid = false;
      }

      // Validate review text
      const reviewText = reviewTextarea.value.trim();
      if (reviewText === '') {
        document.getElementById('reviewError').textContent = 'Please write a review';
        isValid = false;
      } else if (reviewText.length < 10) {
        document.getElementById('reviewError').textContent = 'Review should be at least 10 characters';
        isValid = false;
      } else if (reviewText.length > 500) {
        document.getElementById('reviewError').textContent = 'Review should not exceed 500 characters';
        isValid = false;
      }

      if (!isValid) {
        event.preventDefault();
      }
    });

    // Cancel button functionality
    document.getElementById('cancelButton').addEventListener('click', function() {
      window.history.back();
    });
  });
</script>
</body>
</html>