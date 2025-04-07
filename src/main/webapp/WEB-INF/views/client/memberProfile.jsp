<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>LIFETIME FITNESS - Member Profile</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/generalStyles.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/profilePage.css">
</head>
<body>
<nav id="navbar"></nav>

<div class="main-content">
    <div class="container">
        <div class="card mb-4">
            <div class="card-body text-center">
                <img src="${pageContext.request.contextPath}/images/profilePicAvatar.jpg"
                     alt="Profile Picture"
                     class="profile-image">
                <h1 class="mb-2">Mihindu Dharamasena</h1>
                <div class="text-muted mb-3">
                    <h2 class="mb-1">Platinum Membership - Gents</h2>
                    <p>Expiration Date: 24/02/2024</p>
                </div>
            </div>
        </div>

        <div class="grid stats-grid mb-4">
            <div class="flex flex-col items-center">
                <span class="text-muted">Age</span>
                <span class="text-lg">25 years</span>
            </div>
            <div class="flex flex-col items-center">
                <span class="text-muted">Gender</span>
                <span class="text-lg">Male</span>
            </div>
            <div class="flex flex-col items-center">
                <span class="text-muted">BMI</span>
                <span class="text-lg">22.5</span>
            </div>
            <div class="flex flex-col items-center">
                <span class="text-muted">Weight</span>
                <span class="text-lg">12%</span>
            </div>
            <div class="flex flex-col items-center">
                <span class="text-muted">Height</span>
                <span class="text-lg">12%</span>
            </div>
        </div>

        <div class="grid metric-grid gap-lg mb-4">
            <div class="card">
                <h3 class="mb-3">Gym Activity</h3>
                <p class="mb-2">Days Active This Month: 15</p>
                <p class="mb-2">Total Classes Attended: 45</p>
                <p>Workout Streak: 5 days</p>
            </div>

            <div class="card">
                <h3 class="mb-3">Fitness Goals</h3>
                <p class="mb-2">Target Weight: 75 kg</p>
                <p>Weekly Goal: 4 sessions</p>
            </div>

            <div class="card">
                <h3 class="mb-3">Recent Activities</h3>
                <ul class="activity-list">
                    <li>Cardio Session - 45 mins</li>
                    <li>Weight Training - Upper Body</li>
                </ul>
            </div>
        </div>

        <!-- Action Buttons -->
        <div class="flex justify-center gap-md">
            <button class="btn btn-secondary" onclick="location.href='${pageContext.request.contextPath}/clientEditProfile'">
                Edit Profile
            </button>
            <button class="btn btn-secondary" onclick="location.href='${pageContext.request.contextPath}/clientSessions'">
                View Schedule
            </button>
            <button class="btn btn-primary" onclick="location.href='${pageContext.request.contextPath}/bookSession'">
                Book Class
            </button>
        </div>
    </div>
</div>
</body>
</html>
<%@ include file="clientVerticalNavbar.jsp" %>