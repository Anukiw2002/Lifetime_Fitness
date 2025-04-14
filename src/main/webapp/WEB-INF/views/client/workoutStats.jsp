<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Workout Summary Stats</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/workoutStats.css">

</head>
<body>
<jsp:include page="../client/clientVerticalNavbar.jsp" />
<div class="main-content">
    <!-- Workout Stats Container -->
    <div class="workout-stats-container">
        <div class="stats-header">
            <h2>Workout Summary</h2>
            <p class="text-muted">Great job completing your workout!</p>
        </div>

        <div class="stats-grid">
            <!-- Duration Card -->
            <div class="stat-card">
                <div class="stat-title">Workout Duration</div>
                <div class="stat-value">
                    <span id="durationValue"> ${duration} </span>
                    <span class="stat-unit">minutes</span>
                </div>
            </div>

            <!-- Total Reps Card -->
            <div class="stat-card">
                <div class="stat-title">Total Repetitions</div>
                <div class="stat-value">
                    <span id="totalRepsValue">${stats.totalReps}</span>
                    <span class="stat-unit">reps</span>
                </div>
            </div>

            <!-- Total Weight Card -->
            <div class="stat-card">
                <div class="stat-title">Total Weight Lifted</div>
                <div class="stat-value">
                    <span id="totalWeightValue">${stats.totalWeight}</span>
                    <span class="stat-unit">kg</span>
                </div>
            </div>

            <!-- Average Weight Per Set Card -->
            <div class="stat-card">
                <div class="stat-title">Average Weight Per Set</div>
                <div class="stat-value">
                    <span id="avgWeightValue">${(stats.totalWeight / stats.totalSets)}</span>
                    <span class="stat-unit">kg</span>
                </div>
            </div>
        </div>

        <!-- Personal Bests Section -->
        <div class="personal-bests">
            <h3>New Personal Bests <i data-lucide="trophy" class="inline-icon"></i></h3>
            <c:choose>
                <c:when test="${not empty personalBests}">
                    <div class="pb-grid">
                        <c:forEach var="pb" items="${personalBests}">
                            <div class="pb-card">
                                <div class="pb-exercise">${pb.exerciseName}</div>
                                <div class="stat-value">
                                    <span id="pbValue">${pb.weight}</span>
                                    <span class="stat-unit">kg</span>
                                    <span class="stat-reps">(${pb.reps} reps)</span>
                                </div>
                                <c:if test="${pb.isFirstTime}">
                                    <div class="first-time-badge">First Time</div>
                                </c:if>
                            </div>
                        </c:forEach>
                    </div>
                </c:when>
                <c:otherwise>
                    <p class="text-muted">No new personal bests in this workout. Keep pushing!</p>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- Workout Summary -->
        <div class="workout-summary">
            <h3>Overall Performance</h3>
            <p class="text-muted">You've made progress in strength and endurance!</p>
        </div>

        <!-- Action Buttons -->
        <div class="action-buttons">
            <button class="btn btn-secondary" onclick="shareWorkout()">
                Share Workout
            </button>
            <button class="btn btn-primary" onclick="finishWorkout()">
                Complete Workout
            </button>
        </div>
    </div>
</div>

<script>
    // Function to format numbers with commas
    function formatNumber(num) {
        return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    }

    // Function to share workout (placeholder)
    function shareWorkout() {
        // Implement sharing functionality
        console.log('Sharing workout...');
    }

    // Function to finish workout (placeholder)
    function finishWorkout() {
        // Implement workout completion logic
        console.log('Completing workout...');
        // Redirect to the client workout view
        window.location.href = 'http://localhost:8080/clientWorkoutView';
    }

    // Update stats with actual values from your backend
    function updateStats(stats) {
        // Example stats update
        document.getElementById('durationValue').textContent = stats.duration || '45';
        document.getElementById('totalRepsValue').textContent = formatNumber(stats.totalReps || 124);
        document.getElementById('totalWeightValue').textContent = formatNumber(stats.totalWeight || 2450);
        document.getElementById('avgWeightValue').textContent = stats.avgWeight || '61.25';
    }

    // Call this function when the page loads with actual data
    // updateStats({
    //     duration: 45,
    //     totalReps: 124,
    //     totalWeight: 2450,
    //     avgWeight: 61.25
    // });
</script>
<script src="https://unpkg.com/lucide@latest"></script>
<script>
    lucide.createIcons();
</script>
</body>
</html>