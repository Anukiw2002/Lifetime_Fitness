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
                <div class="form-group">
                    <label>Plan Name</label>
                    <input type="text" id="planName" name="planName" required placeholder="Enter plan name">
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
                    <label class="checkbox-label">
                        <input type="checkbox" id="isActive" name="isActive" checked>
                        Is Active
                    </label>
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
                        <input type="number" id="uniformPrice" name="uniformPrice" placeholder="Enter price">
                    </div>

                    <div id="categoryPricing" class="form-group" style="display:none;">
                        <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 20px;">
                            <div>
                                <label>Gents Price</label>
                                <input type="number" id="gentsPrice" name="gentsPrice" placeholder="Enter gents price">
                            </div>
                            <div>
                                <label>Ladies Price</label>
                                <input type="number" id="ladiesPrice" name="ladiesPrice" placeholder="Enter ladies price">
                            </div>
                            <div>
                                <label>Couple Price</label>
                                <input type="number" id="couplePrice" name="couplePrice" placeholder="Enter couple price">
                            </div>
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
    function validateForm() {
        const planName = document.getElementById('planName').value;
        const startTime = document.getElementById('startTime').value;
        const endTime = document.getElementById('endTime').value;

        if (!planName) {
            alert('Please enter a plan name');
            return false;
        }

        if (startTime >= endTime) {
            alert('End time must be after start time');
            return false;
        }

        return true;
    }

    function addDuration() {
        const container = document.getElementById('durationOptions');
        const newDuration = document.createElement('div');
        newDuration.className = 'duration-option';
        newDuration.innerHTML = `
                <input type="number" placeholder="Duration" required>
                <select required>
                    <option value="">Select Type</option>
                    <option value="days">Days</option>
                    <option value="months">Months</option>
                    <option value="years">Years</option>
                </select>
                <input type="number" placeholder="Price" required>
                <button type="button" onclick="removeDuration(this)">Remove</button>
            `;
        container.appendChild(newDuration);
    }

    function removeDuration(button) {
        button.parentElement.remove();
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
    }

    function addFeature() {
        const customFeature = document.getElementById('customFeature').value;
        if (!customFeature) return;

        const featuresContainer = document.getElementById('customFeatures');
        const featureDiv = document.createElement('div');
        featureDiv.innerHTML = `
                <input type="checkbox" name="features" value="${customFeature}" checked>
                <label>${customFeature}</label>
                <button type="button" onclick="this.parentElement.remove()">Remove</button>
            `;
        featuresContainer.appendChild(featureDiv);
        document.getElementById('customFeature').value = '';
    }

    function cancelForm() {
        if (confirm('Are you sure you want to cancel? All entered data will be lost.')) {
            window.location.href = 'index.jsp'; // Replace with your desired URL
        }
    }
</script>
</body>
</html>