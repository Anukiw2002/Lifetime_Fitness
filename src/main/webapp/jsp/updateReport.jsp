<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map, java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update User Report</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/report.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<body>
<div class="container">
    <h1>UPDATE USER REPORT</h1>
    <%
        Map<String, Object> reportDetails = (Map<String, Object>) request.getAttribute("reportDetails");
        List<Map<String, Object>> exercises = (List<Map<String, Object>>) request.getAttribute("exercises");

        if (reportDetails == null) {
    %>
    <p>No report details available for update.</p>
    <%
    } else {
    %>
    <form id="updateReportForm" action="<%= request.getContextPath() %>/saveUpdatedReport" method="post">
        <!-- Basic Information -->
        <div class="form-section">
            <label>Name: <input type="text" name="name" value="<%= reportDetails.get("name") %>"></label>
            <label>Age: <input type="number" name="age" value="<%= reportDetails.get("age") %>"></label>
            <label>Program No: <input type="text" name="program_no" value="<%= reportDetails.get("program_no") %>"></label>
        </div>

        <!-- Dates -->
        <div class="form-section">
            <label>Starting Date: <input type="date" name="starting_date" value="<%= reportDetails.get("starting_date") %>"></label>
            <label>Expire Date: <input type="date" name="expire_date" value="<%= reportDetails.get("expire_date") %>"></label>
        </div>

        <!-- Measurements -->
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
        <div class="form-section">
            <label>Goal: <input type="text" name="goal" value="<%= reportDetails.get("goal") %>"></label>
            <label>Warm-Up: <input type="text" name="warm_up" value="<%= reportDetails.get("warm_up") %>"></label>
            <label>Flexibility: <input type="text" name="flexibility" value="<%= reportDetails.get("flexibility") %>"></label>
            <label>Cardio: <input type="text" name="cardio" value="<%= reportDetails.get("cardio") %>"></label>
            <label>Remarks: <input type="text" name="remarks" value="<%= reportDetails.get("remarks") %>"></label>
            <input type="hidden" name="email" value="<%= reportDetails.get("email") %>">
        </div>

        <!-- Exercises Table -->
        <h2>Resistance Training Exercises</h2>
        <table id="trainingTable" border="1">
            <thead>
            <tr>
                <th>Exercise Name</th>
                <th>Reps</th>
                <th>Sets</th>
                <th>Exercise Date</th>
                <th>Rest</th>
                <th>Weight</th>
            </tr>
            </thead>
            <tbody>
            <%
                if (exercises != null && !exercises.isEmpty()) {
                    for (Map<String, Object> exercise : exercises) {
            %>
            <tr>
                <td><input type="text" name="exercise_name[]" value="<%= exercise.get("exercise_name") %>"></td>
                <td><input type="number" name="reps[]" value="<%= exercise.get("reps") %>"></td>
                <td><input type="number" name="sets[]" value="<%= exercise.get("sets") %>"></td>
                <td><input type="date" name="exercise_date[]" value="<%= exercise.get("exercise_date") %>"></td>
                <td><input type="text" name="rest[]" value="<%= exercise.get("rest") %>"></td>
                <td><input type="number" name="weight[]" value="<%= exercise.get("weight") %>"></td>
            </tr>
            <%
                }
            } else {
            %>
            <tr>
                <td colspan="6">No exercises found for this report.</td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
        <button type="button" class="submit-button" onclick="saveReport()">Save changes</button>
    </form>
    <%
        }
    %>
    <script src="<%= request.getContextPath() %>/js/updateReport.js"></script>
</div>
</body>
</html>