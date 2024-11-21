<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gym Plan Management</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/addMembershipPlan.css">
</head>
<body>
<jsp:include page="../common/verticalNavBar.jsp" />
<div class="main-content">
    <div class="container">
        <div class="content-card">
            <h2>Add Membership Plan</h2>
            <div id="errorMessage" class="error-message" style="display: none; color: red; margin-bottom: 1rem;"></div>
            <form id="planForm">
                <div class="form-group">
                    <label for="planName">Plan Name</label>
                    <input
                            type="text"
                            id="planName"
                            name="planName"
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
                            <input type="time" id="startTime" name="startTime" value="04:00" required>
                        </div>
                        <div style="flex: 1;">
                            <label>End Time</label>
                            <input type="time" id="endTime" name="endTime" value="12:00" required>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label>Duration Options</label>
                    <div id="durationOptions"></div>
                    <button type="button" onclick="addDuration()" class="btn-secondary">Add Duration Option</button>
                </div>

                <div class="pricing-options">
                    <label>Pricing Type</label>
                    <div class="radio-group">
                        <label>
                            <input type="radio" name="pricingType" value="uniform" checked onclick="togglePricing('uniform')">
                            Uniform Pricing
                        </label>
                        <label>
                            <input type="radio" name="pricingType" value="category" onclick="togglePricing('category')">
                            Category Pricing
                        </label>
                    </div>

                    <div id="uniformPricing" class="pricing-container">
                        <div id="uniformPriceContainer"></div>
                    </div>

                    <div id="categoryPricing" class="pricing-container" style="display:none;">
                        <div id="categoryPriceContainer"></div>
                    </div>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn-primary">Add Plan</button>
                    <button type="reset" class="btn-secondary">Reset</button>
                    <button type="button" onclick="cancelForm()" class="btn-secondary">Cancel</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    let durationCounter = 0;

    function addDuration() {
        const container = document.getElementById('durationOptions');
        const durationDiv = document.createElement('div');
        durationDiv.className = 'duration-option';
        durationDiv.dataset.durationId = durationCounter;

        durationDiv.innerHTML = `
        <div style="display: flex; gap: 10px; margin-bottom: 10px;">
            <input type="number"
                   name="durationValue"
                   class="duration-value"
                   placeholder="Duration"
                   required
                   min="1"
                   onchange="updatePricing(${durationCounter})">
            <select name="durationType"
                    class="duration-type"
                    required
                    onchange="updatePricing(${durationCounter})">
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

        container.appendChild(durationDiv);
        updateAllPricingContainers();
        durationCounter++;
    }

    function removeDuration(button) {
        const durationOption = button.closest('.duration-option');
        durationOption.remove();
        updateAllPricingContainers();
    }

    function togglePricing(type) {
        const uniformPricing = document.getElementById('uniformPricing');
        const categoryPricing = document.getElementById('categoryPricing');

        if (type === 'uniform') {
            uniformPricing.style.display = 'block';
            categoryPricing.style.display = 'none';
        } else {
            uniformPricing.style.display = 'none';
            categoryPricing.style.display = 'block';
        }
        updateAllPricingContainers();
    }

    function updateAllPricingContainers() {
        const pricingType = document.querySelector('input[name="pricingType"]:checked').value;
        const durations = Array.from(document.querySelectorAll('.duration-option')).map(option => ({
            id: option.dataset.durationId,
            value: option.querySelector('[name="durationValue"]').value,
            type: option.querySelector('[name="durationType"]').value
        })).filter(duration => duration.value && duration.type);

        updatePricingContainers(durations, pricingType);
    }

    function updatePricingContainers(durations, pricingType) {
        if (pricingType === 'uniform') {
            updateUniformPricing(durations);
        } else {
            updateCategoryPricing(durations);
        }
    }

    function updateUniformPricing(durations) {
        const container = document.getElementById('uniformPriceContainer');
        container.innerHTML = durations.map(duration => `
        <div class="price-section" data-duration-id="${duration.id}">
            <label>Price for ${duration.value} ${duration.type}</label>
            <input type="number"
                   name="uniformPrice_${duration.id}"
                   step="0.01"
                   min="0"
                   required
                   placeholder="Enter price">
        </div>
    `).join('');
    }

    function updateCategoryPricing(durations) {
        const container = document.getElementById('categoryPriceContainer');
        container.innerHTML = durations.map(duration => `
        <div class="category-price-section" data-duration-id="${duration.id}">
            <h4>Pricing for ${duration.value} ${duration.type}</h4>
            <div class="category-prices">
                <div class="price-input">
                    <label>Gents Price</label>
                    <input type="number"
                           name="categoryPrice_${duration.id}_Gents"
                           step="0.01"
                           min="0"
                           required
                           placeholder="Enter gents price">
                </div>
                <div class="price-input">
                    <label>Ladies Price</label>
                    <input type="number"
                           name="categoryPrice_${duration.id}_Ladies"
                           step="0.01"
                           min="0"
                           required
                           placeholder="Enter ladies price">
                </div>
                <div class="price-input">
                    <label>Couple Price</label>
                    <input type="number"
                           name="categoryPrice_${duration.id}_Couple"
                           step="0.01"
                           min="0"
                           required
                           placeholder="Enter couple price">
                </div>
            </div>
        </div>
    `).join('');
    }

    function showError(message) {
        const errorDiv = document.getElementById('errorMessage');
        errorDiv.textContent = message;
        errorDiv.style.display = 'block';
        setTimeout(() => {
            errorDiv.style.display = 'none';
        }, 5000);
    }

    function validateForm() {
        const planName = document.getElementById('planName').value.trim();
        if (!planName) {
            showError('Please enter a plan name');
            return false;
        }

        const durations = document.querySelectorAll('.duration-option');
        if (durations.length === 0) {
            showError('Please add at least one duration option');
            return false;
        }

        return true;
    }

    function cancelForm() {
        if (confirm('Are you sure you want to cancel? All entered data will be lost.')) {
            window.location.href = '${pageContext.request.contextPath}/membership/list';
        }
    }

    document.getElementById('planForm').addEventListener('submit', async function(e) {
        e.preventDefault();

        if (!validateForm()) {
            return;
        }

        const formData = new FormData(this);

        // Log all form data entries
        for (const [key, value] of formData.entries()) {
            console.log(`Form Data - ${key}: ${value}`);
        }

        try {
            const response = await fetch('${pageContext.request.contextPath}/membership/add', {
                method: 'POST',
                body: formData,
                headers: {
                    // Remove Content-Type to let browser set multipart/form-data
                    // 'Content-Type': 'multipart/form-data'  // DO NOT set this manually
                }
            });

            const data = await response.json();
            console.log("Response data:", data);

            if (data.success) {
                window.location.href = data.redirectUrl;
            } else {
                showError(data.message || 'Error creating membership plan');
            }
        } catch (error) {
            console.error('Error submitting form:', error);
            showError('Error submitting form: ' + error.message);
        }
    });

    // Initialize the form with one duration option
    document.addEventListener('DOMContentLoaded', function() {
        addDuration();
    });
</script>

<style>
    .error-message {
        background-color: #ffebee;
        border: 1px solid #ffcdd2;
        padding: 10px;
        border-radius: 4px;
        margin-bottom: 1rem;
    }

    .duration-option {
        margin-bottom: 1rem;
        padding: 1rem;
        border: 1px solid #ddd;
        border-radius: 4px;
    }

    .category-prices {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
        gap: 1rem;
        margin-top: 0.5rem;
    }

    .price-input {
        display: flex;
        flex-direction: column;
        gap: 0.5rem;
    }

    .btn-primary {
        background-color: #4CAF50;
        color: white;
        padding: 10px 20px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
    }

    .btn-secondary {
        background-color: #9e9e9e;
        color: white;
        padding: 10px 20px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
    }

    .btn-danger {
        background-color: #f44336;
        color: white;
        padding: 10px 20px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
    }

    .pricing-container {
        margin-top: 1rem;
    }

    .category-price-section {
        margin-bottom: 1.5rem;
        padding: 1rem;
        border: 1px solid #ddd;
        border-radius: 4px;
    }
</style>