// Validate email format
function validateEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

// Add and Redirect
function addAndRedirect() {
    const emailInput = document.getElementById('new-email-input');
    const email = emailInput.value;

    if (!email || !validateEmail(email)) {
        alert('Please enter a valid email address.');
        return;
    }

    console.log("Sending email to CheckUser:", email); // Debugging log

    fetch(`${window.location.origin}/CheckUser`, {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: `email=${encodeURIComponent(email)}`
    })
        .then(response => {
            console.log("Received response:", response); // Debugging log
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            return response.json();
        })
        .then(data => {
            console.log("Server Response:", data); // Debugging log

            switch (data.status) {
                case "not_in_users":
                    alert(data.message || "This email is not registered in the system.");
                    break;
                case "already_approved":
                    alert(data.message || "Report is already added for this email. Please try editing it.");
                    break;
                case "not_approved":
                    alert(data.message || "Email is verified successfully!");
                    if (data.redirectUrl) {
                        window.location.href = `${window.location.origin}${data.redirectUrl}`;
                    }
                    break;
                default:
                    alert(data.message || "An unexpected error occurred. Please try again.");
            }
        })
        .catch(error => {
            console.error("Error occurred:", error); // Debugging log
            alert("An error occurred. Please try again.");
        });
}



// Filter table
function filterTable() {
    const input = document.getElementById("search-email-input").value.toLowerCase();
    const rows = document.querySelectorAll("#reports-table tbody tr");

    rows.forEach(row => {
        const email = row.querySelector("td").textContent.toLowerCase();
        row.style.display = email.includes(input) ? "" : "none";
    });
}

// Search Email
function searchEmail() {
    const input = document.getElementById("search-email-input");
    if (input.value.trim() === "") {
        alert("Please enter an email to search.");
        return;
    }
    filterTable();
}

fetch(`${window.location.origin}/saveUpdatedReport`, {
    method: "POST",
    headers: {
        "Content-Type": "application/x-www-form-urlencoded"
    },
    body: new URLSearchParams(formData) // formData contains all form fields
})
    .then(response => response.json())
    .then(data => {
        if (data.status === "success") {
            alert(data.message || "Report updated successfully!");
            window.location.reload(); // Optionally reload or redirect
        } else {
            alert(data.message || "An error occurred while updating the report.");
        }
    })
    .catch(error => {
        console.error("Error:", error);
        alert("An unexpected error occurred. Please try again.");
    });
