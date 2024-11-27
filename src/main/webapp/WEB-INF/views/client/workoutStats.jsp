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
                    <span id="durationValue">45</span>
                    <span class="stat-unit">minutes</span>
                </div>
            </div>

            <!-- Total Reps Card -->
            <div class="stat-card">
                <div class="stat-title">Total Repetitions</div>
                <div class="stat-value">
                    <span id="totalRepsValue">124</span>
                    <span class="stat-unit">reps</span>
                </div>
            </div>

            <!-- Total Weight Card -->
            <div class="stat-card">
                <div class="stat-title">Total Weight Lifted</div>
                <div class="stat-value">
                    <span id="totalWeightValue">2,450</span>
                    <span class="stat-unit">kg</span>
                </div>
            </div>

            <!-- Average Weight Per Set Card -->
            <div class="stat-card">
                <div class="stat-title">Average Weight Per Set</div>
                <div class="stat-value">
                    <span id="avgWeightValue">61.25</span>
                    <span class="stat-unit">kg</span>
                </div>
            </div>
        </div>

        <!-- Personal Bests Section -->
        <div class="personal-bests">
            <h3>New Personal Bests <i data-lucide="trophy" class="inline-icon"></i></h3>
            <div class="pb-grid">
                <div class="pb-card">
                    <div class="pb-exercise">Bench Press</div>
                    <div class="stat-value">
                        <span id="benchPbValue">85</span>
                        <span class="stat-unit">kg</span>
                    </div>
                </div>
                <div class="pb-card">
                    <div class="pb-exercise">Squat</div>
                    <div class="stat-value">
                        <span id="squatPbValue">120</span>
                        <span class="stat-unit">kg</span>
                    </div>
                </div>
                <!-- Add more PB cards as needed -->
            </div>
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
        // Redirect to workout history or dashboard
        // window.location.href = 'workoutHistory.jsp';
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