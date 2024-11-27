<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Upcoming Sessions</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/instructor/upcomingSessions.css">
</head>
<body>
<div class="flex">
    <!-- Side Navbar -->
    <div>
        <jsp:include page="clientVerticalNavbar.jsp" />
    </div>

    <!-- Main Content -->
    <div class="main-content">
        <div class="container">
            <h1 class="mb-4">Upcoming Sessions</h1>

            <!-- Today's Sessions -->
            <div class="date-section">
                <h2 class="mb-3">Today</h2>
                <div class="grid grid-auto-fit">
                    <div class="card session-card">
                        <div class="flex items-center">
                            <div class="profile-icon"></div>
                            <div>
                                <p class="session-name">Kavin Samarasinghe</p>
                                <p class="session-time"><i class="clock-icon"></i> 4:00 - 5:00 am</p>
                            </div>
                        </div>
                    </div>
                    <div class="card session-card">
                        <div class="flex items-center">
                            <div class="profile-icon"></div>
                            <div>
                                <p class="session-name">Thevindu Sureschandra</p>
                                <p class="session-time"><i class="clock-icon"></i> 6:00 - 7:00 am</p>
                            </div>
                        </div>
                    </div>
                    <div class="card session-card">
                        <div class="flex items-center">
                            <div class="profile-icon"></div>
                            <div>
                                <p class="session-name">Rochelle Boteju</p>
                                <p class="session-time"><i class="clock-icon"></i> 7:00 - 8:00 am</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <br>
            <br>

            <!-- Future Sessions -->
            <div class="date-section">
                <h2 class="mb-3">02-10-2024 (Sat)</h2>
                <div class="grid grid-auto-fit">
                    <div class="card session-card">
                        <div class="flex items-center">
                            <div class="profile-icon"></div>
                            <div>
                                <p class="session-name">Kavin Samarasinghe</p>
                                <p class="session-time"><i class="clock-icon"></i> 4:00 - 5:00 am</p>
                            </div>
                        </div>
                    </div>
                    <div class="card session-card">
                        <div class="flex items-center">
                            <div class="profile-icon"></div>
                            <div>
                                <p class="session-name">Thevindu Sureschandra</p>
                                <p class="session-time"><i class="clock-icon"></i> 6:00 - 7:00 am</p>
                            </div>
                        </div>
                    </div>
                    <div class="card session-card">
                        <div class="flex items-center">
                            <div class="profile-icon"></div>
                            <div>
                                <p class="session-name">Rochelle Boteju</p>
                                <p class="session-time"><i class="clock-icon"></i> 7:00 - 8:00 am</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>