<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add New Membership Plan</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/addMembershipPlan.css">
</head>
<body>
<div class="container">
    <h1>Add New Membership Plan</h1>
    <form id="membershipForm" action="addPlan" method="POST">
        <div class="form-group">
            <label for="planName">Plan Name</label>
            <input type="text" id="planName" name="planName" required>
        </div>

        <div class="form-group">
            <label for="timeRange">Time Range</label>
            <input type="text" id="timeRange" name="timeRange" placeholder="e.g., 4:00 am to 12:00 Midnight" required>
        </div>

        <div class="form-group duration-container">
            <label>Duration Options</label>
            <div class="duration-options">
                <button type="button" class="btn-add-duration">+ Add Duration Option</button>
                <div class="duration-item">
                    <input type="number" name="durationValue[]" placeholder="Duration" min="1" required>
                    <select name="durationType[]" required>
                        <option value="days">Days</option>
                        <option value="months">Months</option>
                        <option value="years">Years</option>
                    </select>
                    <input type="number" name="durationPrice[]" placeholder="Price (Rs.)" required>
                    <button type="button" class="btn-remove-duration">×</button>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label>Pricing Structure</label>
            <div class="pricing-type">
                <input type="radio" id="uniformPricing" name="pricingType" value="uniform" checked>
                <label for="uniformPricing">Uniform Pricing</label>

                <input type="radio" id="categoryPricing" name="pricingType" value="category">
                <label for="categoryPricing">Category-based Pricing</label>
            </div>
        </div>

        <div class="pricing-container" id="uniformPricingContainer">
            <div class="price-group">
                <label for="uniformPrice">Price (Rs.)</label>
                <input type="number" id="uniformPrice" name="uniformPrice" placeholder="Enter price">
            </div>
        </div>

        <div class="pricing-container" id="categoryPricingContainer" style="display: none;">
            <div class="price-group">
                <label for="gentsPrice">Gents Price (Rs.)</label>
                <input type="number" id="gentsPrice" name="gentsPrice" placeholder="Enter price for gents">
            </div>

            <div class="price-group">
                <label for="ladiesPrice">Ladies Price (Rs.)</label>
                <input type="number" id="ladiesPrice" name="ladiesPrice" placeholder="Enter price for ladies">
            </div>

            <div class="price-group">
                <label for="couplePrice">Couple Price (Rs.)</label>
                <input type="number" id="couplePrice" name="couplePrice" placeholder="Enter price for couples">
            </div>
        </div>

        <div class="features-container">
            <label>Features</label>
            <div class="features-list">
                <div class="feature-item">
                    <input type="checkbox" id="feature1" name="features" value="premium">
                    <label for="feature1">Access to all premium equipment</label>
                </div>
                <div class="feature-item">
                    <input type="checkbox" id="feature2" name="features" value="trainer">
                    <label for="feature2">Personal trainer sessions</label>
                </div>
                <div class="feature-item">
                    <input type="checkbox" id="feature3" name="features" value="spa">
                    <label for="feature3">Spa & Sauna access</label>
                </div>
                <div class="feature-item">
                    <input type="checkbox" id="feature4" name="features" value="classes">
                    <label for="feature4">Group classes included</label>
                </div>
            </div>
            <div class="custom-feature">
                <input type="text" id="newFeature" placeholder="Enter new feature">
                <button type="button" class="btn-add-feature">+ Add Feature</button>
            </div>
        </div>

        <div class="form-actions">
            <button type="submit" class="btn-submit">Add Plan</button>
            <button type="button" class="btn-cancel" onclick="window.history.back()">Cancel</button>
        </div>
    </form>
</div>
<script src="${pageContext.request.contextPath}/js/addMembershipPlan.js"></script>
</body>
</html>