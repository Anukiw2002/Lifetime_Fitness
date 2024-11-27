<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
  <jsp:include page="clientVerticalNavbar.jsp" />
</div>

<!-- Main Content Container -->
<div class="content-container">
  <div class="workout-list-container">
    <h1>Workout</h1>
    <div class="workout-list">
      <c:forEach items="${exercises}" var="exercise" varStatus="status">
        <div class="workout-item">
          <input type="text" class="workout-input" readonly
                 value="${exercise.exercise.exerciseName} - Sets: ${exercise.setNumber}, Reps: ${exercise.reps}">
          <c:if test="${not empty exercise.notes}">
            <div class="exercise-notes">${exercise.notes}</div>
          </c:if>
        </div>
      </c:forEach>
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
