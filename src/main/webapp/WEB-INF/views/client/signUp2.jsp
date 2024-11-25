<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Sign Up - Lifetime Fitness</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/signUP2.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/library/button.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/library/typography.css">
</head>
<body>
<div class="signup-container">
  <div class="signup-form-section">
    <img src="${pageContext.request.contextPath}/images/LogoWhite.png" alt="Lifetime Fitness" class="signup-logo">
    <h2 class="signup-heading" style="color: white;">Let's get you started</h2>


    <div class="progress-steps">
      <div class="step active">
        <div class="step-number">1</div>
        <span>General details</span>
      </div>
      <div class="step-line2"></div>
      <div class="step">
        <div class="step-number">2</div>
        <span>Medical History</span>
      </div>
      <div class="step-line2"></div>
      <div class="step">
        <div class="step-number">3</div>
        <span>Membership plan</span>
      </div>
    </div>

    <form action="${pageContext.request.contextPath}/medicalDetails2" method="GET">
      <div class="signup-form-group">
        <input type="tel" placeholder="Enter your phone number" class="signup-input" required>
        <input type="text" placeholder="Enter your address" class="signup-input" required>
        <select class="signup-select" required>
          <option value="" disabled selected>Gender</option>
          <option value="male">Male</option>
          <option value="female">Female</option>
          <option value="other">Other</option>
        </select>
        <input type="text" placeholder="NIC number" class="signup-input" required>
        <input type="date" placeholder="Birthday" class="signup-input signup-date" required>
      </div>
      <button type="submit" class="signup-button">Save and continue</button>
    </form>
  </div>
  <div class="signup-image-section" style="background-image: url('${pageContext.request.contextPath}/images/ClientSignUpFormImg.jpg')">
  </div>
</div>
</body>
</html>