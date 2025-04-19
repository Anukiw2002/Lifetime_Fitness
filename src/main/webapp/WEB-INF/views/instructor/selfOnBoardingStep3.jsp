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
    <div class="container">
        <div class="progress-steps">
            <div class="step completed">
                <div class="step-number">1</div>
                <span>Reset Password</span>
            </div>
            <div class="step-line1"></div>
            <div class="step completed">
                <div class="step-number">2</div>
                <span>Basic Information</span>
            </div>
            <div class="step-line2"></div>
            <div class="step active">
                <div class="step-number">3</div>
                <span>Professional Bio</span>
            </div>
        </div>

        <div class="card">
            <h1 class="text-center mb-4">Professional Bio</h1>
            <h3 class="text-center text-muted mb-4">Tell us about your fitness journey and experience.</h3>

            <form id="professionalBioForm" action="${pageContext.request.contextPath}/selfOnBoarding/step3" method="post">
                <div class="form-group mb-4">
                    <label class="form-label">Certifications</label>
                    <div id="certifications-container">
                        <div class="certification-entry">
                            <input type="text" name="certificationName[]" placeholder="Certification Name" class="form-control">
                            <input type="text" name="certificationProvider[]" placeholder="Issuing Organization" class="form-control">
                            <button type="button" class="remove-btn" onclick="removeCertification(this)">×</button>
                        </div>
                    </div>
                    <button type="button" class="add-btn" onclick="addCertification()">+ Add Another Certification</button>
                </div>

                <div class="flex justify-end mt-4">
                    <button type="submit" class="btn btn-primary">Submit</button>
                </div>
            </form>
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