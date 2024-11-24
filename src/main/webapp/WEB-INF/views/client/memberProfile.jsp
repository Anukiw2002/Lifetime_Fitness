
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>LIFETIME FITNESS - Member Profile</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/profilePage.css">
</head>
<body>
<nav id="navbar"></nav>

<div class="profile-container">
    <div class="profile-header">
        <div class="profile-picture">
            <img src="${pageContext.request.contextPath}/images/profilePicAvatar.jpg" alt="Profile Picture">
        </div>
        <h1 class="member-name">Mihindu Dharamasena</h1>
        <div class="membership-info">
            <h2 class="membership-type">Platinum Membership - Gents</h2>
            <p class="expiration-date">Expiration Date: 24/02/2024</p>
        </div>
    </div>

    <div class="member-stats">
        <div class="stat-item">
            <span class="label">Age:</span>
            <span class="value">25 years</span>
        </div>
        <div class="stat-item">
            <span class="label">Gender:</span>
            <span class="value">Male</span>
        </div>
        <div class="stat-item">
            <span class="label">BMI:</span>
            <span class="value">22.5</span>
        </div>
        <div class="stat-item">
            <span class="label">Weight:</span>
            <span class="value">12%</span>
        </div>
        <div class="stat-item">
            <span class="label">Height:</span>
            <span class="value">12%</span>
        </div>
    </div>

    <div class="fitness-metrics">
        <div class="metric-card">
            <h3>Gym Activity</h3>
            <p>Days Active This Month: 15</p>
            <p>Total Classes Attended: 45</p>
            <p>Workout Streak: 5 days</p>
        </div>
        <div class="metric-card">
            <h3>Fitness Goals</h3>
            <p>Target Weight: 75 kg</p>
            <p>Weekly Goal: 4 sessions</p>
        </div>
        <div class="metric-card">
            <h3>Recent Activities</h3>
            <ul class="activity-list">
                <li>Cardio Session - 45 mins</li>
                <li>Weight Training - Upper Body</li>
            </ul>
        </div>
    </div>

    <div class="action-buttons">
        <button class="edit-profile" onclick="location.href='${pageContext.request.contextPath}/editProfile'">Edit Profile</button>
        <button class="view-schedule" onclick="location.href='${pageContext.request.contextPath}/clientSessions'">View Schedule</button>
        <button class="book-class" onclick="location.href='${pageContext.request.contextPath}/calendar'">Book Class</button>
    </div>
</div>
</body>
</html>
<%@ include file="clientVerticalNavbar.jsp" %>