<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Review</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/review.css">
</head>
<body>
<div class="container">
  <div class="card review-form">
    <div class="card-header">
      <h3 class="text-center">Rate and Review</h3>
    </div>

    <div class="card-body">
      <form method="POST" action="review" class="form-group">
        <!-- Star Rating System -->
        <div class="form-group">
          <label class="form-label">Your Rating</label>
          <div class="star-rating">
            <input type="radio" id="star5" name="rating" value="5">
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
        </div>

        <!-- Review Text Area -->
        <div class="form-group">
          <label for="review" class="form-label">Your Review</label>
          <textarea id="review" name="review" class="form-control" placeholder="Please share your experience" required rows="5"></textarea>
        </div>

        <!-- Form Buttons -->
        <div class="form-actions">
          <button type="button" class="btn btn-secondary">Cancel</button>
          <button type="submit" class="btn btn-primary">Submit</button>
        </div>
      </form>
    </div>
  </div>
</div>
</body>
</html>