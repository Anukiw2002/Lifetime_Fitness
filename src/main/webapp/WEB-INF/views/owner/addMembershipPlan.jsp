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
                    <input type="text" id="planName" name="planName" required minlength="1" placeholder="Enter plan name">
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
                   onchange="updatePricing()">
            <select name="durationType"
                    class="duration-type"
                    required
                    onchange="updatePricing()">
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
        updatePricing();
        durationCounter++;
    }

    function removeDuration(button) {
        const durationOption = button.closest('.duration-option');
        durationOption.remove();
        updatePricing();
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
        updatePricing();
    }

    function getDurationData() {
        return Array.from(document.querySelectorAll('.duration-option')).map(option => ({
            value: option.querySelector('[name="durationValue"]').value,
            type: option.querySelector('[name="durationType"]').value
        })).filter(duration => duration.value && duration.type);
    }

    function updateUniformPricing(durations) {
        const container = document.getElementById('uniformPriceContainer');
        container.innerHTML = durations.map((duration, index) => `
        <div class="price-section">
            <label>Price for ${duration.value} ${duration.type}</label>
            <input type="number"
                   name="uniformPrice"
                   step="0.01"
                   min="0"
                   required
                   placeholder="Enter price">
        </div>
    `).join('');
    }

    function updateCategoryPricing(durations) {
        const container = document.getElementById('categoryPriceContainer');
        container.innerHTML = durations.map((duration, index) => `
        <div class="category-price-section">
            <h4>Pricing for ${duration.value} ${duration.type}</h4>
            <div class="category-prices">
                <div class="price-input">
                    <label>Gents Price</label>
                    <input type="number"
                           name="categoryPriceGents"
                           step="0.01"
                           min="0"
                           required
                           placeholder="Enter gents price">
                </div>
                <div class="price-input">
                    <label>Ladies Price</label>
                    <input type="number"
                           name="categoryPriceLadies"
                           step="0.01"
                           min="0"
                           required
                           placeholder="Enter ladies price">
                </div>
                <div class="price-input">
                    <label>Couple Price</label>
                    <input type="number"
                           name="categoryPriceCouple"
                           step="0.01"
                           min="0"
                           required
                           placeholder="Enter couple price">
                </div>
            </div>
        </div>
    `).join('');
    }

    function updatePricing() {
        const pricingType = document.querySelector('input[name="pricingType"]:checked').value;
        const durations = getDurationData();

        if (pricingType === 'uniform') {
            updateUniformPricing(durations);
        } else {
            updateCategoryPricing(durations);
        }
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
            window.location.href = '${pageContext.request.contextPath}/membership/view';
        }
    }

    document.getElementById('planForm').addEventListener('submit', async function(e) {
        e.preventDefault();

        if (!validateForm()) {
            return;
        }

        const formData = new FormData();

        // Add basic plan details
        formData.append('planName', document.getElementById('planName').value);
        formData.append('startTime', document.getElementById('startTime').value);
        formData.append('endTime', document.getElementById('endTime').value);
        formData.append('pricingType', document.querySelector('input[name="pricingType"]:checked').value);

        // Get all duration options
        document.querySelectorAll('.duration-option').forEach((duration) => {
            formData.append('durationValue', duration.querySelector('[name="durationValue"]').value);
            formData.append('durationType', duration.querySelector('[name="durationType"]').value);
        });

        // Add pricing based on type
        const pricingType = document.querySelector('input[name="pricingType"]:checked').value;
        if (pricingType === 'uniform') {
            const uniformPrices = document.querySelectorAll('[name="uniformPrice"]');
            uniformPrices.forEach(input => {
                if (input.value) {
                    formData.append('uniformPrice', input.value);
                }
            });
        } else {
            const gentsInputs = document.querySelectorAll('[name="categoryPriceGents"]');
            const ladiesInputs = document.querySelectorAll('[name="categoryPriceLadies"]');
            const coupleInputs = document.querySelectorAll('[name="categoryPriceCouple"]');

            gentsInputs.forEach(input => {
                if (input.value) formData.append('categoryPriceGents', input.value);
            });
            ladiesInputs.forEach(input => {
                if (input.value) formData.append('categoryPriceLadies', input.value);
            });
            coupleInputs.forEach(input => {
                if (input.value) formData.append('categoryPriceCouple', input.value);
            });
        }

        try {
            const response = await fetch('${pageContext.request.contextPath}/membership/add', {
                method: 'POST',
                body: formData
            });

            const data = await response.json();

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
</body>
</html>