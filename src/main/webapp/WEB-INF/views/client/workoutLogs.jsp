<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <!-- ... existing head content ... -->
</head>
<body>
<jsp:include page="../client/clientVerticalNavbar.jsp" />
<div class="main-content">
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

        <form id="workoutLogForm" action="${pageContext.request.contextPath}/StartExercises" method="post">
            <input type="hidden" name="workoutId" value="${workout.workoutId}">
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
                        <button type="submit" name="nextAction" value="previousExercise" class="btn btn-secondary">
                            <i class="fas fa-arrow-left"></i> Previous Exercise
                        </button>
                    </c:otherwise>
                </c:choose>

                <c:choose>
                    <c:when test="${isLastExercise}">
                        <button type="submit" name="nextAction" value="finishWorkout" class="btn btn-success">
                            <i class="fas fa-check"></i> Finish Workout
                        </button>
                    </c:when>
                    <c:otherwise>
                        <button type="submit" name="nextAction" value="nextExercise" class="btn btn-primary">
                            Next Exercise <i class="fas fa-arrow-right"></i>
                        </button>
                    </c:otherwise>
                </c:choose>
            </div>
        </form>
    </div>
</div>

<script>
    // Input validation
    document.querySelectorAll('input[type="number"]').forEach(input => {
        input.addEventListener('input', function() {
            if (this.value < 0) this.value = 0;
        });
    });

    // Form submission validation
    document.getElementById('workoutLogForm').addEventListener('submit', function(e) {
        // Optional: Add client-side validation if needed
        // For example, ensure at least one set has data
    });
</script>
</body>
</html>