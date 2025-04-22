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

    <form action="${pageContext.request.contextPath}/signup/step2" method="post">
      <div class="signup-form-group">
        <!-- Phone Number -->
        <div class="input-wrapper">
          <label class="form-label">Phone Number</label>
          <input type="tel" name="phoneNumber" id="phoneNumber"
                 placeholder="Enter your mobile number"
                 pattern="[0-9]{10}"
                 class="signup-input"
                 required>
        </div>

        <!-- Address Section -->
        <div class="address-section">
          <label class="form-label">Address</label>
          <div class="address-grid">
            <div class="input-wrapper">
              <input type="text" name="houseNumber" id="houseNumber"
                     placeholder="House No."
                     class="signup-input"
                     required>
            </div>
            <div class="input-wrapper">
              <input type="text" name="streetName" id="streetName"
                     placeholder="Street name"
                     class="signup-input"
                     required>
            </div>
          </div>
          <div class="input-wrapper">
            <input type="text" name="city" id="city"
                   placeholder="City"
                   class="signup-input"
                   required>
          </div>
        </div>

        <!-- Personal Details -->
        <div class="details-grid">
          <div class="input-wrapper">
            <label class="form-label">Gender</label>
            <select name="gender" id="gender" class="signup-select" required>
              <option value="" disabled selected>Select gender</option>
              <option value="Male">Male</option>
              <option value="Female">Female</option>
            </select>
          </div>

          <div class="input-wrapper">
            <label class="form-label">Date of Birth</label>
            <input type="date" name="dateOfBirth" id="dateOfBirth"
                   class="signup-input signup-date"
                   max="2010-12-31"
                   required>
          </div>
        </div>

        <!-- Additional Suggested Fields -->
        <div class="input-wrapper">
          <label class="form-label">Emergency Contact</label>
          <input type="tel" name="emergencyContact" id="emergencyContact"
                 placeholder="Emergency contact number"
                 pattern="[0-9]{10}"
                 class="signup-input"
                 required>
        </div>

        <div class="input-wrapper">
          <label class="form-label">Relationship to Emergency Contact</label>
          <input type="text" name="emergencyContactRelation" id="emergencyContactRelation"
                 placeholder="e.g., Parent, Spouse, Sibling"
                 class="signup-input"
                 required>
        </div>
      </div>

      <div class="signup-button-container">
        <button type="submit" class="signup-button">Save and continue</button>
      </div>
    </form>
  </div>
  <div class="signup-image-section" style="background-image: url('${pageContext.request.contextPath}/images/ClientSignUpFormImg.jpg')">
  </div>
</div>
<script>
  function validateForm() {
    const nicNumber = document.getElementById('nicNumber').value;
    const nicPattern = /^[0-9]{9}[vVxX]$|^[0-9]{12}$/;

    if (!nicPattern.test(nicNumber)) {
      alert('Please enter a valid NIC number.');
      return false;
    }
    return true;
  }
</script>
</body>
</html>