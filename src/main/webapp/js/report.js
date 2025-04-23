document.addEventListener("DOMContentLoaded", function() {
    console.log("DOM loaded - debugging form validation");

    // Debug all fields
    function debugFormFields() {
        console.log("Checking form fields existence:");
        const fieldsToCheck = [
            'waist_circumference', 'body_weight', 'height', 'fat', 'bmr',
            'max_heart_rate', 'bpm_65', 'bpm_75', 'bpm_85', 'weight_1'
        ];

        fieldsToCheck.forEach(field => {
            const el = document.querySelector(`input[name="${field}"]`);
            console.log(`${field}: ${el ? "exists" : "MISSING"}`);
        });
    }

    // Run debug on load
    debugFormFields();

    // Add Row Button Logic - More explicit approach
    const addRowBtn = document.getElementById('addRowButton');
    if (addRowBtn) {
        addRowBtn.addEventListener('click', function () {
            // Get the table body
            const tableBody = document.querySelector('#trainingTable tbody');

            // Create a new row and cells
            const newRow = document.createElement('tr');

            // Date cell
            const dateCell = document.createElement('td');
            const dateInput = document.createElement('input');
            dateInput.type = 'date';
            dateInput.name = 'exercise_date[]';
            dateInput.required = true;
            dateCell.appendChild(dateInput);

            // Weight cell
            const weightCell = document.createElement('td');
            const weightInput = document.createElement('input');
            weightInput.type = 'number';
            weightInput.step = '0.1';
            weightInput.name = 'weight[]';
            weightInput.placeholder = 'Weight (kg)';
            weightInput.min = '0';
            weightInput.max = '250';
            weightInput.required = true;
            weightCell.appendChild(weightInput);

            // Add cells to row
            newRow.appendChild(dateCell);
            newRow.appendChild(weightCell);

            // Add row to table
            tableBody.appendChild(newRow);

            console.log('Row added, total rows: ' + tableBody.querySelectorAll('tr').length);

            // Log all row data for debugging
            logAllRowData();
        });
    }

    // Helper function to log all row data
    function logAllRowData() {
        const dates = document.querySelectorAll('input[name="exercise_date[]"]');
        const weights = document.querySelectorAll('input[name="weight[]"]');

        console.log('=== FORM STATE DEBUG ===');
        console.log('Number of date fields: ' + dates.length);
        console.log('Number of weight fields: ' + weights.length);

        dates.forEach((date, i) => {
            console.log(`Row ${i+1}: Date=${date.value}, Weight=${weights[i]?.value}`);
        });
        console.log('======================');
    }

    // Form Submission Logic with enhanced debugging
    const reportForm = document.getElementById('userReportForm');
    if (reportForm) {
        reportForm.addEventListener('submit', function(event) {
            event.preventDefault();

            // Debug form data before submission
            console.log('Form submission detected');
            logAllRowData();

            // Verify form elements are correctly named
            const formData = new FormData(reportForm);
            console.log('Form data entries:');
            for (let pair of formData.entries()) {
                console.log(pair[0] + ': ' + pair[1]);
            }

            if (!validateForm()) {
                console.log('Form validation failed');
                return;
            }

            alert('Form is being submitted. Redirecting to the list form.');
            setTimeout(() => {
                console.log('Submitting form now...');
                reportForm.submit();
            }, 1000);
        });
    }

    // Alert Message from Server (if any)
    const messageElement = document.getElementById('message');
    const message = messageElement ? messageElement.innerText.trim() : "";
    if (message !== "") {
        alert(message);
        window.location.href = "/first.jsp"; // Change this to your actual redirect page
    }
});

// Validation Logic
function validateForm() {
    let isValid = true;

    // Validate age
    const age = document.querySelector('input[name="age"]');
    if (age && age.value && (parseInt(age.value) < 0 || parseInt(age.value) > 70)) {
        alert('Age must be between 0 and 70.');
        isValid = false;
    }

    // Only validate fields that actually exist in the form
    // These checks match what's in your HTML
    try {
        // Check negative values for measurements
        const measurementFields = [
            'waist_circumference', 'body_weight', 'height', 'fat', 'bmr'
        ];

        for (const field of measurementFields) {
            const input = document.querySelector(`input[name="${field}"]`);
            if (input && input.value !== "") {
                const value = parseFloat(input.value);
                if (value < 0) {
                    alert(`${field.replace(/_/g, ' ')} cannot be negative.`);
                    isValid = false;
                }
                if (field == 'waist_circumference' && value > 200){
                    alert('waist circumference must not exceed 200.');
                    isValid = false;
                }
            }
        }

        // Check weight fields separately - Modified to check weight[] as well
        const weightArrayFields = document.querySelectorAll('input[name="weight[]"]');
        for (const inputField of weightArrayFields) {
            if (inputField && inputField.value !== "") {
                const value = parseFloat(inputField.value);
                if (value < 0 || value > 250) {
                    alert('Weight must be between 0 and 250 kg.');
                    isValid = false;
                }
            }
        }

        // Also check the original weight fields with underscores
        const weightFields = document.querySelectorAll('input[name^="weight_"]');
        for (const inputField of weightFields) {
            if (inputField && inputField.value !== "") {
                const value = parseFloat(inputField.value);
                if (value < 0 || value > 250) {
                    alert('Weight must be between 0 and 250 kg.');
                    isValid = false;
                }
            }
        }

        // Check heart rate fields separately
        const heartRateFields = ['max_heart_rate', 'bpm_65', 'bpm_75', 'bpm_85'];
        for (const field of heartRateFields) {
            const input = document.querySelector(`input[name="${field}"]`);
            if (input && input.value !== "") {
                const value = parseFloat(input.value);
                if (value < 30 || value > 220) {
                    alert(`${field.replace(/_/g, ' ')} must be between 30 and 220 BPM.`);
                    isValid = false;
                }
            }
        }

        // Check height specifically
        const height = document.querySelector('input[name="height"]');
        if (height && height.value !== "") {
            const value = parseFloat(height.value);
            if (value < 30 || value > 300) {
                alert('Height must be between 30 cm and 300 cm.');
                isValid = false;
            }
        }
    } catch (error) {
        console.error("Validation error:", error);
        // Don't prevent form submission on validation error
        return true;
    }

    return isValid;
}