<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>Client Sessions</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/clientSession.css">
</head>
<body>
<div class="navbar-container">
  <jsp:include page="../common/verticalNavBar.jsp" />
</div>
<div class="main-container">
  <h1>Client Sessions</h1>
  <div class="session-container">
    <!-- Upcoming Sessions -->
    <div class="card">
      <div class="card-header">
        <h2>Upcoming Sessions</h2>
      </div>
      <div class="card-content">
        <p><span class="label">Date:</span> Monday, 4 October</p>
        <p><span class="label">Time:</span> 8:00 pm - 9:00 pm</p>
        <div class="actions">
          <button class="btn cancel">Cancel</button>
          <button class="btn reschedule">Reschedule</button>
        </div>
      </div>
    </div>

    <!-- Completed Sessions -->
    <div class="card">
      <div class="card-header">
        <h2>Completed Sessions</h2>
      </div>
      <div class="card-content">
        <p>Saturday, 2 October</p>
        <p>Friday, 1 October</p>
        <p>Wednesday, 30 September</p>
        <div class="actions">
          <button class="btn view-all">View All</button>
        </div>
      </div>
    </div>

    <!-- Your Package -->
    <div class="card">
      <div class="card-header">
        <h2>Your Package</h2>
      </div>
      <div class="card-content">
        <p>Platinum Membership - Gents - Annual</p>
        <div class="actions">
          <button class="btn book-now">Book Now</button>
          <button class="btn change-package">Change Package</button>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>
