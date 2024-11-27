<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Notification Details</title>

</head>
<body>
<jsp:include page="../common/verticalNavBar.jsp" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/detailedNotification.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">

<div class="main-content">
    <div class="container">
        <h1 class="mb-4">Notification Details</h1>

        <div class="notification-card card">
            <div class="notification-title">Upcoming Session Due</div>
            <div class="notification-description">
                You have an upcoming session scheduled on <strong>Monday, Nov 15, 2024</strong> at <strong>10:00 AM</strong>.
            </div>
            <div class="notification-time">3 days 10 hours ago</div>
        </div>

        <div class="notification-card card">
            <div class="notification-title">Upcoming Session Due</div>
            <div class="notification-description">
                You have an upcoming session scheduled on <strong>Wednesday, Nov 17, 2024</strong> at <strong>2:00 PM</strong>.
            </div>
            <div class="notification-time">4 days 15 hours ago</div>
        </div>

        <div class="notification-card card">
            <div class="notification-title">Upcoming Session Due</div>
            <div class="notification-description">
                You have an upcoming session scheduled on <strong>Friday, Nov 19, 2024</strong> at <strong>11:00 AM</strong>.
            </div>
            <div class="notification-time">7 days 2 hours ago</div>
        </div>

        <a href="/memberProfile" class="back-link">
            <span>&larr;</span>
            <span class="ml-2">Back to Home</span>
        </a>
    </div>
</div>
</body>
</html>