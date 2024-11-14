<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Instructor Onboarding - Basic Information</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/selfOnboarding.css">
</head>
<body>
<div class="container">
    <div class="header">
        <img src="${pageContext.request.contextPath}/images/LogoWhite.png" alt="Lifetime Fitness" class="logo">
        <div class="progress-steps">
            <div class="step active">
                <span class="step-number">1</span>
                <span class="step-text">Basic Information</span>
            </div>
            <div class="step">
                <span class="step-number">2</span>
                <span class="step-text">Professional Bio</span>
            </div>
            <div class="step">
                <span class="step-number">3</span>
                <span class="step-text">Availability</span>
            </div>
            <div class="step">
                <span class="step-number">4</span>
                <span class="step-text">Payment</span>
            </div>
        </div>
    </div>

    <div class="content-card">
        <h1>Basic Information</h1>
        <form id="basicInfoForm" action="${pageContext.request.contextPath}/instructor-onboarding/step1" method="post">
            <div class="form-section">
                <div class="photo-upload">
                    <div class="photo-preview" id="photoPreview">
                        <i class="upload-icon">📷</i>
                        <span>Upload Photo</span>
                    </div>
                    <input type="file" id="profilePhoto" name="profilePhoto" accept="image/*" hidden>
                </div>
            </div>

            <div class="form-section">
                <div class="form-group">
                    <label for="fullName">Full Name</label>
                    <input type="text" id="fullName" name="fullName" required>
                </div>

                <div class="form-group">
                    <label for="dateOfBirth">Date of Birth</label>
                    <input type="date" id="dateOfBirth" name="dateOfBirth" required>
                </div>
            </div>

            <div class="form-section">
                <div class="form-group">
                    <label for="email">Email Address</label>
                    <input type="email" id="email" name="email" required>
                </div>

                <div class="form-group">
                    <label for="phone">Phone Number</label>
                    <input type="tel" id="phone" name="phone" required>
                </div>
            </div>

            <div class="form-section emergency-contact">
                <h2>Emergency Contact</h2>
                <div class="form-group">
                    <label for="emergencyName">Contact Name</label>
                    <input type="text" id="emergencyName" name="emergencyName" required>
                </div>

                <div class="form-group">
                    <label for="emergencyRelation">Relationship</label>
                    <input type="text" id="emergencyRelation" name="emergencyRelation" required>
                </div>

                <div class="form-group">
                    <label for="emergencyPhone">Contact Phone</label>
                    <input type="tel" id="emergencyPhone" name="emergencyPhone" required>
                </div>
            </div>

            <div class="form-section">
                <div class="form-group full-width">
                    <label for="address">Address</label>
                    <textarea id="address" name="address" rows="3" required></textarea>
                </div>
            </div>

            <div class="form-actions">
                <button type="submit" class="next-button">Next</button>
            </div>
        </form>
    </div>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        const photoPreview = document.getElementById('photoPreview');
        const photoInput = document.getElementById('profilePhoto');

        photoPreview.addEventListener('click', function() {
            photoInput.click();
        });

        photoInput.addEventListener('change', function(e) {
            if (e.target.files && e.target.files[0]) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    photoPreview.style.backgroundImage = `url(${e.target.result})`;
                    photoPreview.innerHTML = '';
                }
                reader.readAsDataURL(e.target.files[0]);
            }
        });
    });
</script>
</body>
</html>