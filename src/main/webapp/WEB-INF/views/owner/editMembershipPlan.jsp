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
            <h2>Edit Membership Plan</h2>
            <div id="errorMessage" class="error-message" style="display: none; color: red; margin-bottom: 1rem;"></div>

            <form id="membershipForm" action="${pageContext.request.contextPath}/membership/update" method="POST">
                <input type="hidden" name="planId" value="${membershipPlan.planId}">

                <div class="form-group">
                    <label for="planName">Plan Name</label>
                    <input
                            type="text"
                            id="planName"
                            name="planName"
                            value="${membershipPlan.planName}"
                            required
                            minlength="1"
                            placeholder="Enter plan name"
                    >
                </div>

                <div class="form-group">
                    <label>Duration</label>
                    <div style="display: flex; gap: 20px;">
                        <div style="flex: 1;">
                            <label>Start Time</label>
                            <input type="time" id="startTime" name="startTime" value="${membershipPlan.startTime}" required>
                        </div>
                        <div style="flex: 1;">
                            <label>End Time</label>
                            <input type="time" id="endTime" name="endTime" value="${membershipPlan.endTime}" required>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label>Duration Options</label>
                    <div id="durationsList">
                        <c:forEach var="duration" items="${membershipPlan.durations}" varStatus="status">
                            <div class="duration-option">
                                <div style="display: flex; gap: 10px; margin-bottom: 10px;">
                                    <input type="number"
                                           name="durationValue[]"
                                           class="duration-value"
                                           value="${duration.durationValue}"
                                           min="1"
                                           required
                                           placeholder="Duration">
                                    <select name="durationType[]"
                                            class="duration-type"
                                            required>
                                        <option value="days" ${duration.durationType == 'days' ? 'selected' : ''}>Days</option>
                                        <option value="months" ${duration.durationType == 'months' ? 'selected' : ''}>Months</option>
                                        <option value="years" ${duration.durationType == 'years' ? 'selected' : ''}>Years</option>
                                    </select>
                                    <c:if test="${!status.first}">
                                        <button type="button"
                                                onclick="removeDuration(this)"
                                                class="btn-danger">Remove</button>
                                    </c:if>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <button type="button" onclick="addDuration()" class="btn-secondary">Add Duration Option</button>
                </div>

                <div class="pricing-options">
                    <label>Pricing Type</label>
                    <div class="radio-group">
                        <label>
                            <input type="radio"
                                   id="uniformPricing"
                                   name="pricingType"
                                   value="uniform"
                            ${membershipPlan.pricingType == 'uniform' ? 'checked' : ''}
                                   onclick="togglePricingContainer()">
                            Uniform Pricing
                        </label>
                        <label>
                            <input type="radio"
                                   id="categoryPricing"
                                   name="pricingType"
                                   value="category"
                            ${membershipPlan.pricingType == 'category' ? 'checked' : ''}
                                   onclick="togglePricingContainer()">
                            Category Pricing
                        </label>
                    </div>

                    <!-- Uniform Pricing Container -->
                    <div id="uniformPricingContainer" class="pricing-container"
                         style="display: ${membershipPlan.pricingType == 'uniform' ? 'block' : 'none'}">
                        <div id="uniformPriceContainer">
                            <c:forEach var="duration" items="${membershipPlan.durations}" varStatus="status">
                                <c:if test="${not empty duration.uniformPricing}">
                                    <div class="price-section" data-duration-id="${duration.durationId}">
                                        <label>Price for ${duration.durationValue} ${duration.durationType}</label>
                                        <input type="number"
                                               name="uniformPrice"
                                               step="0.01"
                                               min="0"
                                               value="${duration.uniformPricing[0].price}"
                                               required
                                               placeholder="Enter price">
                                    </div>
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>

                    <!-- Category Pricing Container -->
                    <div id="categoryPricingContainer" class="pricing-container"
                         style="display: ${membershipPlan.pricingType == 'category' ? 'block' : 'none'}">
                        <div id="categoryPriceContainer">
                            <c:forEach var="duration" items="${membershipPlan.durations}" varStatus="durationStatus">
                                <div class="category-price-section" data-duration-id="${duration.durationId}">
                                    <h4>Pricing for ${duration.durationValue} ${duration.durationType}</h4>
                                    <div class="category-prices">
                                        <c:forEach var="categoryPrice" items="${duration.categoryPricing}">
                                            <div class="price-input">
                                                <input type="hidden" name="category" value="${categoryPrice.category}">
                                                <label>${categoryPrice.category} Price</label>
                                                <input type="number"
                                                       name="categoryPrice"
                                                       step="0.01"
                                                       min="0"
                                                       value="${categoryPrice.price}"
                                                       required
                                                       placeholder="Enter ${categoryPrice.category} price">
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn-primary">Update Plan</button>
                    <button type="button" onclick="window.history.back()" class="btn-secondary">Cancel</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    function addDuration() {
        const durationsList = document.getElementById('durationsList');
        const newDuration = document.createElement('div');
        newDuration.className = 'duration-option';
        newDuration.innerHTML = `
        <div style="display: flex; gap: 10px; margin-bottom: 10px;">
            <input type="number"
                   name="durationValue[]"
                   class="duration-value"
                   placeholder="Duration"
                   required
                   min="1">
            <select name="durationType[]"
                    class="duration-type"
                    required>
                <option value="">Select Type</option>
                <option value="days">Days</option>
                <option value="months">Months</option>
                <option value="years">Years</option>
            </select>
            <button type="button"
                    onclick="removeDuration(this)"
                    class="btn-danger">Remove</button>
        </div>
    `;
        durationsList.appendChild(newDuration);
    }

    function removeDuration(button) {
        const durationsList = document.getElementById('durationsList');
        const durationOption = button.closest('.duration-option');

        // Check if this is not the last remaining duration option
        if (durationsList.children.length > 1) {
            durationOption.remove();
        }
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
</script>