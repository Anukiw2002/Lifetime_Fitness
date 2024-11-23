<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Workout</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/workout.css">
</head>
<body>
<!-- Navbar Container -->
<div class="navbar-container">
  <jsp:include page="../common/verticalNavBar.jsp" />
</div>

<!-- Main Content Container -->
<div class="content-container">
  <div class="workout-list-container">
    <h1>Workout</h1>
    <div class="workout-list">
      <div class="workout-item">
        <input type="text" class="workout-input" readonly value="Workout 1">
        <button class="delete-btn">&#10006;</button>
      </div>
      <div class="workout-item">
        <input type="text" class="workout-input" readonly value="Workout 2">
        <button class="delete-btn">&#10006;</button>
      </div>
      <div class="workout-item">
        <input type="text" class="workout-input" readonly value="Workout 3">
        <button class="delete-btn">&#10006;</button>
      </div>
      <div class="workout-item">
        <input type="text" class="workout-input" readonly value="Workout 4">
        <button class="delete-btn">&#10006;</button>
      </div>
      <!-- Add more workout rows as needed -->
    </div>
    <div class="navigation">
      <button class="nav-btn">Prev</button>
      <button class="nav-btn">Next</button>
    </div>
  </div>
</div>
</body>
</html>
