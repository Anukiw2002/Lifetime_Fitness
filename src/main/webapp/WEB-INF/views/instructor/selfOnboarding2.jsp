<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Instructor Onboarding - Professional Bio</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/selfOnboarding.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/library/button.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/library/typography.css">
</head>
<body>
<jsp:include page="../common/verticalNavBar.jsp" />
<div class="main-content">
    <div class="container">
        <div class="header">
            <div class="progress-steps">
                <div class="step completed">
                    <div class="step-number">1</div>
                    <span>Basic Information</span>
                </div>
                <div class="step-line2"></div>
                <div class="step active">
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
        </div>

        <div class="content-card">
            <h1>Professional Bio</h1>
            <h3>Tell us about your fitness journey and experience.</h3>
            <form id="professionalBioForm" action="${pageContext.request.contextPath}/selfOnboarding/step3" method="post">
                <div class="form-section">
                    <div class="form-group">
                        <label>Certifications</label>
                        <div id="certifications-container">
                            <div class="certification-entry">
                                <input type="text" name="certificationName[]" placeholder="Certification Name">
                                <input type="text" name="certificationProvider[]" placeholder="Issuing Organization">
                                <input type="month" name="certificationDate[]">
                                <button type="button" class="remove-btn" onclick="removeCertification(this)">×</button>
                            </div>
                        </div>
                        <button type="button" class="add-btn" onclick="addCertification()">+ Add Another Certification</button>
                    </div>
                </div>

                <div class="form-group">
                    <label for="experience">Years of Experience</label>
                    <input type="number" id="experience" name="experience" min="0" required>
                </div>

                <div class="form-group">
                    <label>Areas of Expertise</label>
                    <div class="checkbox-group">
                        <label class="checkbox-label">
                            <input type="checkbox" name="expertise[]" value="weight-loss">
                            Weight Loss
                        </label>
                        <label class="checkbox-label">
                            <input type="checkbox" name="expertise[]" value="strength">
                            Strength Training
                        </label>
                        <label class="checkbox-label">
                            <input type="checkbox" name="expertise[]" value="rehabilitation">
                            Rehabilitation
                        </label>
                        <label class="checkbox-label">
                            <input type="checkbox" name="expertise[]" value="senior">
                            Senior Fitness
                        </label>
                    </div>
                </div>

                <div class="button-group">
                    <button type="button" class="back-button" onclick="window.location.href='/selfOnboarding/step2'">Back</button>
                    <button type="submit" class="next-button">Continue</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    function addCertification() {
        const container = document.getElementById('certifications-container');
        const newEntry = container.firstElementChild.cloneNode(true);
        const inputs = newEntry.querySelectorAll('input');
        inputs.forEach(input => input.value = '');
        container.appendChild(newEntry);
    }

    function removeCertification(button) {
        const container = document.getElementById('certifications-container');
        if (container.children.length > 1) {
            button.closest('.certification-entry').remove();
        }
    }

    document.addEventListener('DOMContentLoaded', function() {
        if (document.getElementById('photoPreview') && document.getElementById('profilePhoto')) {
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
        }
    });
</script>
</body>
</html>