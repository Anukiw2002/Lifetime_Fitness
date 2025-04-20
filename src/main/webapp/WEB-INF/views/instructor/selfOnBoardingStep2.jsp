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
<div class="container">
    <div class="progress-steps">
        <div class="step completed">
            <div class="step-number">1</div>
            <span>Reset Password</span>
        </div>
        <div class="step-line1"></div>
        <div class="step active">
            <div class="step-number">2</div>
            <span>Basic Information</span>
        </div>
        <div class="step-line2"></div>
        <div class="step">
            <div class="step-number">3</div>
            <span>Professional Bio</span>
        </div>
    </div>

    <div class="card">
        <h1 class="text-center mb-4">Basic Information</h1>
        <form id="basicInfoForm" action="${pageContext.request.contextPath}/selfOnBoarding/step2" method="post" enctype="multipart/form-data" >
            <div class="photo-upload">
                <label for="profilePicture" class="photo-preview" id="photoPreview">
                    <div id="defaultView">
                        <svg class="camera-icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <path d="M23 19a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h4l2-3h6l2 3h4a2 2 0 0 1 2 2z"></path>
                            <circle cx="12" cy="13" r="4"></circle>
                        </svg>
                        <span>Upload Photo</span>
                    </div>
                    <img id="previewImg" style="display: none;" src="" alt="Profile Preview">
                </label>
                <input type="file" id="profilePicture" name="profilePicture" style="display: none;" accept="image/*" onchange="previewImage(this);">
            </div>
            <br>
            <br>
            <br>
            <div class="grid grid-3 gap-lg">
                <div class="form-group">
                    <label class="form-label" for="firstName">First Name</label>
                    <input type="text" id="firstName" name="firstName" class="form-control" value="${instructor.firstName}" required>
                </div>

                <div class="form-group">
                    <label class="form-label" for="surname">Surname</label>
                    <input type="text" id="surname" name="surname" class="form-control" value="${instructor.surname}" required>
                </div>

                <div class="form-group">
                    <label class="form-label" for="email">Email Address</label>
                    <input type="email" id="email" name="email" class="form-control" value="${instructor.email}" required>
                </div>
            </div>

            <div class="grid grid-3 gap-lg">
                <div class="form-group">
                    <label class="form-label" for="dateOfBirth">Date of Birth</label>
                    <input type="date" id="dateOfBirth" name="dateOfBirth" class="form-control" required>
                </div>

                <div class="form-group">
                    <label class="form-label" for="phoneNumber">Phone Number</label>
                    <input type="tel" id="phoneNumber" name="phoneNumber" class="form-control" required>
                </div>

                <div class="form-group">
                    <label class="form-label" for="nic">NIC number</label>
                    <input type="text" id="nic" name="nic" class="form-control" required>
                </div>
            </div>

            <h2 class="mb-3">Address</h2>
            <div class="grid grid-3 gap-lg">
                <div class="form-group">
                    <label class="form-label" for="houseNumber">House Number</label>
                    <input type="text" id="houseNumber" name="houseNumber" class="form-control" required>
                </div>

                <div class="form-group">
                    <label class="form-label" for="streetName">Street Name</label>
                    <input type="text" id="streetName" name="streetName" class="form-control" required>
                </div>

                <div class="form-group">
                    <label class="form-label" for="city">City</label>
                    <input type="text" id="city" name="city" class="form-control" required>
                </div>
            </div>

            <div class="card emergency-contact mt-4">
                <h2 class="mb-3">Emergency Contact</h2>
                <div class="grid grid-3 gap-lg">
                    <div class="form-group">
                        <label class="form-label" for="emergencyContactName">Contact Name</label>
                        <input type="text" id="emergencyContactName" name="emergencyContactName" class="form-control" required>
                    </div>

                    <div class="form-group">
                        <label class="form-label" for="emergencyContactRelationship">Relationship</label>
                        <input type="text" id="emergencyContactRelationship" name="emergencyContactRelationship" class="form-control" required>
                    </div>

                    <div class="form-group">
                        <label class="form-label" for="emergencyContactNumber">Contact Phone</label>
                        <input type="tel" id="emergencyContactNumber" name="emergencyContactNumber" class="form-control" required>
                    </div>
                </div>
            </div>

            <div class="flex justify-end mt-4">
                <button type="submit" class="btn btn-primary">Next</button>
            </div>
        </form>
    </div>
</div>

<script>
    function previewImage(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function(e) {
                var previewImg = document.getElementById('previewImg');
                previewImg.src = e.target.result;
                previewImg.style.display = 'block';
                document.getElementById('defaultView').style.display = 'none';
            }
            reader.readAsDataURL(input.files[0]);
        }
    }
</script>
</body>
</html>