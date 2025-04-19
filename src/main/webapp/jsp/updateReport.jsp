<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map, java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update User Report</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/generalStyles.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/report.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/common/verticalNavBar.jsp" />
<div class="main-content">
    <div class="report-container">
        <h1 class="text-center mb-4">UPDATE USER REPORT</h1>
        <%
            Map<String, Object> reportDetails = (Map<String, Object>) request.getAttribute("reportDetails");
            List<Map<String, Object>> exercises = (List<Map<String, Object>>) request.getAttribute("exercises");

            if (reportDetails == null) {
        %>
        <p class="text-center">No report details available for update.</p>
        <%
        } else {
        %>
        <form id="updateReportForm" action="<%= request.getContextPath() %>/saveUpdatedReport" method="post">
            <!-- Basic Information -->
            <h3 class="section-title">Basic Information</h3>
            <div class="form-section">
                <label>Name: <input type="text" name="name" value="<%= reportDetails.get("name") %>"></label>
                <label>Age: <input type="number" name="age" value="<%= reportDetails.get("age") %>"></label>
                <label>Program No: <input type="text" name="program_no" value="<%= reportDetails.get("program_no") %>"></label>
            </div>

            <!-- Dates -->
            <h3 class="section-title">Program Duration</h3>
            <div class="form-section">
                <label>Starting Date: <input type="date" name="starting_date" value="<%= reportDetails.get("starting_date") %>"></label>
                <label>Expire Date: <input type="date" name="expire_date" value="<%= reportDetails.get("expire_date") %>"></label>
            </div>

            <!-- Measurements -->
            <h3 class="section-title">Health Metrics</h3>
            <div class="form-section">
                <label>Maximum Heart Rate: <input type="number" name="max_heart_rate" value="<%= reportDetails.get("max_heart_rate") %>"></label>
                <label>65% BPM: <input type="number" name="bpm_65" value="<%= reportDetails.get("bpm_65") %>"></label>
                <label>75% BPM: <input type="number" name="bpm_75" value="<%= reportDetails.get("bpm_75") %>"></label>
                <label>85% BPM: <input type="number" name="bpm_85" value="<%= reportDetails.get("bpm_85") %>"></label>
                <label>Waist Circumference: <input type="number" name="waist_circumference" value="<%= reportDetails.get("waist_circumference") %>"></label>
                <label>Body Weight: <input type="number" name="body_weight" value="<%= reportDetails.get("body_weight") %>"></label>
                <label>Height: <input type="number" name="height" value="<%= reportDetails.get("height") %>"></label>
                <label>Fat %: <input type="number" name="fat_percentage" value="<%= reportDetails.get("fat_percentage") %>"></label>
                <label>BMR: <input type="number" name="bmr" value="<%= reportDetails.get("bmr") %>"></label>
            </div>

            <!-- Goal and Remarks -->
            <h3 class="section-title">Program Details</h3>
            <div class="form-section">
                <label>Goal: <input type="text" name="goal" value="<%= reportDetails.get("goal") %>"></label>
                <label>Warm-Up: <input type="text" name="warm_up" value="<%= reportDetails.get("warm_up") %>"></label>
                <label>Flexibility: <input type="text" name="flexibility" value="<%= reportDetails.get("flexibility") %>"></label>
                <label>Cardio: <input type="text" name="cardio" value="<%= reportDetails.get("cardio") %>"></label>
                <label>Remarks: <input type="text" name="remarks" value="<%= reportDetails.get("remarks") %>"></label>
                <input type="hidden" name="email" value="<%= reportDetails.get("email") %>">
                <label>Target Weight: <input type="number" name="target_weight" value="<%= reportDetails.get("target_weight") %>"></label>
            </div>

            <!-- Exercises Table -->
            <h3 class="section-title">Track Weight</h3>
            <div class="table-section">
                <table id="trainingTable" class="report-table">
                    <thead>
                    <tr>

                        <th>Date</th>
                        <th>Weight</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        if (exercises != null && !exercises.isEmpty()) {
                            for (Map<String, Object> exercise : exercises) {
                    %>
                    <tr>
                        <td><input type="date" name="exercise_date[]" value="<%= exercise.get("exercise_date") %>"></td>
                        <td><input type="number" name="weight[]" value="<%= exercise.get("weight") %>"></td>
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="6" class="text-center">No exercises found for this report.</td>
                    </tr>
                    <%
                        }
                    %>
                    </tbody>
                </table>
            </div>

            <!-- Submit Button -->
            <div class="flex justify-center mt-4">
                <button type="button" class="btn btn-success" onclick="saveReport()">
                    <i class="fas fa-save"></i> Save Changes
                </button>
            </div>
        </form>
        <%
            }
        %>
    </div>
</div>
<script src="<%= request.getContextPath() %>/js/updateReport.js"></script>
</body>
</html>