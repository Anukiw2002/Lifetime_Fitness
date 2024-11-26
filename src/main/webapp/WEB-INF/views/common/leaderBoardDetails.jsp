<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Leaderboard Details</title>
    <!-- Reference to the CSS file -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/leaderBoardDetails.css">
    <!-- Reference to the JavaScript file -->
    <script defer src="<%= request.getContextPath() %>/js/leaderBoardDetails.js"></script>
</head>
<body>
<div class="container">
    <h1><u>LeaderBoard Details</u></h1>

    <h2>Select Category</h2>
    <form id="leaderboard-form">
        <div class="form-section category">
            <label class="radio">
                <input type="radio" name="category" value="Push-ups">
                <span class="radio-custom"></span>
                Push-ups
            </label>
            <label class="radio">
                <input type="radio" name="category" value="Squats">
                <span class="radio-custom"></span>
                Squats
            </label>
            <label class="radio">
                <input type="radio" name="category" value="Deadlifts">
                <span class="radio-custom"></span>
                Deadlifts
            </label>
            <label class="radio">
                <input type="radio" name="category" value="Bench Press">
                <span class="radio-custom"></span>
                Bench Press
            </label>
            <label class="radio">
                <input type="radio" name="category" value="Pull-Ups">
                <span class="radio-custom"></span>
                Pull-Ups
            </label>
            <label class="radio">
                <input type="radio" name="category" value="Tricep Dips">
                <span class="radio-custom"></span>
                Tricep Dips
            </label>
            <label class="radio">
                <input type="radio" name="category" value="Overhead Press">
                <span class="radio-custom"></span>
                Overhead Press
            </label>
        </div>
        <div class="form-section">
            <label>Enter the maximum amount:
                <input type="number" name="amount">
            </label>
            <p>(For the weight-based categories, KG will be considered)</p>
        </div>
        <button type="submit" class="submit-button">Submit</button><br/><br/><br/>
        <a href="/leaderBoard">
            <button type="button" class="view-leaderboard">View LeaderBoard</button>
        </a>
    </form>
</div>
</body>
</html>
