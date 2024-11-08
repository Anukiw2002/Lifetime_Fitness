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
    <form id="userReportForm" action="<%= request.getContextPath() %>/processReport" method="post">
        <div class="form-section">
            <label>Name: <input type="text" name="name"></label>
            <label>Age: <input type="number" name="age"></label>
            <label>Program No: <input type="text" name="program_no"></label>
        </div>
        <div class="form-section">
            <label>Starting date: <input type="date" name="starting_date"></label>
            <label>Expire date: <input type="date" name="expire_date"></label>
        </div>
        <div class="form-section">
            <label>Frequency: <input type="text" name="frequency"></label>
            <label>Times per week: <input type="number" name="times_per_week"></label>
        </div>
        <div class="form-section">
            <label>Maximum heart rate: <input type="number" name="max_heart_rate"></label>
            <label>65% Bpm: <input type="number" name="bpm_65"></label>
            <label>75% Bpm: <input type="number" name="bpm_75"></label>
            <label>85% Bpm: <input type="number" name="bpm_85"></label>
        </div>
        <div class="form-section">
            <label>Waist circumference: <input type="number" name="waist_circumference"></label>
            <label>Body weight: <input type="number" name="body_weight"></label>
        </div>
        <div class="form-section">
            <label>Height: <input type="number" name="height"></label>
            <label>Fat %: <input type="number" name="fat"></label>
            <label>Basal Metabolic Rate: <input type="number" name="bmr"></label>
        </div>
        <div class="form-section">
            <label>GOAL: <input type="text" name="goal"></label>
        </div>
        <div class="form-section">
            <label>Warm Up: <input type="text" name="warm_up"></label>
            <label>Flexibility: <input type="text" name="flexibility"></label>
        </div>
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
                <tr>
                    <td><input type="text" name="exercise_2" placeholder="Enter Exercise"></td>
                    <td><input type="number" name="reps_2" placeholder="Reps"></td>
                    <td><input type="number" name="sets_2" placeholder="Sets"></td>
                    <td><input type="date" name="date_2"></td>
                    <td><input type="text" name="rest_2" placeholder="Rest"></td>
                    <td><input type="number" name="weight_2" placeholder="Weight (kg)"></td>
                </tr>
                </tbody>
            </table>
            <button type="button" id="addRowButton"><i class="fas fa-plus"></i> Add Row</button>
        </div>
        <div class="form-section">
            <label>Cardio: <input type="text" name="cardio"></label>
            <label>Flexibility: <input type="text" name="flexibility_2"></label>
        </div>
        <div class="form-section">
            <label>Remarks: <input type="text" name="remarks"></label>
        </div>
        <div class="form-section submit-section">
            <button type="submit"><i class="fas fa-check"></i> Submit</button>
        </div>
    </form>
</div>
<!-- Linking JS files -->
<script src="<%= request.getContextPath() %>/js/report.js"></script>
</body>
</html>
