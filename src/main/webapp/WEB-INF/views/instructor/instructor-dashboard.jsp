<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gym Management - Instructor Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/instructorDashboard.css">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.7.0/chart.min.js"></script>
</head>
<body>
<jsp:include page="instructorVerticalNavbar.jsp" />

<div class="main-content">
    <div class="dashboard-container">
        <div class="dashboard-header">
            <h1>Gym Overview</h1>
            <p class="text-muted">Today's Activity Summary</p>
        </div>

        <div class="stats-grid">
            <div class="stat-card">
                <div class="stat-icon">
                    <i class="fas fa-users-line fa-lg" style="color: var(--primary-color)"></i>
                </div>
                <div>
                    <h3>${ActiveMembers}</h3>
                    <p class="text-muted">Active Members</p>
                </div>
            </div>
            <div class="stat-card">
                <div class="stat-icon">
                    <i class="fas fa-dumbbell fa-lg" style="color: var(--success-color)"></i>
                </div>
                <div>
                    <h3>${countWorkout}</h3>
                    <p class="text-muted">Today's Workouts</p>
                </div>
            </div>
            <div class="stat-card">
                <div class="stat-icon">
                    <i class="fas fa-chart-line fa-lg" style="color: var(--warning-color)"></i>
                </div>
                <div>
                    <h3>${reportCount}</h3>
                    <p class="text-muted">Total reports</p>
                </div>
            </div>
        </div>

        <div class="content-grid">
            <!-- Store the data in a hidden div for JavaScript to access -->
            <div id="workout-data"
                 data-today="${todayCount}"
                 data-yesterday="${yesterdayCount}"
                 data-tomorrow="${tomorrowCount}"
                 style="display: none;"></div>

            <!-- Chart container with proper dimensions -->
            <div style="height: 500px; width: 100%;">
                <canvas id="workoutTrendChart"></canvas>
            </div>

            <div class="upcoming-sessions">
                <h2>Upcoming Sessions</h2>
                <div style="margin-top: 1rem;">
                    <div class="session-card">
                        <div class="session-header">
                            <h3>HIIT Training</h3>
                            <span class="session-time">9:00 AM</span>
                        </div>
                        <p class="text-muted">Client: John Doe</p>
                        <p class="text-muted">Phone: +94 77 123 4567</p>
                    </div>
                    <div class="session-card">
                        <div class="session-header">
                            <h3>Strength Training</h3>
                            <span class="session-time">10:30 AM</span>
                        </div>
                        <p class="text-muted">Client: Jane Smith</p>
                        <p class="text-muted">Phone: +94 77 987 6543</p>
                    </div>
                    <div class="session-card">
                        <div class="session-header">
                            <h3>Cardio Session</h3>
                            <span class="session-time">2:00 PM</span>
                        </div>
                        <p class="text-muted">Client: Mike Johnson</p>
                        <p class="text-muted">Phone: +94 77 456 7890</p>
                    </div>
                </div>
            </div>
        </div>

        <div class="activity-feed">
            <h2>Recent Activity</h2>
            <div class="activity-list">
                <div class="activity-item">
                    <div class="activity-icon">
                        <i class="fas fa-plus"></i>
                    </div>
                    <div>
                        <h4>New Workout Created</h4>
                        <p class="text-muted">Upper Body Strength • Client: #1234 • 10 mins ago</p>
                    </div>
                </div>
                <div class="activity-item">
                    <div class="activity-icon">
                        <i class="fas fa-user-check"></i>
                    </div>
                    <div>
                        <h4>Member Check-in</h4>
                        <p class="text-muted">Client: #5678 • 15 mins ago</p>
                    </div>
                </div>
                <div class="activity-item">
                    <div class="activity-icon">
                        <i class="fas fa-clipboard-check"></i>
                    </div>
                    <div>
                        <h4>Workout Completed</h4>
                        <p class="text-muted">HIIT Training • Client: #9012 • 30 mins ago</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/instructorDashboard.js"></script>

</body>
</html>