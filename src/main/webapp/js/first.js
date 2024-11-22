// Function to validate email
function validateEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // Basic email regex
    return emailRegex.test(email);
}

// Function to handle adding and redirecting
function addAndRedirect() {
    const emailInput = document.getElementById('new-email-input');
    const email = emailInput.value;

    if (!email || !validateEmail(email)) {
        alert('Please enter a valid email address.');
        return;
    }

    fetch(`${window.location.origin}/CheckEmailExistence`, {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: `email=${encodeURIComponent(email)}`
    })
        .then(response => response.json())
        .then(data => {
            console.log("Server Response:", data); // Debugging log

            if (data.status === "exists") {
                alert("This email is already registered. You cannot add another report.");
            } else if (data.status === "not_exists") {
                alert("Email verified successfully!");
                window.location.href = `${window.location.origin}/processReport1`;
            } else {
                alert("An error occurred. Please try again.");
            }
        })
        .catch(error => {
            console.error("Error:", error);
            alert("An error occurred. Please try again.");
        });
}


// Function to filter table rows based on search input
function filterTable() {
    const input = document.getElementById("search-email-input").value.toLowerCase();
    const rows = document.querySelectorAll("#reports-table tbody tr");

    rows.forEach(row => {
        const email = row.querySelector("td").textContent.toLowerCase();
        row.style.display = email.includes(input) ? "" : "none";
    });
}

// Function to handle the search button click
function searchEmail() {
    const input = document.getElementById("search-email-input");
    if (input.value.trim() === "") {
        alert("Please enter an email to search.");
        return;
    }
    filterTable(); // Reuse the filtering logic
}

// Function to fetch approved emails and update the table dynamically
function fetchApprovedEmails() {
    fetch(`${window.location.origin}/first`)
        .then(response => response.json())
        .then(data => {
            if (Array.isArray(data.approvedEmails)) {
                const tableBody = document.querySelector("#reports-table tbody");
                tableBody.innerHTML = ""; // Clear existing rows

                data.approvedEmails.forEach(email => {
                    const row = document.createElement("tr");
                    row.innerHTML = `
                        <td>${email}</td>
                        <td>
                            <div class="button-container">
                                <button class="table-button view">View</button>
                                <button class="table-button update">Update</button>
                                <button class="table-button delete">Delete</button>
                            </div>
                        </td>
                    `;
                    tableBody.appendChild(row);
                });
            }
        })
        .catch(error => {
            console.error("Error fetching emails:", error);
            alert("Failed to fetch approved emails. Please try again.");
        });
}

// Function to delete an email from the approved list (optional, for delete button)
function deleteEmail(email) {
    fetch(`${window.location.origin}/DeleteEmail`, {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: `email=${encodeURIComponent(email)}`
    })
        .then(response => response.json())
        .then(data => {
            if (data.status === "success") {
                alert("Email deleted successfully!");
                fetchApprovedEmails(); // Refresh the table
            } else {
                alert("Failed to delete email. Please try again.");
            }
        })
        .catch(error => {
            console.error("Error deleting email:", error);
            alert("An error occurred. Please try again.");
        });
}
