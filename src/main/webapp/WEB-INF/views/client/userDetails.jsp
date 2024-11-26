<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Member Profile</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/userDetails.css">
</head>
<body>
<jsp:include page="../common/verticalNavBar.jsp" />
<div class="main-content">
  <div class="container">
    <div class="grid details-grid">
      <!-- Personal Details Card -->
      <div class="card details-card">
        <h3>Personal Details</h3>
        <div class="details-row">
          <span class="details-label">Name</span>
          <span class="details-value" id="userName">${userName}</span>
        </div>
        <div class="details-row">
          <span class="details-label">E-mail</span>
          <span class="details-value" id="userEmail">${userEmail}</span>
        </div>
        <div class="details-row">
          <span class="details-label">City</span>
          <span class="details-value" id="userCity">${userCity}</span>
        </div>
        <div class="details-row">
          <span class="details-label">T.P Number</span>
          <span class="details-value" id="userTP">${userTP}</span>
        </div>
        <div class="details-row">
          <span class="details-label">Gender</span>
          <span class="details-value" id="userGender">${userGender}</span>
        </div>
        <div class="details-row">
          <span class="details-label">Age</span>
          <span class="details-value" id="userAge">${userAge}</span>
        </div>
      </div>

      <!-- Health Details Card -->
      <div class="card details-card">
        <h3>Health Metrics</h3>
        <div class="details-row">
          <span class="details-label">Weight</span>
          <span class="details-value" id="bodyWeight">${bodyWeight}</span>
        </div>
        <div class="details-row">
          <span class="details-label">Height</span>
          <span class="details-value" id="bodyHeight">${bodyHeight}</span>
        </div>
        <div class="details-row">
          <span class="details-label">Blood Pressure</span>
          <span class="details-value" id="bodyBP">${bodyBP}</span>
        </div>
        <div class="details-row">
          <span class="details-label">Fitness Test</span>
          <span class="details-value" id="fitnessTest">${fitnessTest}</span>
        </div>
        <div class="details-row">
          <span class="details-label">BMI</span>
          <span class="details-value" id="bodyBMI">${bodyBMI}</span>
        </div>
      </div>

      <!-- Membership Details Card -->
      <div class="card details-card">
        <h3>Membership Status</h3>
        <div class="details-row">
          <span class="details-label">Gym Plan</span>
          <span class="details-value" id="gymPlan">${gymPlan}</span>
        </div>
        <div class="details-row">
          <span class="details-label">Last Visited</span>
          <span class="details-value" id="lastVisited">${lastVisited}</span>
        </div>
        <div class="details-row">
          <span class="details-label">Last Paid</span>
          <span class="details-value" id="lastPaid">${lastPaid}</span>
        </div>
        <div class="details-row">
          <span class="details-label">Pay Date</span>
          <span class="details-value" id="payDate">${payDate}</span>
        </div>
        <div class="details-row">
          <span class="details-label">Started Date</span>
          <span class="details-value" id="startDate">${startDate}</span>
        </div>
        <div class="details-row">
          <span class="details-label">End Date</span>
          <span class="details-value" id="endDate">${endDate}</span>
        </div>
      </div>
    </div>
  </div>
</div>

</body>
</html>