document.getElementById('addRowButton').addEventListener('click', function() {
    // Get the table body
    var tableBody = document.querySelector('#trainingTable tbody');
    var rowCount = tableBody.rows.length + 1; // Ensure unique index

    // Create a new row
    var newRow = document.createElement('tr');

    // Create new cells with input fields
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
    // Allow the default form submission
    console.log('Form is being submitted normally.');
});

});
