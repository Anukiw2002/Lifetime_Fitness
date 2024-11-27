<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Instructor Onboarding - Professional Bio</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/selfOnboarding.css">
</head>
<body>
<div class="main-content">
    <div class="container">
        <div class="progress-steps">
            <div class="step completed">
                <div class="step-number">1</div>
                <span>Basic Information</span>
            </div>
            <div class="step-line1"></div>
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

        <div class="card">
            <h1 class="text-center mb-4">Professional Bio</h1>
            <h3 class="text-center text-muted mb-4">Tell us about your fitness journey and experience.</h3>

            <form id="professionalBioForm" action="${pageContext.request.contextPath}/selfOnboarding/step3" method="post">
                <div class="form-group mb-4">
                    <label class="form-label">Certifications</label>
                    <div id="certifications-container">
                        <div class="certification-entry">
                            <input type="text" name="certificationName[]" placeholder="Certification Name" class="form-control">
                            <input type="text" name="certificationProvider[]" placeholder="Issuing Organization" class="form-control">
                            <input type="month" name="certificationDate[]" class="form-control">
                            <button type="button" class="remove-btn" onclick="removeCertification(this)">×</button>
                        </div>
                    </div>
                    <button type="button" class="add-btn" onclick="addCertification()">+ Add Another Certification</button>
                </div>

                <div class="form-group mb-4">
                    <label class="form-label" for="experience">Years of Experience</label>
                    <input type="number" id="experience" name="experience" min="0" class="form-control" required>
                </div>

                <div class="form-group mb-4">
                    <label class="form-label">Areas of Expertise</label>
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

                <div class="flex justify-between mt-4">
                    <button type="button" class="btn btn-secondary" onclick="window.location.href='/selfOnboarding/step1'">Back</button>
                    <button type="submit" class="btn btn-primary">Continue</button>
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
</script>
</body>
</html>