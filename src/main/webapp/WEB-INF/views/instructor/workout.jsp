<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Workout</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/workout.css">
</head>
<body>
<div class="main-container">
  <jsp:include page="../common/verticalNavBar.jsp" />
  <div class="workout-content">
    <h1>Workout</h1>
    <div class="workout-list">
      <div class="workout-item">
        <input type="text" class="workout-input" readonly>
        <button class="check-btn">&#10003;</button>
        <button class="delete-btn">&#10006;</button>
      </div>
      <!-- Repeat the workout-item structure for additional items -->
    </div>
    <div class="navigation">
      <button class="nav-btn">Prev</button>
      <button class="nav-btn">Next</button>
    </div>
  </div>
</div>
</body>
</html>
