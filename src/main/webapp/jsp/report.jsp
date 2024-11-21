<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Report</title>
    <!-- Linking CSS files -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/report.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<body>
<div class="container">
    <h1>USER REPORT</h1>
    <form id="userReportForm" action="<%= request.getContextPath() %>/processReport1" method="post">
        <!-- Basic Information -->
        <div class="form-section">
            <label>Name: <input type="text" name="name" required></label>
            <label>Age: <input type="number" name="age" required></label>
            <label>Program No: <input type="text" name="program_no" required></label>
        </div>

        <!-- Dates -->
        <div class="form-section">
            <label>Starting date: <input type="date" name="starting_date" required></label>
            <label>Expire date: <input type="date" name="expire_date" required></label>
        </div>

        <!-- Frequency -->
        <div class="form-section">
            <label>Frequency: <input type="text" name="frequency"></label>
            <label>Times per week: <input type="number" name="times_per_week"></label>
        </div>

        <!-- Heart Rate -->
        <div class="form-section">
            <label>Maximum heart rate: <input type="number" name="max_heart_rate"></label>
            <label>65% Bpm: <input type="number" name="bpm_65"></label>
            <label>75% Bpm: <input type="number" name="bpm_75"></label>
            <label>85% Bpm: <input type="number" name="bpm_85"></label>
        </div>

        <!-- Measurements -->
        <div class="form-section">
            <label>Waist circumference: <input type="number" name="waist_circumference"></label>
            <label>Body weight: <input type="number" name="body_weight"></label>
            <label>Height: <input type="number" name="height"></label>
            <label>Fat %: <input type="number" name="fat"></label>
            <label>Basal Metabolic Rate: <input type="number" name="bmr"></label>
        </div>

        <!-- Goal -->
        <div class="form-section">
            <label>GOAL: <input type="text" name="goal"></label>
        </div>

        <!-- Exercises -->
        <div class="table-container">
            <table id="trainingTable">
                <thead>
                <tr>
                    <th>Resistance Training Exercise</th>
                    <th>Reps</th>
                    <th>Sets</th>
                    <th>Date</th>
                    <th>Rest</th>
                    <th>Weight</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td><input type="text" name="exercise_1" placeholder="Enter Exercise"></td>
                    <td><input type="number" name="reps_1" placeholder="Reps"></td>
                    <td><input type="number" name="sets_1" placeholder="Sets"></td>
                    <td><input type="date" name="date_1"></td>
                    <td><input type="text" name="rest_1" placeholder="Rest"></td>
                    <td><input type="number" name="weight_1" placeholder="Weight (kg)"></td>
                </tr>
                </tbody>
            </table>
            <button type="button" id="addRowButton"><i class="fas fa-plus"></i> Add Row</button>
        </div>

        <!-- Cardio and Remarks -->
        <div class="form-section">
            <label>Cardio: <input type="text" name="cardio"></label>
            <label>Remarks: <input type="text" name="remarks"></label>
        </div>

        <!-- Submit -->
        <div class="form-section submit-section">
            <button type="submit"><i class="fas fa-check"></i> Submit</button>
        </div>
    </form>
</div>
<script src="<%= request.getContextPath() %>/js/report.js"></script>
</body>
</html>
