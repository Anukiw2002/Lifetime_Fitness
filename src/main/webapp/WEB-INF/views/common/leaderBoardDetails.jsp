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

            <form id="leaderboard-form" class="form-group" action="<%= request.getContextPath()%>/leaderBoardDetails" method="post">
                <!-- Client Search Section -->
                <div class="client-search mb-4">
                    <h2 class="mb-3">Search Client</h2>
                    <div class="search-container">
                        <div class="form-group">
                            <label class="form-label" for="clientSearch">Search by Phone Number:</label>
                            <input type="tel"
                                   id="clientSearch"
                                   name="clientSearch"
                                   class="form-control"
                                   placeholder="Enter client's phone number"
                                   pattern="[0-9]*"
                                   maxlength="10"
                                   required>
                        </div>
                        <div id="clientInfo" class="client-info mt-3" style="display: none;">
                            <div class="client-card">
                                <h3>Client Details</h3>
                                <p id="clientName"></p>
                                <p id="clientPhone"></p>
                                <input type="hidden" id="clientId" name="clientId">
                            </div>
                        </div>
                    </div>
                </div>

                <h2 class="mb-3">Select Category</h2>
                <div class="category-grid">
                    <label class="radio-label">
                        <input type="radio" name="category" value="Push-ups">
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
                    <input type="number" name="amount" class="form-control">
                    <p class="amount-note">For weight-based categories, KG will be considered</p>
                </div>

                <div class="btn-group">
                    <button type="submit" class="btn btn-primary">Submit</button>
                    <a href="leaderBoard" class="btn btn-secondary">View LeaderBoard</a>
                </div>
            </form>
        </div>
    </div>
</div>


<script>
    document.getElementById('leaderboard-form').addEventListener('submit', function(e) {
        e.preventDefault(); // prevent default form submit

        const formData = new FormData(this);

        fetch('/leaderBoardDetails', {
            method: 'POST',
            body: formData
        })
            .then(res => res.json())
            .then(data => {
                if (data.success) {
                    alert(data.message);
                    // Optionally reload or reset form
                } else {
                    alert(data.message); // this shows "User does not exist."
                }
            })
            .catch(err => {
                console.error('AJAX Error:', err);
                alert('An error occurred while submitting the form.');
            });
    });
</script>

<!-- Add these styles -->
<style>

    .client-card {        background: #f8f9fa;
        border: 1px solid #dee2e6;
        border-radius: 4px;
        padding: 15px;
        margin-top: 10px;
    }

    .client-card h3 {
        margin: 0 0 10px 0;
        font-size: 1.1em;
    }

    .client-card p {
        margin: 5px 0;
    }

    #clientSearch {
        font-size: 16px;
        padding: 8px 12px;
    }
</style>

</body>
</html>