<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Sign Up - Lifetime Fitness</title>
  <style>
    * {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
      font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Arial, sans-serif;
    }

    body {
      background-color: #f5f5f5;
    }

    .container {
      display: flex;
      min-height: 100vh;
    }

    .form-section {
      flex: 1;
      background-color: #1a1a1a;
      color: white;
      padding: 40px;
      display: flex;
      flex-direction: column;
    }

    .image-section {
      flex: 1;
      background-size: cover;
      background-position: center;
      display: none;
    }

    .logo {
      width: 200px;
      margin-bottom: 40px;
    }

    h2 {
      font-size: 32px;
      margin-bottom: 30px;
    }

    .progress-steps {
      display: flex;
      align-items: center;
      margin-bottom: 40px;
    }

    .step {
      display: flex;
      align-items: center;
      color: #ffffff;
      font-size: 14px;
    }

    .step-number {
      width: 24px;
      height: 24px;
      border-radius: 50%;
      background-color: #333;
      color: white;
      display: flex;
      align-items: center;
      justify-content: center;
      margin-right: 8px;
      font-size: 12px;
    }

    .step.active .step-number {
      background-color: white;
      color: black;
    }

    .step-line {
      height: 1px;
      background-color: #333;
      width: 100px;
      margin: 0 15px;
    }

    .form-group {
      margin-bottom: 20px;
    }

    input, select {
      width: 100%;
      padding: 15px;
      border-radius: 8px;
      border: none;
      background-color: white;
      font-size: 16px;
      margin-bottom: 15px;
    }

    select {
      appearance: none;
      background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='24' height='24' viewBox='0 0 24 24' fill='none' stroke='%23000000' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3E%3Cpolyline points='6 9 12 15 18 9'%3E%3C/polyline%3E%3C/svg%3E");
      background-repeat: no-repeat;
      background-position: right 15px center;
      background-size: 16px;
    }

    input[type="date"]::-webkit-calendar-picker-indicator {
      background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='24' height='24' viewBox='0 0 24 24' fill='none' stroke='%23000000' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3E%3Crect x='3' y='4' width='18' height='18' rx='2' ry='2'%3E%3C/rect%3E%3Cline x1='16' y1='2' x2='16' y2='6'%3E%3C/line%3E%3Cline x1='8' y1='2' x2='8' y2='6'%3E%3C/line%3E%3Cline x1='3' y1='10' x2='21' y2='10'%3E%3C/line%3E%3C/svg%3E");
      background-size: 16px;
    }

    button {
      width: 100%;
      padding: 15px;
      border-radius: 8px;
      border: none;
      background-color: #8b0000;
      color: white;
      font-size: 16px;
      cursor: pointer;
      transition: background-color 0.3s;
    }

    button:hover {
      background-color: #a00000;
    }

    @media (min-width: 768px) {
      .image-section {
        display: block;
      }
    }
  </style>
</head>
<body>
<div class="container">
  <div class="form-section">
    <img src="${pageContext.request.contextPath}/images/LogoWhite.png" alt="Lifetime Fitness" class="logo">
    <h2>Let's get you started</h2>

    <div class="progress-steps">
      <div class="step active">
        <div class="step-number">1</div>
        <span>General details</span>
      </div>
      <div class="step-line"></div>
      <div class="step">
        <div class="step-number">2</div>
        <span>Medical History</span>
      </div>
      <div class="step-line"></div>
      <div class="step">
        <div class="step-number">3</div>
        <span>Membership plan</span>
      </div>
    </div>

    <form>
      <div class="form-group">
        <input type="tel" placeholder="Enter your phone number" required>
        <input type="text" placeholder="Enter your address" required>
        <select required>
          <option value="" disabled selected>Gender</option>
          <option value="male">Male</option>
          <option value="female">Female</option>
          <option value="other">Other</option>
        </select>
        <input type="text" placeholder="NIC number" required>
        <input type="date" placeholder="Birthday" required>
      </div>
      <button type="submit">Save and continue</button>
    </form>
  </div>
  <div class="image-section" style="background-image: url('${pageContext.request.contextPath}/images/ClientSignUpFormImg.jpg')">
  </div>
</div>
</body>
</html>