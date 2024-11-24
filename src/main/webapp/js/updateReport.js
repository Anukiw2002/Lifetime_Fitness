document.getElementById('updateForm').addEventListener('submit', function (event) {
    event.preventDefault(); // Prevents the form from submitting the normal way
    fetch('/saveUpdatedReport', {
        method: 'POST',
        body: new FormData(this)
    })
        .then(response => response.json())
        .then(data => {
            if (data.status === "success") {
                alert("Report updated successfully!");
            } else {
                alert("Failed to update report: " + data.message);
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert("An error occurred while updating the report.");
        });
});

// Save Report
function saveReport() {
    // Get the form element
    const formElement = document.getElementById('updateReportForm');
    const formData = new FormData(formElement); // Collect all form data

    // Send the POST request using fetch
    fetch(`${window.location.origin}/saveUpdatedReport`, {
        method: 'POST',
        body: new URLSearchParams(formData), // Convert FormData to URL-encoded format
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

