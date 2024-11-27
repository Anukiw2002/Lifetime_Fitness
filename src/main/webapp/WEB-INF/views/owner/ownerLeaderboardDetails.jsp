<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Leaderboard Details</title>
    <!-- Reference to the CSS file -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/generalStyles.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/leaderBoardDetails.css">
    <!-- Reference to the JavaScript file -->
    <script defer src="<%= request.getContextPath() %>/js/leaderBoardDetails.js"></script>
</head>
<body>
<jsp:include page="../common/verticalNavBar.jsp" />
<div class="main-content">
    <div class="container">
        <div class="card">
            <h1 class="text-center mb-4">Leaderboard Details</h1>

            <form id="leaderboard-form" class="form-group">
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
                        <input type="radio" name="category" value="Bench Press">
                        Bench Press
                    </label>
                    <label class="radio-label">
                        <input type="radio" name="category" value="Pull-Ups">
                        Pull-Ups
                    </label>
                    <label class="radio-label">
                        <input type="radio" name="category" value="Tricep Dips">
                        Tricep Dips
                    </label>
                    <label class="radio-label">
                        <input type="radio" name="category" value="Overhead Press">
                        Overhead Press
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
</body>
</html>