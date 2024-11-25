<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>Client Sessions</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/clientSession.css">

</head>
<body>
<div class="main-content">
  <jsp:include page="../common/verticalNavBar.jsp" />

  <div class="container">
    <h1 class="text-center mb-4">Client Sessions</h1>

    <div class="grid grid-3">
      <!-- Upcoming Sessions -->
      <div class="card">
        <div class="card-header">
          <h2 class="mb-0">Upcoming Sessions</h2>
        </div>
        <div class="card-body">
          <div class="session-date">Monday, 4 October</div>
          <div class="session-time">8:00 pm - 9:00 pm</div>
          <div class="flex gap-md justify-center mt-3">
            <button class="btn btn-danger">Cancel</button>
            <button class="btn btn-secondary">Reschedule</button>
          </div>
        </div>
      </div>

      <!-- Completed Sessions -->
      <div class="card">
        <div class="card-header">
          <h2 class="mb-0">Completed Sessions</h2>
        </div>
        <div class="card-body">
          <div class="completed-session">Saturday, 2 October</div>
          <div class="completed-session">Friday, 1 October</div>
          <div class="completed-session">Wednesday, 30 September</div>
          <div class="flex justify-center mt-3">
            <button class="btn btn-secondary">View All</button>
          </div>
        </div>
      </div>

      <!-- Your Package -->
      <div class="card">
        <div class="card-header">
          <h2 class="mb-0">Your Package</h2>
        </div>
        <div class="card-body">
          <div class="package-name">Platinum Membership - Gents - Annual</div>
          <div class="flex flex-col gap-md">
            <button class="btn btn-primary">Book Now</button>
            <button class="btn btn-outline">Change Package</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>