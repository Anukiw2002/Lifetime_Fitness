<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <div class="container">
        <!-- Welcome Section -->
        <div class="welcome-section">
            <div>
                <h1>Gym Overview</h1>
                <p class="text-muted">Today's Activity Summary</p>
            </div>
            <a href="${pageContext.request.contextPath}/instructorProfile" class="profile-badge">
                <c:choose>
                    <c:when test="${empty instructor.profilePictureBase64}">
                        <img id="profilePreview" src="${pageContext.request.contextPath}/images/profilePicAvatar.jpg" alt="Default Profile Picture">
                    </c:when>
                    <c:otherwise>
                        <img id="profilePreview" src="data:image/jpeg;base64,${instructor.profilePictureBase64}" alt="${instructor.firstName} ${instructor.surname}">
                    </c:otherwise>
                </c:choose>
            </a>
        </div>

        <!-- Stats Overview -->
        <div class="grid grid-3 gap-lg mb-3">
            <div class="stat-card">
                <div class="stat-icon">
                    <i class="fas fa-users"></i>
                </div>
                <div>
                    <h3>Active Members</h3>
                    <div class="flex items-center justify-between">
                        <span class="text-2xl">${ActiveMembers}</span>
                    </div>
                </div>
            </div>
            <div class="stat-card">
                <div class="stat-icon">
                    <i class="fas fa-dumbbell"></i>
                </div>
                <div>
                    <h3>Today's Workouts</h3>
                    <div class="flex items-center justify-between">
                        <span class="text-2xl">${countWorkout}</span>
                    </div>
                </div>
            </div>
            <div class="stat-card">
                <div class="stat-icon">
                    <i class="fas fa-chart-line"></i>
                </div>
                <div>
                    <h3>Total Reports</h3>
                    <div class="flex items-center justify-between">
                        <span class="text-2xl">${reportCount}</span>
                    </div>
                </div>
            </div>
        </div>

        <!-- Main Content Grid -->
        <div class="grid grid-2 gap-lg">
            <!-- Left Column -->
            <div>
                <div class="card mb-4">
                    <div class="card-header">
                        <h2><i class="fas fa-chart-line"></i> Workout Trend</h2>
                    </div>
                    <div class="card-body">
                        <!-- Store the data in a hidden div for JavaScript to access -->
                        <div id="workout-data"
                             data-today="${todayCount}"
                             data-yesterday="${yesterdayCount}"
                             data-tomorrow="${tomorrowCount}"
                             style="display: none;"></div>

                        <!-- Chart container -->
                        <div class="weight-trend">
                            <canvas id="workoutTrendChart"></canvas>
                        </div>
                    </div>
                </div>

                <div class="card mb-4">
                    <div class="card-header">
                        <h2><i class="fas fa-clock"></i> Recent Activity</h2>
                    </div>
                    <div class="card-body">
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

            <!-- Right Column -->
            <div>
                <div class="card mb-4">
                    <div class="card-header">
                        <h2><i class="fas fa-calendar-alt"></i> Upcoming Sessions</h2>
                    </div>
                    <div class="card-body">
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
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/instructorDashboard.js"></script>
</body>
</html>