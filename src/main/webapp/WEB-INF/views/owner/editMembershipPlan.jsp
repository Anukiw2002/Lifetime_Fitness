<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Edit Membership Plan</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/addMembershipPlan.css">
</head>
<body>
<div class="container">
  <h1>Edit Membership Plan</h1>
  <form id="membershipForm" action="updatePlan" method="POST">
    <div class="form-group">
      <label for="planName">Plan Name</label>
      <input type="text" id="planName" name="planName" value="Platinum Membership" required>
    </div>

    <div class="form-group">
      <label for="timeRange">Time Range</label>
      <input type="text" id="timeRange" name="timeRange" value="4:00 am to 12:00 Midnight" required>
    </div>

    <div class="form-group duration-container">
      <label>Duration Options</label>
      <div class="duration-options">
        <button type="button" class="btn-add-duration">+ Add Duration Option</button>
        <div class="duration-item">
          <input type="number" name="durationValue[]" value="6" min="1" required>
          <select name="durationType[]" required>
            <option value="months" selected>Months</option>
            <option value="days">Days</option>
            <option value="years">Years</option>
          </select>
          <input type="number" name="durationPrice[]" value="1500" required>
          <button type="button" class="btn-remove-duration">×</button>
        </div>
        <div class="duration-item">
          <input type="number" name="durationValue[]" value="12" min="1" required>
          <select name="durationType[]" required>
            <option value="months" selected>Months</option>
            <option value="days">Days</option>
            <option value="years">Years</option>
          </select>
          <input type="number" name="durationPrice[]" value="2800" required>
          <button type="button" class="btn-remove-duration">×</button>
        </div>
      </div>
    </div>

    <div class="form-group">
      <label>Pricing Structure</label>
      <div class="pricing-type">
        <input type="radio" id="uniformPricing" name="pricingType" value="uniform">
        <label for="uniformPricing">Uniform Pricing</label>

        <input type="radio" id="categoryPricing" name="pricingType" value="category" checked>
        <label for="categoryPricing">Category-based Pricing</label>
      </div>
    </div>

    <div class="pricing-container" id="uniformPricingContainer" style="display: none;">
      <div class="price-group">
        <label for="uniformPrice">Price (Rs.)</label>
        <input type="number" id="uniformPrice" name="uniformPrice" placeholder="Enter price">
      </div>
    </div>

    <div class="pricing-container" id="categoryPricingContainer">
      <div class="price-group">
        <label for="gentsPrice">Gents Price (Rs.)</label>
        <input type="number" id="gentsPrice" name="gentsPrice" value="2000" required>
      </div>

      <div class="price-group">
        <label for="ladiesPrice">Ladies Price (Rs.)</label>
        <input type="number" id="ladiesPrice" name="ladiesPrice" value="1800" required>
      </div>

      <div class="price-group">
        <label for="couplePrice">Couple Price (Rs.)</label>
        <input type="number" id="couplePrice" name="couplePrice" value="3500" required>
      </div>
    </div>

    <div class="features-container">
      <label>Features</label>
      <div class="features-list">
        <div class="feature-item">
          <input type="checkbox" id="feature1" name="features" value="premium" checked>
          <label for="feature1">Access to all premium equipment</label>
        </div>
        <div class="feature-item">
          <input type="checkbox" id="feature2" name="features" value="trainer" checked>
          <label for="feature2">Personal trainer sessions</label>
        </div>
        <div class="feature-item">
          <input type="checkbox" id="feature3" name="features" value="spa" checked>
          <label for="feature3">Spa & Sauna access</label>
        </div>
        <div class="feature-item">
          <input type="checkbox" id="feature4" name="features" value="classes" checked>
          <label for="feature4">Group classes included</label>
        </div>
      </div>
      <div class="custom-feature">
        <input type="text" id="newFeature" value="Nutrition counseling" placeholder="Enter new feature">
        <button type="button" class="btn-add-feature">+ Add Feature</button>
      </div>
    </div>

    <div class="form-actions">
      <button type="submit" class="btn-submit">Update Plan</button>
      <button type="button" class="btn-cancel" onclick="window.history.back()">Cancel</button>
    </div>
  </form>
</div>
<script src="${pageContext.request.contextPath}/js/addMembershipPlan.js"></script>
</body>
</html>