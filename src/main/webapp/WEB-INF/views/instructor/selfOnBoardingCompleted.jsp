<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Instructor Onboarding - Completed</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/selfOnboarding.css">
  <style>
    .success-container {
      height: 100vh;
      display: flex;
      align-items: center;
      justify-content: center;
    }

    .success-card {
      max-width: 600px;
      text-align: center;
      padding: var(--spacing-3xl);
      background-color: var(--card-bg);
      border: 1px solid var(--success-color);
      box-shadow: 0 8px 24px rgba(0, 0, 0, 0.4);
    }

    .checkmark-circle {
      width: 100px;
      height: 100px;
      background-color: rgba(46, 125, 50, 0.2);
      border-radius: 50%;
      margin: 0 auto var(--spacing-xl);
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 3rem;
    }

    .success-title {
      color: var(--success-color);
      margin-bottom: var(--spacing-lg);
    }

    .success-text {
      font-size: var(--font-size-lg);
      margin-bottom: var(--spacing-xl);
      color: var(--text-color);
    }

    .instructor-name {
      font-weight: 600;
      font-size: var(--font-size-xl);
      margin-bottom: var(--spacing-lg);
    }

    .go-dashboard-btn {
      margin-top: var(--spacing-xl);
    }
  </style>
</head>
<body>
<div class="success-container">
  <div class="card success-card">
    <div class="checkmark-circle">
      ✓
    </div>
    <p class="instructor-name">Dear ${instructor.firstName},</p>
    <h2 class="success-title">🎉 You're all set!</h2>
    <p class="success-text">You have successfully completed the onboarding process.</p>
    <button onclick="window.location.href='${pageContext.request.contextPath}/instructorDashboard'" class="btn btn-success go-dashboard-btn">Go to Dashboard</button>
  </div>
</div>
</body>
</html>
