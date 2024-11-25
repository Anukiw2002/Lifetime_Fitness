<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map, java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>User Report</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/report.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<body>
<div class="container">
  <h1>USER REPORT</h1>
  <%
    Map<String, Object> reportDetails = (Map<String, Object>) request.getAttribute("reportDetails");
    List<Map<String, Object>> exercises = (List<Map<String, Object>>) request.getAttribute("exercises");
    if (reportDetails == null) {
  %>
  <p>No report details available.</p>
  <%
  } else {
  %>
  <form id="userReportForm" action="<%= request.getContextPath() %>/processReport" method="post">
    <!-- Basic Information -->
    <div class="form-section">
      <label>Name: <input type="text" name="name" value="<%= reportDetails.get("name") %>" readonly></label>
      <label>Age: <input type="number" name="age" value="<%= reportDetails.get("age") %>" readonly></label>
      <label>Program No: <input type="text" name="program_no" value="<%= reportDetails.get("program_no") %>" readonly></label>
    </div>

    <!-- Dates -->
    <div class="form-section">
      <label>Starting Date: <input type="date" name="starting_date" value="<%= reportDetails.get("starting_date") %>" readonly></label>
      <label>Expire Date: <input type="date" name="expire_date" value="<%= reportDetails.get("expire_date") %>" readonly></label>
    </div>

    <!-- Measurements -->
    <div class="form-section">
      <label>Maximum Heart Rate: <input type="number" name="max_heart_rate" value="<%= reportDetails.get("max_heart_rate") %>" readonly></label>
      <label>65% BPM: <input type="number" name="bpm_65" value="<%= reportDetails.get("bpm_65") %>" readonly></label>
      <label>75% BPM: <input type="number" name="bpm_75" value="<%= reportDetails.get("bpm_75") %>" readonly></label>
      <label>85% BPM: <input type="number" name="bpm_85" value="<%= reportDetails.get("bpm_85") %>" readonly></label>
      <label>Waist Circumference: <input type="number" name="waist_circumference" value="<%= reportDetails.get("waist_circumference") %>" readonly></label>
      <label>Body Weight: <input type="number" name="body_weight" value="<%= reportDetails.get("body_weight") %>" readonly></label>
      <label>Height: <input type="number" name="height" value="<%= reportDetails.get("height") %>" readonly></label>
      <label>Fat %: <input type="number" name="fat_percentage" value="<%= reportDetails.get("fat_percentage") %>" readonly></label>
      <label>BMR: <input type="number" name="bmr" value="<%= reportDetails.get("bmr") %>" readonly></label>
    </div>

    <!-- Goal and Remarks -->
    <div class="form-section">
      <label>Goal: <input type="text" name="goal" value="<%= reportDetails.get("goal") %>" readonly></label>
      <label>Warm-Up: <input type="text" name="warm_up" value="<%= reportDetails.get("warm_up") %>" readonly></label>
      <label>Flexibility: <input type="text" name="flexibility" value="<%= reportDetails.get("flexibility") %>" readonly></label>
      <label>Cardio: <input type="text" name="cardio" value="<%= reportDetails.get("cardio") %>" readonly></label>
      <label>Remarks: <input type="text" name="remarks" value="<%= reportDetails.get("remarks") %>" readonly></label>
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
        <td><input type="text" value="<%= exercise.get("exercise_name") %>" readonly></td>
        <td><input type="number" value="<%= exercise.get("reps") %>" readonly></td>
        <td><input type="number" value="<%= exercise.get("sets") %>" readonly></td>
        <td><input type="date" value="<%= exercise.get("exercise_date") %>" readonly></td>
        <td><input type="text" value="<%= exercise.get("rest") %>" readonly></td>
        <td><input type="number" value="<%= exercise.get("weight") %>" readonly></td>
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
  </form>
  <%
    }
  %>
</div>
</body>
</html>