document.getElementById('addRowButton').addEventListener('click', function() {
    // Get the table body
    var tableBody = document.querySelector('#trainingTable tbody');
    var rowCount = tableBody.querySelectorAll('tr').length + 1; // Ensure unique index based on the number of rows

    // Create a new row
    var newRow = document.createElement('tr');

    // Create new cells with input fields using template literals
    newRow.innerHTML = `
        <td><input type="text" name="exercise_${rowCount}" placeholder="Enter Exercise"></td>
        <td><input type="number" name="reps_${rowCount}" placeholder="Reps"></td>
        <td><input type="number" name="sets_${rowCount}" placeholder="Sets"></td>
        <td><input type="date" name="date_${rowCount}"></td>
        <td><input type="text" name="rest_${rowCount}" placeholder="Rest"></td>
        <td><input type="number" name="weight_${rowCount}" placeholder="Weight (kg)"></td>
    `;

    // Append the new row to the table body
    tableBody.appendChild(newRow);
});

document.getElementById('userReportForm').addEventListener('submit', function(event) {
    // Prevent default form submission to show alert first
    event.preventDefault();

    // Validate form fields
    if (!validateForm()) {
        return; // Prevent form submission if validation fails
    }

    // Optionally show an alert here before submitting
    alert('Form is being submitted. Redirecting to the list form.');

    // Delay the form submission for a brief moment
    setTimeout(() => {
        this.submit(); // Proceed with the form submission after the alert
    }, 1000); // 1 second delay (adjust as needed)
});

function validateForm() {
    let isValid = true;

    // Get the age input and check its value
    const age = document.querySelector('input[name="age"]');
    if (age.value < 0 || age.value > 70) {
        alert('Age must be between 0 and 70.');
        isValid = false;
    }

    // Validate other numeric fields (e.g., waist circumference, body weight, fat percentage, etc.)
    const numberFields = [
        'waist_circumference',
        'body_weight',
        'height',
        'fat',
        'bmr',
        'max_heart_rate',
        'bpm_65',
        'bpm_75',
        'bpm_85',
        'reps_1',
        'sets_1',
        'weight_1'
    ];

    numberFields.forEach(function(field) {
        const fieldValue = document.querySelector(`input[name="${field}"]`).value;

        // Check for negative values
        if (fieldValue < 0) {
            alert(`${field.replace('_', ' ')} cannot be negative.`);
            isValid = false;
        }
    });

    // Validate max_heart_rate and BPM values (reasonable heart rate range 30-220)
    const maxHeartRate = document.querySelector('input[name="max_heart_rate"]');
    const bpm65 = document.querySelector('input[name="bpm_65"]');
    const bpm75 = document.querySelector('input[name="bpm_75"]');
    const bpm85 = document.querySelector('input[name="bpm_85"]');

    [maxHeartRate, bpm65, bpm75, bpm85].forEach(function(inputField) {
        const bpmValue = inputField.value;
        if (bpmValue < 30 || bpmValue > 220) {
            alert(`${inputField.name.replace('_', ' ')} must be between 30 and 220 BPM.`);
            isValid = false;
        }
    });

    // Validate height (reasonable range 30 cm - 300 cm, convert to feet for checks)
    const height = document.querySelector('input[name="height"]');
    const heightValue = height.value;
    if (heightValue < 30 || heightValue > 300) {
        alert('Height must be between 30 cm and 300 cm.');
        isValid = false;
    }

    return isValid;
}

document.addEventListener("DOMContentLoaded", function() {
    // Check if a message is available from the server
    const messageElement = document.getElementById('message');
    const message = messageElement ? messageElement.innerText : "";

    // If a message is present, show an alert
    if (message && message.trim() !== "") {
        alert(message);
        // Redirect after alert
        window.location.href = "/first.jsp"; // Change this URL to where you want to redirect
    }
});
