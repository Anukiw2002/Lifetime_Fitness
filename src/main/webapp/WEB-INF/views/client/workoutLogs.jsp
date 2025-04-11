<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Exercise Log</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/workoutLogs.css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="../client/clientVerticalNavbar.jsp" />
<div class="main-content">
    <!-- Workout Logs Container -->
    <div class="workout-logs-container">
        <div class="exercise-info">
            <h2 class="exercise-name">${workout.workoutName} - ${currentExercise.exercise.exerciseName}</h2>
            <p class="text-muted">
                Exercise ${exerciseIndex + 1} of ${totalExercises} |
                Sets: ${currentExercise.setNumber} |
                Reps: ${currentExercise.reps}
            </p>
            <c:if test="${not empty currentExercise.notes}">
                <div class="exercise-notes">
                    <strong>Instructions:</strong> ${currentExercise.notes}
                </div>
            </c:if>
        </div>

        <form id="workoutLogForm" action="${pageContext.request.contextPath}/InsertWorkoutLogsServlet" method="post">
            <input type="hidden" name="workoutId" value="${workout.workoutId}">
            <input type="hidden" name="exerciseId" value="${currentExercise.exercise.exerciseId}">
            <input type="hidden" name="exerciseIndex" value="${exerciseIndex}">
            <input type="hidden" name="totalSets" value="${currentExercise.setNumber}">

            <div class="set-grid">
                <c:forEach var="setNum" begin="1" end="${currentExercise.setNumber}">
                    <div class="set-card">
                        <div class="set-header">Set ${setNum}</div>
                        <div class="input-group">
                            <label for="weight${setNum}">Weight (kg)</label>
                            <input type="number" id="weight${setNum}" name="weight${setNum}" class="form-control" placeholder="0.0" min="0" step="0.5">
                        </div>
                        <div class="input-group">
                            <label for="reps${setNum}">Reps (Target: ${currentExercise.reps})</label>
                            <input type="number" id="reps${setNum}" name="reps${setNum}" class="form-control" placeholder="0" min="0">
                        </div>
                    </div>
                </c:forEach>
            </div>

            <!-- Notes Section -->
            <div class="notes-section">
                <label for="exerciseNotes" class="form-label">Your Notes</label>
                <textarea id="exerciseNotes" name="userNotes" class="form-control"
                          placeholder="Add any notes about this exercise (form, difficulty, etc.)"></textarea>
            </div>

            <!-- Navigation Buttons -->
            <div class="nav-buttons">
                <c:choose>
                    <c:when test="${isFirstExercise}">
                        <button type="button" class="btn btn-secondary" disabled>
                            <i class="fas fa-arrow-left"></i> Previous Exercise
                        </button>
                    </c:when>
                    <c:otherwise>
                        <button type="submit" name="action" value="previous" class="btn btn-secondary">
                            <i class="fas fa-arrow-left"></i> Previous Exercise
                        </button>
                    </c:otherwise>
                </c:choose>

                <c:choose>
                    <c:when test="${isLastExercise}">
                        <button type="submit" name="action" value="finish" class="btn btn-success">
                            <i class="fas fa-check"></i> Finish Workout
                        </button>
                    </c:when>
                    <c:otherwise>
                        <button type="submit" name="action" value="next" class="btn btn-primary">
                            Next Exercise <i class="fas fa-arrow-right"></i>
                        </button>
                    </c:otherwise>
                </c:choose>
            </div>
        </form>
    </div>
</div>

<script>
    // Track progress
    const currentExercise = ${exerciseIndex} + 1;
    const totalExercises = ${totalExercises};

    // Add input validation for numbers
    document.querySelectorAll('input[type="number"]').forEach(input => {
        input.addEventListener('input', function() {
            if (this.value < 0) this.value = 0;
        });
    });

    // Show completion confirmation
    document.querySelector('button[value="finish"]')?.addEventListener('click', function(e) {
        const confirmed = confirm('Are you sure you want to finish this workout?');
        if (!confirmed) {
            e.preventDefault();
        }
    });
</script>
</body>
</html>