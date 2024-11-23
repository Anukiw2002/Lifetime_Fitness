<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Upcoming Sessions</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/upcomingSessions.css">
</head>
<body>
<div class="page-layout">
    <!-- Side Navbar -->
    <div class="navbar-container">
        <jsp:include page="../common/verticalNavBar.jsp" />
    </div>

    <!-- Upcoming Sessions Section -->
    <div class="main-container">
        <div class="container">
            <h1>Upcoming Sessions</h1>

            <!-- Today's Sessions -->
            <div class="date-group">
                <h2>Today</h2>
                <div class="session-cards">
                    <div class="card">
                        <div class="profile-icon"></div>
                        <div class="details">
                            <p class="name">Kavin Samarasinghe</p>
                            <p class="time"><i class="clock-icon"></i> 4:00 - 5:00 am</p>
                        </div>
                    </div>
                    <div class="card">
                        <div class="profile-icon"></div>
                        <div class="details">
                            <p class="name">Thevindu Sureschandra</p>
                            <p class="time"><i class="clock-icon"></i> 6:00 - 7:00 am</p>
                        </div>
                    </div>
                    <div class="card">
                        <div class="profile-icon"></div>
                        <div class="details">
                            <p class="name">Rochelle Boteju</p>
                            <p class="time"><i class="clock-icon"></i> 7:00 - 8:00 am</p>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Future Sessions -->
            <div class="date-group">
                <h2>02-10-2024 (Sat)</h2>
                <div class="session-cards">
                    <div class="card">
                        <div class="profile-icon"></div>
                        <div class="details">
                            <p class="name">Kavin Samarasinghe</p>
                            <p class="time"><i class="clock-icon"></i> 4:00 - 5:00 am</p>
                        </div>
                    </div>
                    <div class="card">
                        <div class="profile-icon"></div>
                        <div class="details">
                            <p class="name">Thevindu Sureschandra</p>
                            <p class="time"><i class="clock-icon"></i> 6:00 - 7:00 am</p>
                        </div>
                    </div>
                    <div class="card">
                        <div class="profile-icon"></div>
                        <div class="details">
                            <p class="name">Rochelle Boteju</p>
                            <p class="time"><i class="clock-icon"></i> 7:00 - 8:00 am</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
