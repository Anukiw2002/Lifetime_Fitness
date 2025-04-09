<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer Dashboard - Lifetime Fitness</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
    <style>
        .stat-card {
            background: rgba(255, 255, 255, 0.08);
            backdrop-filter: blur(10px);
            border: 1px solid rgba(255, 255, 255, 0.1);
            padding: 1.5rem;
            border-radius: 8px;
            transition: transform 0.2s;
        }

        .stat-card:hover {
            transform: translateY(-5px);
        }

        .upcoming-session {
            padding: 1rem;
            border-left: 4px solid var(--primary-color);
            margin-bottom: 1rem;
        }

        .announcement {
            padding: 1rem;
            border-radius: var(--border-radius);
            background: rgba(255, 255, 255, 0.05);
            margin-bottom: 1rem;
        }

        .weight-trend {
            height: 200px;
            background: rgba(255, 255, 255, 0.05);
            border-radius: var(--border-radius);
            margin-bottom: 1rem;
            display: flex;
            align-items: center;
            justify-content: center;
            color: var(--text-muted);
        }
    </style>
</head>
<body>
<jsp:include page="../client/clientVerticalNavbar.jsp" />
<div class="main-content">
    <div class="container">
        <!-- Welcome Section -->
        <div class="flex items-center justify-between mb-4">
            <h1>Welcome back, ${userName}!</h1>
            <p class="text-muted">December 15, 2024</p>
        </div>

        <!-- Stats Overview -->
        <div class="grid grid-4 gap-lg mb-4">
            <div class="stat-card">
                <h3>Workout Streak</h3>
                <div class="flex items-center justify-between">
                    <span class="text-2xl">7 days</span>
                    <span class="text-success">↑</span>
                </div>
            </div>
            <div class="stat-card">
                <h3>Current Weight</h3>
                <div class="flex items-center justify-between">
                    <span class="text-2xl">75 kg</span>
                    <span class="text-success">↓</span>
                </div>
            </div>
            <div class="stat-card">
                <h3>Total Workouts</h3>
                <div class="flex items-center justify-between">
                    <span class="text-2xl">48</span>
                    <span class="text-success">↑</span>
                </div>
            </div>
            <div class="stat-card">
                <h3>Goals Met</h3>
                <div class="flex items-center justify-between">
                    <span class="text-2xl">3/5</span>
                    <span class="text-success">60%</span>
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
                        <div class="weight-trend">
                            [Weight Trend Graph Placeholder]
                        </div>
                        <div class="flex justify-between">
                            <div>
                                <p class="text-muted">Starting Weight</p>
                                <p class="text-xl">82 kg</p>
                            </div>
                            <div>
                                <p class="text-muted">Current Weight</p>
                                <p class="text-xl">75 kg</p>
                            </div>
                            <div>
                                <p class="text-muted">Target Weight</p>
                                <p class="text-xl">70 kg</p>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="card">
                    <div class="card-header">
                        <h2>Upcoming Sessions</h2>
                    </div>
                    <div class="card-body">
                        <div class="upcoming-session">
                            <div class="flex justify-between items-center">
                                <h3>Upper Body Workout</h3>
                                <span class="text-muted">Today, 4:00 PM</span>
                            </div>
                            <p>Instructor: Alex Thompson</p>
                        </div>
                        <div class="upcoming-session">
                            <div class="flex justify-between items-center">
                                <h3>Leg Day</h3>
                                <span class="text-muted">Tomorrow, 5:30 PM</span>
                            </div>
                            <p>Instructor: Sarah Wilson</p>
                        </div>
                        <div class="upcoming-session">
                            <div class="flex justify-between items-center">
                                <h3>Core Strength</h3>
                                <span class="text-muted">Dec 18, 6:00 PM</span>
                            </div>
                            <p>Instructor: Mike Johnson</p>
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
                        <h3>Premium Membership</h3>
                        <p class="text-muted mb-4">Valid until: January 15, 2025</p>
                        <div class="flex justify-between mb-4">
                            <span>Sessions completed</span>
                            <span>12/20</span>
                        </div>
                        <div class="progress mb-4" style="height: 8px; background: rgba(255,255,255,0.1); border-radius: 4px;">
                            <div style="width: 60%; height: 100%; background: var(--primary-color); border-radius: 4px;"></div>
                        </div>
                        <button class="btn btn-primary w-full">View Plan Details</button>
                    </div>
                </div>

                <div class="card">
                    <div class="card-header">
                        <h2>Recent Announcements</h2>
                    </div>
                    <div class="card-body">
                        <c:forEach var="notifications" items="${notifications}">
                            <div class="notifications">
                                <div class="flex justify-between">
                                    <h4>${notifications.title}</h4>
                                    <span class="text-muted">${notifications.created_at}</span>
                                </div>
                                <p>${notifications.message}</p>
                            </div>
                        </c:forEach>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>