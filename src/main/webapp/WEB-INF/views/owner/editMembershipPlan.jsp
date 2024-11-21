<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Membership Plan</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/addMembershipPlan.css">
</head>
<body>
<jsp:include page="../common/verticalNavBar.jsp" />
<div class="main-content">
    <div class="container">
        <div class="content-card">
            <h1>Edit Membership Plan</h1>
            <form id="membershipForm" action="${pageContext.request.contextPath}/membership/update" method="POST">
                <input type="hidden" name="planId" value="${membershipPlan.planId}">

                <div class="form-group">
                    <label for="planName">Plan Name</label>
                    <input type="text" id="planName" name="planName" value="${membershipPlan.planName}" required>
                </div>

                <div class="form-group">
                    <label for="startTime">Start Time</label>
                    <input type="time" id="startTime" name="startTime" value="${membershipPlan.startTime}" required>
                </div>

                <div class="form-group">
                    <label for="endTime">End Time</label>
                    <input type="time" id="endTime" name="endTime" value="${membershipPlan.endTime}" required>
                </div>

                <div class="form-group duration-container">
                    <label>Duration Options</label>
                    <div class="duration-options">
                        <div id="durationsList">
                            <c:forEach var="duration" items="${membershipPlan.durations}" varStatus="status">
                                <div class="duration-item">
                                    <input type="number" name="durationValue[]"
                                           value="${duration.durationValue}" min="1" required>
                                    <select name="durationType[]" required>
                                        <option value="days" ${duration.durationType == 'days' ? 'selected' : ''}>Days</option>
                                        <option value="months" ${duration.durationType == 'months' ? 'selected' : ''}>Months</option>
                                        <option value="years" ${duration.durationType == 'years' ? 'selected' : ''}>Years</option>
                                    </select>
                                    <button type="button" class="btn-remove-duration" onclick="removeDuration(this)">×</button>
                                </div>
                            </c:forEach>
                        </div>
                        <button type="button" class="btn-add-duration" onclick="addDuration()">+ Add Duration Option</button>
                    </div>
                </div>

                <div class="form-group">
                    <label>Pricing Structure</label>
                    <div class="pricing-type">
                        <input type="radio" id="uniformPricing" name="pricingType" value="uniform"
                        ${membershipPlan.pricingType == 'uniform' ? 'checked' : ''}
                               onclick="togglePricingContainer()">
                        <label for="uniformPricing">Uniform Pricing</label>

                        <input type="radio" id="categoryPricing" name="pricingType" value="category"
                        ${membershipPlan.pricingType == 'category' ? 'checked' : ''}
                               onclick="togglePricingContainer()">
                        <label for="categoryPricing">Category-based Pricing</label>
                    </div>
                </div>

                <!-- Uniform Pricing Container -->
                <div class="pricing-container" id="uniformPricingContainer"
                     style="display: ${membershipPlan.pricingType == 'uniform' ? 'block' : 'none'}">
                    <c:forEach var="duration" items="${membershipPlan.durations}" varStatus="status">
                        <c:if test="${not empty duration.uniformPricing}">
                            <div class="price-group">
                                <label for="uniformPrice_${duration.durationId}">Price for ${duration.durationValue} ${duration.durationType} (Rs.)</label>
                                <input type="number"
                                       id="uniformPrice_${duration.durationId}"
                                       name="uniformPrice"
                                       value="${duration.uniformPricing[0].price}"
                                       required>
                            </div>
                        </c:if>
                    </c:forEach>
                </div>

                <!-- Category Pricing Container -->
                <div class="pricing-container" id="categoryPricingContainer"
                     style="display: ${membershipPlan.pricingType == 'category' ? 'block' : 'none'}">
                    <c:forEach var="duration" items="${membershipPlan.durations}" varStatus="durationStatus">
                        <div class="duration-pricing-section">
                            <h4>Pricing for ${duration.durationValue} ${duration.durationType}</h4>
                            <c:forEach var="categoryPrice" items="${duration.categoryPricing}">
                                <div class="price-group">
                                    <input type="hidden" name="category" value="${categoryPrice.category}">
                                    <label for="categoryPrice_${duration.durationId}_${categoryPrice.category}">
                                            ${categoryPrice.category} Price (Rs.)
                                    </label>
                                    <input type="number"
                                           id="categoryPrice_${duration.durationId}_${categoryPrice.category}"
                                           name="categoryPrice"
                                           value="${categoryPrice.price}"
                                           required>
                                </div>
                            </c:forEach>
                        </div>
                    </c:forEach>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn-submit">Update Plan</button>
                    <button type="button" class="btn-cancel" onclick="window.history.back()">Cancel</button>
                </div>
            </form>
        </div>

        <script>
            function addDuration() {
                const durationsList = document.getElementById('durationsList');
                const newDuration = document.createElement('div');
                newDuration.className = 'duration-item';
                newDuration.innerHTML = `
        <input type="number" name="durationValue[]" min="1" required>
        <select name="durationType[]" required>
            <option value="days">Days</option>
            <option value="months">Months</option>
            <option value="years">Years</option>
        </select>
        <button type="button" class="btn-remove-duration" onclick="removeDuration(this)">×</button>
    `;
                durationsList.appendChild(newDuration);
            }

            function removeDuration(button) {
                button.parentElement.remove();
            }

            function togglePricingContainer() {
                const uniformContainer = document.getElementById('uniformPricingContainer');
                const categoryContainer = document.getElementById('categoryPricingContainer');
                const uniformRadio = document.getElementById('uniformPricing');

                if (uniformRadio.checked) {
                    uniformContainer.style.display = 'block';
                    categoryContainer.style.display = 'none';
                } else {
                    uniformContainer.style.display = 'none';
                    categoryContainer.style.display = 'block';
                }
            }

            // Initialize pricing container visibility
            document.addEventListener('DOMContentLoaded', function() {
                togglePricingContainer();
            });

            function togglePricingContainer() {
                const uniformContainer = document.getElementById('uniformPricingContainer');
                const categoryContainer = document.getElementById('categoryPricingContainer');
                const uniformRadio = document.getElementById('uniformPricing');

                if (uniformRadio.checked) {
                    uniformContainer.style.display = 'block';
                    categoryContainer.style.display = 'none';
                } else {
                    uniformContainer.style.display = 'none';
                    categoryContainer.style.display = 'block';
                }
            }

            // Initialize pricing container visibility on page load
            document.addEventListener('DOMContentLoaded', function() {
                togglePricingContainer();
            });
        </script>
    </div>
</div>
</body>
</html>