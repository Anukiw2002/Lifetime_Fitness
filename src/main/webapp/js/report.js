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

    // Optionally show an alert here before submitting
    alert('Form is being submitted. Redirecting to the list form.');

    // Now submit the form programmatically
    this.submit(); // Proceed with the form submission after the alert
});

document.addEventListener("DOMContentLoaded", function() {
    // Check if a message is available from the server
    const message = document.getElementById('message').innerText;

    // If a message is present, show an alert
    if (message && message.trim() !== "") {
        alert(message);
    }
});
