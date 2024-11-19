<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Responsive Details Cards</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/userDetails.css">
</head>
<body>

<div class="container">
  <div class="card">
    <h3>User details</h3>
    <p>Name: <span id="userName">${userName}</span></p>
    <p>E-mail: <span id="userEmail">${userEmail}</span></p>
    <p>City: <span id="userCity">${userCity}</span></p>
    <p>T.P number: <span id="userTP">${userTP}</span></p>
    <p>Gender: <span id="userGender">${userGender}</span></p>
    <p>Age: <span id="userAge">${userAge}</span></p>
  </div>

  <div class="card">
    <h3>Body details</h3>
    <p>Weight: <span id="bodyWeight">${bodyWeight}</span></p>
    <p>Height: <span id="bodyHeight">${bodyHeight}</span></p>
    <p>Blood pressure: <span id="bodyBP">${bodyBP}</span></p>
    <p>Fitness test: <span id="fitnessTest">${fitnessTest}</span></p>
    <p>BMI: <span id="bodyBMI">${bodyBMI}</span></p>
  </div>

  <div class="card">
    <h3>Gym details</h3>
    <p>Gym plan: <span id="gymPlan">${gymPlan}</span></p>
    <p>Last visited: <span id="lastVisited">${lastVisited}</span></p>
    <p>Last paid: <span id="lastPaid">${lastPaid}</span></p>
    <p>Pay date: <span id="payDate">${payDate}</span></p>
    <p>Started date: <span id="startDate">${startDate}</span></p>
    <p>End date: <span id="endDate">${endDate}</span></p>
  </div>
</div>

</body>
</html>
