<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Instructor Onboarding - Basic Information</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/selfOnboarding.css">
</head>
<body>
<div class="main-content">
    <div class="container">
        <div class="progress-steps">
            <div class="step active">
                <div class="step-number">1</div>
                <span>Basic Information</span>
            </div>
            <div class="step-line2"></div>
            <div class="step">
                <div class="step-number">2</div>
                <span>Professional Bio</span>
            </div>
            <div class="step-line2"></div>
            <div class="step">
                <div class="step-number">3</div>
                <span>Availability and Schedule</span>
            </div>
            <div class="step-line2"></div>
            <div class="step">
                <div class="step-number">4</div>
                <span>Payment Preferences</span>
            </div>
        </div>

        <div class="card">
            <h1 class="text-center mb-4">Basic Information</h1>
            <form id="basicInfoForm" action="${pageContext.request.contextPath}/selfOnboarding/step2" method="post">
                <div class="photo-upload">
                    <div class="photo-preview" id="photoPreview">
                        <i class="upload-icon">📷</i>
                        <span>Upload Photo</span>
                    </div>
                    <input type="file" id="profilePhoto" name="profilePhoto" accept="image/*" hidden>
                </div>
                <br>
                <br>
                <br>
                <div class="grid grid-2 gap-lg">
                    <div class="form-group">
                        <label class="form-label" for="fullName">Full Name</label>
                        <input type="text" id="fullName" name="fullName" class="form-control" required>
                    </div>

                    <div class="form-group">
                        <label class="form-label" for="dateOfBirth">Date of Birth</label>
                        <input type="date" id="dateOfBirth" name="dateOfBirth" class="form-control" required>
                    </div>
                </div>

                <div class="grid grid-2 gap-lg">
                    <div class="form-group">
                        <label class="form-label" for="email">Email Address</label>
                        <input type="email" id="email" name="email" class="form-control" required>
                    </div>

                    <div class="form-group">
                        <label class="form-label" for="phone">Phone Number</label>
                        <input type="tel" id="phone" name="phone" class="form-control" required>
                    </div>
                </div>

                <div class="card emergency-contact mt-4">
                    <h2 class="mb-3">Emergency Contact</h2>
                    <div class="grid grid-3 gap-lg">
                        <div class="form-group">
                            <label class="form-label" for="emergencyName">Contact Name</label>
                            <input type="text" id="emergencyName" name="emergencyName" class="form-control" required>
                        </div>

                        <div class="form-group">
                            <label class="form-label" for="emergencyRelation">Relationship</label>
                            <input type="text" id="emergencyRelation" name="emergencyRelation" class="form-control" required>
                        </div>

                        <div class="form-group">
                            <label class="form-label" for="emergencyPhone">Contact Phone</label>
                            <input type="tel" id="emergencyPhone" name="emergencyPhone" class="form-control" required>
                        </div>
                    </div>
                </div>

                <div class="form-group mt-4">
                    <label class="form-label" for="address">Address</label>
                    <textarea id="address" name="address" class="form-control" rows="3" required></textarea>
                </div>

                <div class="flex justify-end mt-4">
                    <button type="submit" class="btn btn-primary">Next</button>
                </div>
            </form>
        </div>
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