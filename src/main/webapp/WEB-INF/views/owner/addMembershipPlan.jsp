<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gym Plan Management</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/addMembershipPlan.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
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
                    <label for="colour">Plan Color</label>
                    <input type="color" id="colour" name="colour" value="#000000" required>
                </div>

                <div class="form-group">
                    <label>Duration</label>
                    <div style="display: flex; gap: 20px;">
                        <div style="flex: 1;">
                            <label>Start Time</label>
                            <input type="time" id="startTime" name="startTime" required>
                        </div>
                        <div style="flex: 1;">
                            <label>End Time</label>
                            <input type="time" id="endTime" name="endTime" required>
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
                    <button type="submit" class="btn btn-primary">Add Plan</button>
                    <button type="reset" class="btn btn-secondary">Reset</button>
                    <button type="button" onclick="cancelForm()" class="btn btn-danger">Cancel</button>
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

        const isFirstDuration = container.children.length === 0;

        durationDiv.innerHTML =
            '<div style="display: flex; gap: 10px; margin-bottom: 10px;">' +
            '<input type="number" ' +
            'name="durationValue" ' +
            'class="duration-value" ' +
            'placeholder="Duration" ' +
            'required ' +
            'min="1" ' +
            'onchange="updatePricing()">' +
            '<select name="durationType" ' +
            'class="duration-type" ' +
            'required ' +
            'onchange="updatePricing()">' +
            '<option value="">Select Type</option>' +
            '<option value="days">Days</option>' +
            '<option value="months">Months</option>' +
            '<option value="years">Years</option>' +
            '</select>' +
            (isFirstDuration ? '' :
                    '<button type="button" ' +
                    'onclick="removeDuration(this)" ' +
                    'class="btn-danger">Remove</button>'
            ) +
            '</div>';

        container.appendChild(durationDiv);
        updatePricing();
        durationCounter++;
    }

    function removeDuration(button) {
        const durationOption = button.closest('.duration-option');
        const container = document.getElementById('durationOptions');
        const isFirstDuration = durationOption === container.firstElementChild;

        if (!isFirstDuration) {
            durationOption.remove();
            updatePricing();
        }
    }

    function togglePricing(type) {
        const uniformPricing = document.getElementById('uniformPricing');
        const categoryPricing = document.getElementById('categoryPricing');

        // Remove required attribute from all pricing inputs first
        document.querySelectorAll('[name^="uniformPrice"], [name^="categoryPrice"]').forEach(input => {
            input.removeAttribute('required');
        });

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
        const isUniform = document.querySelector('input[name="pricingType"]:checked').value === 'uniform';

        container.innerHTML = durations.map((duration, index) => `
        <div class="price-section">
            <label>Price for ${duration.value} ${duration.type}</label>
            <input type="number"
                   name="uniformPrice"
                   step="0.01"
                   min="0"
                   ${isUniform ? 'required' : ''}
                   placeholder="Enter price">
        </div>
    `).join('');
    }

    function updateCategoryPricing(durations) {
        const container = document.getElementById('categoryPriceContainer');
        const isCategory = document.querySelector('input[name="pricingType"]:checked').value === 'category';

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
                           placeholder="Enter gents price">
                </div>
                <div class="price-input">
                    <label>Ladies Price</label>
                    <input type="number"
                           name="categoryPriceLadies"
                           step="0.01"
                           min="0"
                           placeholder="Enter ladies price">
                </div>
                <div class="price-input">
                    <label>Couple Price</label>
                    <input type="number"
                           name="categoryPriceCouple"
                           step="0.01"
                           min="0"
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

        const startTime = document.getElementById('startTime').value;
        const endTime = document.getElementById('endTime').value;

        if (!startTime || !endTime) {
            showError('Please select both start and end times');
            return false;
        }

        if (startTime >= endTime) {
            showError('End time must be after start time');
            return false;
        }

        const durations = document.querySelectorAll('.duration-option');
        if (durations.length === 0) {
            showError('Please add at least one duration option');
            return false;
        }

        const pricingType = document.querySelector('input[name="pricingType"]:checked').value;

        if (pricingType === 'uniform') {
            // Validate uniform pricing
            const uniformPrices = document.querySelectorAll('#uniformPricing [name="uniformPrice"]:not([disabled])');
            let valid = true;
            uniformPrices.forEach(input => {
                if (!input.value || input.value <= 0) {
                    showError('Please enter a valid price for uniform pricing');
                    valid = false;
                }
            });
            return valid;
        } else {
            // Validate category pricing
            return validateCategoryPricing();
        }
    }

    function validateCategoryPricing() {
        const categoryPriceSections = document.querySelectorAll('.category-price-section');

        for (const section of categoryPriceSections) {
            const gentsPrice = section.querySelector('[name="categoryPriceGents"]').value;
            const ladiesPrice = section.querySelector('[name="categoryPriceLadies"]').value;
            const couplePrice = section.querySelector('[name="categoryPriceCouple"]').value;

            // Check if at least one price is entered for this duration
            if (!gentsPrice && !ladiesPrice && !couplePrice) {
                showError('Please enter at least one category price (Gents, Ladies, or Couple) for each duration');
                return false;
            }

            // Validate that entered prices are positive numbers
            if ((gentsPrice && gentsPrice <= 0) ||
                (ladiesPrice && ladiesPrice <= 0) ||
                (couplePrice && couplePrice <= 0)) {
                showError('All entered prices must be greater than 0');
                return false;
            }
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
        formData.append('colour', document.getElementById('colour').value);

        const pricingType = document.querySelector('input[name="pricingType"]:checked').value;
        formData.append('pricingType', pricingType);

        // Get all duration options
        const durations = document.querySelectorAll('.duration-option');
        durations.forEach((duration, index) => {
            formData.append('durationValue', duration.querySelector('[name="durationValue"]').value);
            formData.append('durationType', duration.querySelector('[name="durationType"]').value);

            // Add pricing based on type
            if (pricingType === 'uniform') {
                const uniformPrice = document.querySelectorAll('[name="uniformPrice"]')[index];
                if (uniformPrice && uniformPrice.value) {
                    formData.append('uniformPrice', uniformPrice.value);
                }
            } else {
                const gentsPrice = document.querySelectorAll('[name="categoryPriceGents"]')[index];
                const ladiesPrice = document.querySelectorAll('[name="categoryPriceLadies"]')[index];
                const couplePrice = document.querySelectorAll('[name="categoryPriceCouple"]')[index];

                if (gentsPrice && gentsPrice.value) formData.append('categoryPriceGents', gentsPrice.value);
                if (ladiesPrice && ladiesPrice.value) formData.append('categoryPriceLadies', ladiesPrice.value);
                if (couplePrice && couplePrice.value) formData.append('categoryPriceCouple', couplePrice.value);
            }
        });

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

    function validateTime() {
        const startTime = document.getElementById('startTime').value;
        const endTime = document.getElementById('endTime').value;

        if (startTime && endTime) {
            if (startTime >= endTime) {
                showError('End time must be after start time');
                document.getElementById('endTime').setCustomValidity('End time must be after start time');
                return false;
            } else {
                document.getElementById('endTime').setCustomValidity('');
            }
        }
        return true;
    }

    // Initialize the form with one duration option
    document.addEventListener('DOMContentLoaded', function() {
        addDuration();
        document.getElementById('startTime').addEventListener('change', validateTime);
        document.getElementById('endTime').addEventListener('change', validateTime);
    });
</script>
</body>
</html>