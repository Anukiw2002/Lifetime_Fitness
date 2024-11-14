console.log('MembershipPlan JS loaded successfully');

document.addEventListener('DOMContentLoaded', function() {
    console.log('DOM Content Loaded');

    const form = document.getElementById('membershipForm');
    if (!form) {
        console.error('Form not found!');
        return;
    }
    console.log('Form found successfully');

document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('membershipForm');
    const featuresContainer = document.querySelector('.features-list');
    const addFeatureBtn = document.querySelector('.btn-add-feature');
    const newFeatureInput = document.getElementById('newFeature');
    const addDurationBtn = document.querySelector('.btn-add-duration');
    const durationOptions = document.querySelector('.duration-options');

    // Toggle pricing containers based on pricing type selection
    document.querySelectorAll('input[name="pricingType"]').forEach(radio => {
        radio.addEventListener('change', function() {
            const uniformContainer = document.getElementById('uniformPricingContainer');
            const categoryContainer = document.getElementById('categoryPricingContainer');

            if (this.value === 'uniform') {
                uniformContainer.style.display = 'block';
                categoryContainer.style.display = 'none';
                // Reset category inputs
                document.querySelectorAll('#categoryPricingContainer input').forEach(input => input.value = '');
            } else {
                uniformContainer.style.display = 'none';
                categoryContainer.style.display = 'block';
                // Reset uniform input
                document.getElementById('uniformPrice').value = '';
            }
        });
    });

    // Add new feature
    addFeatureBtn.addEventListener('click', function() {
        const featureText = newFeatureInput.value.trim();
        if (featureText) {
            const featureId = 'feature_' + Date.now();
            const featureDiv = document.createElement('div');
            featureDiv.className = 'feature-item';
            featureDiv.innerHTML = `
                <input type="checkbox" id="${featureId}" name="features" value="${featureText}">
                <label for="${featureId}">${featureText}</label>
                <button type="button" class="btn-remove-duration" onclick="this.parentElement.remove()">×</button>
            `;
            featuresContainer.appendChild(featureDiv);
            newFeatureInput.value = '';
        }
    });

    // Add new duration option
    addDurationBtn.addEventListener('click', function() {
        const durationItem = document.createElement('div');
        durationItem.className = 'duration-item';
        durationItem.innerHTML = `
            <input type="number" name="durationValue[]" placeholder="Duration" min="1" required>
            <select name="durationType[]" required>
                <option value="days">Days</option>
                <option value="months">Months</option>
                <option value="years">Years</option>
            </select>
            <input type="number" name="durationPrice[]" placeholder="Price (Rs.)" required>
            <button type="button" class="btn-remove-duration" onclick="this.parentElement.remove()">×</button>
        `;
        addDurationBtn.insertAdjacentElement('afterend', durationItem);
    });

    // Form validation
    form.addEventListener('submit', function(e) {
        e.preventDefault();

        const planName = document.getElementById('planName').value;
        const timeRange = document.getElementById('timeRange').value;
        const durationItems = document.querySelectorAll('.duration-item');
        const pricingType = document.querySelector('input[name="pricingType"]:checked').value;

        if (!planName || !timeRange) {
            alert('Please fill in all required fields');
            return;
        }

        if (durationItems.length === 0) {
            alert('Please add at least one duration option');
            return;
        }

        if (pricingType === 'uniform') {
            if (!document.getElementById('uniformPrice').value) {
                alert('Please enter the price');
                return;
            }
        } else {
            const gentsPrice = document.getElementById('gentsPrice').value;
            const ladiesPrice = document.getElementById('ladiesPrice').value;
            if (!gentsPrice || !ladiesPrice) {
                alert('Please enter prices for all categories');
                return;
            }
        }

        // If validation passes, show success message
        alert('Plan added successfully!');
        // When backend is ready, uncomment:
        // this.submit();
    });
}); }