document.addEventListener('DOMContentLoaded', function () {
    // Get the form element after the DOM is fully loaded
    const form = document.getElementById('updateReportForm');

    if (!form) {
        console.warn("Form with ID 'updateReportForm' not found.");
        return; // Exit early if form doesn't exist
    }

    // Add event listener for the addRowButton
    const addRowButton = document.getElementById('addRowButton');
    if (addRowButton) {
        addRowButton.addEventListener('click', function() {
            const tableBody = document.querySelector('#trainingTable tbody');
            const newRow = document.createElement('tr');
            newRow.innerHTML = `
                <td><input type="date" name="exercise_date[]" value=""></td>
                <td><input type="number" name="weight[]" value=""></td>
            `;
            tableBody.appendChild(newRow);
        });
    }

    // Add event listener for form submission
    form.addEventListener('submit', function (event) {
        event.preventDefault(); // Prevents the form from submitting the normal way
        saveReport(); // Call the saveReport function
    });
});

// Save Report function
function saveReport() {
    // Get the form element
    const formElement = document.getElementById('updateReportForm');
    if (!formElement) {
        console.error("Form element not found.");
        return;
    }

    const formData = new FormData(formElement); // Collect all form data
    const formAction = formElement.action; // Get the correct action URL from the form

    // Send the POST request using fetch
    fetch(formAction, {
        method: 'POST',
        body: formData, // Use FormData directly for proper multipart/form-data submission
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            return response.json(); // Parse the JSON response
        })
        .then(data => {
            // Show success alert and redirect if needed
            if (data.status === "success") {
                alert(data.message || "Form submitted successfully!");
                if (data.redirectUrl) {
                    window.location.href = data.redirectUrl; // Redirect to the provided URL
                }
            } else {
                alert(data.message || "An error occurred while submitting the form.");
            }
        })
        .catch(error => {
            console.error("Error occurred:", error);
            alert("An unexpected error occurred. Please try again.");
        });
}