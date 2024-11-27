<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Exercise Log</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/workoutLogs.css">
</head>
<body>
<jsp:include page="../common/verticalNavBar.jsp" />
<div class="main-content">
    <!-- Workout Logs Container -->
    <div class="workout-logs-container">
        <div class="exercise-info">
            <h2 class="exercise-name">Exercise: Deadlift <span id="exerciseName">${exercise.name}</span></h2>
            <p class="text-muted">Track your sets, weights, and reps for this exercise</p>
        </div>

        <div class="set-grid">
            <!-- Set 1 -->
            <div class="set-card">
                <div class="set-header">Set 1</div>
                <div class="input-group">
                    <label for="weight1">Weight (kg)</label>
                    <input type="number" id="weight1" name="weight1" class="form-control" placeholder="0.0">
                </div>
                <div class="input-group">
                    <label for="reps1">Reps</label>
                    <input type="number" id="reps1" name="reps1" class="form-control" placeholder="0">
                </div>
            </div>

            <!-- Set 2 -->
            <div class="set-card">
                <div class="set-header">Set 2</div>
                <div class="input-group">
                    <label for="weight2">Weight (kg)</label>
                    <input type="number" id="weight2" name="weight2" class="form-control" placeholder="0.0">
                </div>
                <div class="input-group">
                    <label for="reps2">Reps</label>
                    <input type="number" id="reps2" name="reps2" class="form-control" placeholder="0">
                </div>
            </div>

            <!-- Set 3 -->
            <div class="set-card">
                <div class="set-header">Set 3</div>
                <div class="input-group">
                    <label for="weight3">Weight (kg)</label>
                    <input type="number" id="weight3" name="weight3" class="form-control" placeholder="0.0">
                </div>
                <div class="input-group">
                    <label for="reps3">Reps</label>
                    <input type="number" id="reps3" name="reps3" class="form-control" placeholder="0">
                </div>
            </div>
        </div>

        <!-- Notes Section -->
        <div class="notes-section">
            <label for="exerciseNotes" class="form-label">Notes</label>
            <textarea id="exerciseNotes" name="notes" class="form-control"
                      placeholder="Add any notes about this exercise (form, difficulty, etc.)"></textarea>
        </div>

        <!-- Navigation Buttons -->
        <div class="nav-buttons">
            <button type="button" class="btn btn-secondary" id="prevButton">
                Previous Exercise
            </button>
            <a href="/workoutOptionss?page=workoutStats" class="btn btn-danger" id="finishButton">
                FINISH
            </a>
            <a href="/workoutOptionss?page=workoutLogs" class="btn btn-primary" id="nextButton">
                Next Exercise
            </a>

        </div>
    </div>
</div>

<script>
    // Add event listeners for the navigation buttons
    document.getElementById('prevButton').addEventListener('click', function() {
        // Handle previous exercise navigation
        console.log('Navigate to previous exercise');
    });

    document.getElementById('nextButton').addEventListener('click', function() {
        // Handle next exercise navigation
        console.log('Navigate to next exercise');
    });

    // Add input validation for numbers
    document.querySelectorAll('input[type="number"]').forEach(input => {
        input.addEventListener('input', function() {
            if (this.value < 0) this.value = 0;
        });
    });
</script>
</body>
</html>