<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <title>Workout Details | Lifetime Fitness</title>
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/workoutStylesheet.css">
  </head>
<body>

<div class="container">
    <a href="javascript:history.back()" class="back-button">
        <i class="fas fa-arrow-left"></i> Back to Workouts
    </a>

    <div class="workout-header">
        <div class="workout-title">
            <h2>${workout.workoutName}</h2>
            <span class="category-badge">
                <i class="fas fa-tag"></i> ${workout.category.categoryName}
            </span>
        </div>
    </div>

    <div class="exercise-list">
        <c:choose>
            <c:when test="${empty workout.exercises}">
                <div class="no-exercises">
                    <p>No exercises found for this workout.</p>
                </div>
            </c:when>
            <c:otherwise>
                <c:forEach var="exercise" items="${workout.exercises}">
                    <div class="exercise-card">
                        <div class="exercise-header">
                            <span class="exercise-name">
                                <i class="fas fa-running"></i>
                                ${exercise.exercise.exerciseName}
                            </span>
                        </div>
                        <div class="exercise-details">
                            <div class="detail-box">
                                <div class="detail-label">Set</div>
                                <div class="detail-value">${exercise.setNumber}</div>
                            </div>
                            <div class="detail-box">
                                <div class="detail-label">Reps</div>
                                <div class="detail-value">${exercise.reps}</div>
                            </div>
                        </div>
                        <c:if test="${not empty exercise.notes}">
                            <div class="exercise-notes">
                                <i class="fas fa-sticky-note"></i> ${exercise.notes}
                            </div>
                        </c:if>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>