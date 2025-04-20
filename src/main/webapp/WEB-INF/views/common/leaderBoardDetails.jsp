<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Leaderboard Details</title>
    <!-- Reference to the CSS files -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/generalStyles.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/leaderBoardDetails.css">
</head>
<body>
<jsp:include page="../instructor/instructorVerticalNavbar.jsp" />
<div class="main-content">
    <div class="container">
        <div class="card">
            <h1 class="text-center mb-4">Leaderboard Details</h1>

            <form id="leaderboard-form" class="form-group" action="<%= request.getContextPath() %>/leaderBoardDetails" method="post">
                <!-- Client Search Section -->
                <div class="client-search mb-4">
                    <h2 class="mb-3">Search Client</h2>
                    <div class="search-container">
                        <div class="form-group">
                            <label class="form-label" for="clientSearch">Search by Phone Number:</label>
                            <div class="input-group">
                                <input type="tel"
                                       id="clientSearch"
                                       name="clientSearch"
                                       class="form-control"
                                       placeholder="Enter client's phone number"
                                       pattern="[0-9]*"
                                       maxlength="10"
                                       required>
                                <button type="button" id="searchBtn" class="btn btn-secondary">Search</button>
                            </div>
                        </div>
                        <div id="clientInfo" class="client-info mt-3" style="display: none;">
                            <div class="client-card">
                                <h3>Client Details</h3>
                                <p id="clientName"></p>
                                <p id="clientPhone"></p>
                                <input type="hidden" id="clientId" name="clientId">
                            </div>
                        </div>
                        <div id="errorMessage" class="alert alert-danger mt-3" style="display: none;"></div>
                    </div>
                </div>

                <h2 class="mb-3">Select Category</h2>
                <div class="category-grid">
                    <label class="radio-label">
                        <input type="radio" name="category" value="Push-ups" required>
                        Push-ups
                    </label>
                    <label class="radio-label">
                        <input type="radio" name="category" value="Squats">
                        Squats
                    </label>
                    <label class="radio-label">
                        <input type="radio" name="category" value="Deadlifts">
                        Deadlifts
                    </label>
                    <label class="radio-label">
                        <input type="radio" name="category" value="Bench-Press">
                        Bench Press
                    </label>
                    <label class="radio-label">
                        <input type="radio" name="category" value="Tricep-Dips">
                        Tricep Dips
                    </label>
                </div>

                <div class="form-group amount-input">
                    <label class="form-label">Enter the maximum amount:</label>
                    <input type="number" name="amount" class="form-control" required>
                    <p class="amount-note">For weight-based categories, KG will be considered</p>
                </div>

                <div class="btn-group">
                    <button type="submit" class="btn btn-primary">Submit</button>
                    <a href="<%= request.getContextPath() %>/leaderBoard" class="btn btn-secondary">View LeaderBoard</a>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    // Client search functionality
    document.getElementById('searchBtn').addEventListener('click', function() {
        const phoneNumber = document.getElementById('clientSearch').value;
        if (!phoneNumber) {
            showError("Please enter a phone number");
            return;
        }

        // AJAX call to search for client
        fetch('<%= request.getContextPath() %>/searchClient?phone=' + phoneNumber)
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    // Display client info
                    document.getElementById('clientName').textContent = 'Name: ' + data.fullName;
                    document.getElementById('clientPhone').textContent = 'Phone: ' + phoneNumber;
                    document.getElementById('clientId').value = data.userId;
                    document.getElementById('clientInfo').style.display = 'block';
                    document.getElementById('errorMessage').style.display = 'none';
                } else {
                    showError(data.message || "Client not found");
                    document.getElementById('clientInfo').style.display = 'none';
                }
            })
            .catch(err => {
                console.error('AJAX Error:', err);
                showError("An error occurred while searching for the client");
            });
    });

    // Form submission
    document.getElementById('leaderboard-form').addEventListener('submit', function(e) {
        // Check if client has been found and selected
        if (document.getElementById('clientInfo').style.display === 'none') {
            e.preventDefault();
            showError("Please search and select a client first");
            return;
        }

        // Check if a category is selected
        const categorySelected = document.querySelector('input[name="category"]:checked');
        if (!categorySelected) {
            e.preventDefault();
            showError("Please select a category");
            return;
        }

        // Check if amount is entered
        const amount = document.querySelector('input[name="amount"]').value;
        if (!amount) {
            e.preventDefault();
            showError("Please enter an amount");
            return;
        }
    });

    function showError(message) {
        const errorDiv = document.getElementById('errorMessage');
        errorDiv.textContent = message;
        errorDiv.style.display = 'block';
    }

    // Check for error messages from the server
    window.onload = function() {
        <% if(request.getAttribute("errorMessage") != null) { %>
        showError("<%= request.getAttribute("errorMessage") %>");
        <% } %>

        <% if(request.getAttribute("successMessage") != null) { %>
        alert("<%= request.getAttribute("successMessage") %>");
        <% } %>
    };
</script>

</body>
</html>