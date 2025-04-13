<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer Dashboard - Lifetime Fitness</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/clientDashboard.css">
</head>
<body>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<jsp:include page="../client/clientVerticalNavbar.jsp" />
<div class="main-content">
    <div class="container">
        <!-- Welcome Section -->
        <div class="flex items-center justify-between mb-4">
            <h1>Welcome back, ${userName}!</h1>
            <p class="text-muted">${currentDay}/${currentMonth}/${currentYear}</p>
        </div>

        <!-- Stats Overview -->
        <div class="grid grid-3 gap-lg mb-3">
            <div class="stat-card">
                <h3>Workout Streak</h3>
                <div class="flex items-center justify-between">
                    <span class="text-2xl">${streak}</span>
                </div>
            </div>
            <div class="stat-card">
                <h3>Current Weight</h3>
                <div class="flex items-center justify-between">
                    <span class="text-2xl">${currentWeight}</span>
                </div>
            </div>
            <div class="stat-card">
                <h3>Total Workouts</h3>
                <div class="flex items-center justify-between">
                    <span class="text-2xl">${workoutCount}</span>
                </div>
            </div>

        </div>

        <!-- Main Content Grid -->
        <div class="grid grid-2 gap-lg">
            <!-- Left Column -->
            <div>
                <div class="card mb-4">
                    <div class="card-header">
                        <h2>Progress Overview</h2>
                    </div>
                    <div class="card-body">
                        <div id="weight-data"
                             data-beginning-weight="${beginningWeight}"
                             data-current-weight="${currentWeight}"
                             data-target-weight="${targetWeight}">
                        </div>

                        <div class="weight-trend">
                            <canvas id="weightTrendChart" width="400" height="200"></canvas>
                        </div>
                        <div class="flex justify-between">
                            <div>
                                <p class="text-muted">Starting Weight</p>
                                <p class="text-xl">${beginningWeight}</p>
                            </div>
                            <div>
                                <p class="text-muted">Current Weight</p>
                                <p class="text-xl">${currentWeight}</p>
                            </div>
                            <div>
                                <p class="text-muted">Target Weight</p>
                                <p class="text-xl">${targetWeight}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Right Column -->
            <div>
                <div class="card mb-4">
                    <div class="card-header">
                        <h2>Current Plan</h2>
                    </div>
                    <div class="card-body">
                        <h3>${membership[0].planName} Membership Plan</h3>
                        <fmt:parseDate value="${membership[0].endDate}" pattern="yyyy-MM-dd" var="parsedDate" />
                        <p class="text-muted mb-4">Valid until: <fmt:formatDate value="${parsedDate}" pattern="MMMM d, yyyy" /></p>
                        <button class="btn btn-primary w-full">Change Package</button>
                    </div>
                </div>
                <div>
                    <button class="btn btn-primary" onclick="location.href='${pageContext.request.contextPath}/bookSession'">
                        Book Class
                    </button>
                    <button class="btn btn-secondary" onclick="location.href='${pageContext.request.contextPath}/clientBookings'">
                        View Schedule
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/clientDashboard.js"></script>
</body>
</html>