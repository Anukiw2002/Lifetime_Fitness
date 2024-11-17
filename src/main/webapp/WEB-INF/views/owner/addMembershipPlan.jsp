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
            <form id="planForm" onsubmit="return validateForm()">
                <input type="hidden" name="action" value="add">
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
                    <div id="durationOptions">
                        <!-- Duration options will be added here -->
                    </div>
                    <button type="button" onclick="addDuration()">Add Duration Option</button>
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

                    <div id="uniformPricing" class="form-group">
                        <label>Uniform Price</label>
                        <div id="uniformPriceContainer">
                            <!-- Uniform prices will be added here based on durations -->
                        </div>
                    </div>

                    <div id="categoryPricing" class="form-group" style="display:none;">
                        <div id="categoryPriceContainer">
                            <!-- Category prices will be added here based on durations -->
                        </div>
                    </div>
                </div>

                <div class="form-actions">
                    <button type="submit">Add Plan</button>
                    <button type="reset">Reset</button>
                    <button type="button" onclick="cancelForm()">Cancel</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    function addDuration() {
        const container = document.getElementById('durationOptions');
        const durationId = Date.now(); // Unique ID for this duration option
        const newDuration = document.createElement('div');
        newDuration.className = 'duration-option';
        newDuration.dataset.durationId = durationId;
        newDuration.innerHTML = `
            <input type="number" class="duration-value" placeholder="Duration" required min="1"
                   onchange="updatePricing(${durationId})">
            <select class="duration-type" required onchange="updatePricing(${durationId})">
                <option value="">Select Type</option>
                <option value="days">Days</option>
                <option value="months">Months</option>
                <option value="years">Years</option>
            </select>
            <button type="button" onclick="removeDuration(this, ${durationId})">Remove</button>
        `;
        container.appendChild(newDuration);
        updateAllPricingContainers();
    }

    function removeDuration(button, durationId) {
        button.closest('.duration-option').remove();
        // Remove corresponding pricing sections
        document.querySelectorAll(`.price-section[data-duration-id="${durationId}"]`).forEach(el => el.remove());
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
            value: option.querySelector('.duration-value').value,
            type: option.querySelector('.duration-type').value
        })).filter(duration => duration.value && duration.type);

        // Update uniform pricing container
        const uniformContainer = document.getElementById('uniformPriceContainer');
        uniformContainer.innerHTML = durations.map(duration => `
            <div class="price-section" data-duration-id="${duration.id}">
                <label>Price for ${duration.value} ${duration.type}</label>
                <input type="number" name="uniformPrice_${duration.id}"
                       placeholder="Enter price" min="0" step="0.01" required>
            </div>
        `).join('');

        // Update category pricing container
        const categoryContainer = document.getElementById('categoryPriceContainer');
        categoryContainer.innerHTML = durations.map(duration => `
            <div class="category-price-container price-section" data-duration-id="${duration.id}">
                <div class="category-price-header">Pricing for ${duration.value} ${duration.type}</div>
                <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 20px;">
                    <div class="price-field">
                        <label>Gents Price</label>
                        <input type="number" name="gentsPrice_${duration.id}"
                               placeholder="Enter gents price" min="0" step="0.01"
                               <input type="number" name="gentsPrice_${duration.id}" placeholder="Enter gents price" min="0" step="0.01" required>
                    </div>
                    <div class="price-field">
                        <label>Ladies Price</label>
                        <input type="number" name="ladiesPrice_${duration.id}"
                               placeholder="Enter ladies price" min="0" step="0.01"
                               <input type="number" name="ladiesPrice_${duration.id}" placeholder="Enter ladies price" min="0" step="0.01" required>
                    </div>
                    <div class="price-field">
                        <label>Couple Price</label>
                        <input type="number" name="couplePrice_${duration.id}"
                               placeholder="Enter couple price" min="0" step="0.01"
                               <input type="number" name="couplePrice_${duration.id}" placeholder="Enter couple price" min="0" step="0.01" required>
                    </div>
                </div>
            </div>
        `).join('');
    }

    function validateForm() {
        const planName = document.getElementById('planName').value.trim();
        if (!planName) {
            alert('Please enter a plan name');
            return false;
        }

        const durations = document.querySelectorAll('.duration-option');
        if (durations.length === 0) {
            alert('Please add at least one duration option');
            return false;
        }

        // Validate that at least one duration is properly filled
        let hasValidDuration = false;
        durations.forEach(duration => {
            const value = duration.querySelector('.duration-value').value;
            const type = duration.querySelector('.duration-type').value;
            if (value && type) hasValidDuration = true;
        });

        if (!hasValidDuration) {
            alert('Please complete at least one duration option');
            return false;
        }

        return true;
    }

    // Initialize the form with one duration option
    document.addEventListener('DOMContentLoaded', function() {
        addDuration();
    });
    function cancelForm() {
        if (confirm('Are you sure you want to cancel? All entered data will be lost.')) {
            window.location.href = '/MembershipPlan'; // Replace with your desired URL
        }
    }
    document.getElementById('planForm').onsubmit = function(e) {
        e.preventDefault();

        if (!validateForm()) {
            return false;
        }

        // Create FormData object from the form
        const formData = new FormData(this);

        // Add action parameter
        formData.set('action', 'add');

        // Debug: Log all form data being sent
        console.log('Form data being sent:');
        for (let pair of formData.entries()) {
            console.log(pair[0] + ': ' + pair[1]);
        }

        // Send AJAX request
        fetch('MembershipPlan?action=add', {
            method: 'POST',
            body: formData
        })
            .then(response => {
                // First log the raw response
                console.log('Raw response:', response);
                return response.json();
            })
            .then(data => {
                console.log('Response data:', data); // Debug log
                if (data.success) {
                    alert(data.message);
                    window.location.href = 'MembershipPlan?action=view';
                } else {
                    alert('Error: ' + data.message);
                }
            })
            .catch(error => {
                console.error('Error:', error); // Better error logging
                alert('Error submitting form: ' + error);
            });

        return false;
    };
</script>
</body>
</html>