<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <div class="grid grid-2 gap-lg mb-2">
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
            </div>

            <!-- Right Column -->
            <div>
                <div class="card mb-4">
                    <div class="card-header">
                        <h2><i class="fas fa-calendar-alt"></i> Upcoming Sessions</h2>
                    </div>
                    <div class="card-body">
                        <c:if test="${empty bookings}">
                            <p class="text-muted">No upcoming sessions for the day.</p>
                        </c:if>

                        <c:forEach var="booking" items="${bookings}" varStatus="status">
                            <c:if test="${status.index < 5}">
                                <div class="session-card">
                                    <div class="session-header">
                                        <h3>${booking.fname} ${booking.lname}</h3>
                                        <span class="session-time">
                                        <fmt:formatDate value="${booking.timeSlot}" pattern="hh:mm a" />
                                        </span>
                                    </div>
                                    <p class="text-muted">Phone: ${booking.phoneNumber}</p>
                                </div>
                            </c:if>
                        </c:forEach>

                        <div class="view-all-container">
                            <a href="${pageContext.request.contextPath}/viewBookings" class="btn btn-primary">
                                <i class="fas fa-list"></i> View All Sessions
                            </a>
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