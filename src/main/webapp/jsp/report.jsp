<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Report</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/generalStyles.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/report.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/common/verticalNavBar.jsp" />
<div class="main-content">
    <div class="report-container">
        <h1 class="text-center mb-4">USER REPORT</h1>
        <form id="userReportForm" action="<%= request.getContextPath() %>/processReport1" method="post">
            <!-- Basic Information -->
            <h3 class="section-title">Basic Information</h3>
            <div class="form-section">
                <label>Name: <input type="text" name="name" required></label>
                <label>Age: <input type="number" name="age" required></label>
                <label>Program No: <input type="text" name="program_no" required></label>
            </div>

            <!-- Dates -->
            <h3 class="section-title">Program Duration</h3>
            <div class="form-section">
                <label>Starting date: <input type="date" name="starting_date" required></label>
                <label>Expire date: <input type="date" name="expire_date" required></label>
            </div>

            <!-- Frequency -->
            <h3 class="section-title">Training Frequency</h3>
            <div class="form-section">
                <label>Frequency: <input type="text" name="frequency"></label>
                <label>Times per week: <input type="number" name="times_per_week"></label>
            </div>

            <!-- Heart Rate -->
            <h3 class="section-title">Heart Rate Metrics</h3>
            <div class="form-section">
                <label>Maximum heart rate: <input type="number" name="max_heart_rate"></label>
                <label>65% Bpm: <input type="number" name="bpm_65"></label>
                <label>75% Bpm: <input type="number" name="bpm_75"></label>
                <label>85% Bpm: <input type="number" name="bpm_85"></label>
            </div>

            <!-- Measurements -->
            <h3 class="section-title">Body Measurements</h3>
            <div class="form-section">
                <label>Waist circumference(cm): <input type="number" name="waist_circumference"></label>
                <label>Body weight(kg): <input type="number" name="body_weight"></label>
                <label>Height(cm): <input type="number" name="height"></label>
                <label>Fat %: <input type="number" name="fat"></label>
                <label>Basal Metabolic Rate: <input type="number" name="bmr"></label>
            </div>

            <!-- Goal -->
            <h3 class="section-title">Program Goal</h3>
            <div class="form-section">
                <label>GOAL: <input type="text" name="goal"></label>
                <label>Target weight(kg) : <input type="number" name="target_weight"></label>
                <input type="hidden" name="userEmail" value="<%= session.getAttribute("userEmail") %>">

            </div>

            <!-- Exercises -->
            <h3 class="section-title">Track Weight</h3>
            <div class="table-container">
                <table id="trainingTable" class="report-table">
                    <thead>
                    <tr>

                        <th>Date</th>
                        <th>Weight</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>

                        <td><input type="date" name="date_1"></td>
                        <td><input type="number" name="weight_1" placeholder="Weight (kg)"></td>
                    </tr>
                    </tbody>
                </table>
                <button type="button" id="addRowButton" class="btn btn-primary">
                    <i class="fas fa-plus"></i> Add Row
                </button>
            </div>

            <!-- Cardio and Remarks -->
            <h3 class="section-title">Additional Information</h3>
            <div class="form-section">
                <label>Cardio: <input type="text" name="cardio"></label>
                <label>Remarks: <input type="text" name="remarks"></label>
            </div>

            <!-- Submit -->
            <div class="form-section submit-section">
                <button type="submit" class="btn btn-success">
                    <i class="fas fa-check"></i> Submit
                </button>
            </div>
        </form>
    </div>
</div>
<div id="message" style="display:none;">
    <%= request.getAttribute("message") != null ? request.getAttribute("message") : "" %>
</div>
<script src="<%= request.getContextPath() %>/js/report.js"></script>
</body>
</html>