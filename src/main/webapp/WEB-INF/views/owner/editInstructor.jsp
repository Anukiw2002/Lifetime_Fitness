<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Instructor Profile | Lifetime Fitness</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/editInstructor.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
<jsp:include page="../common/verticalNavBar.jsp" />
<div class="main-content">
    <div class="container">
        <div class="card">
            <div class="profile-header">
                <h2>Edit Instructor Profile</h2>
                <div class="instructor-status" id="statusIndicator">
                    Active
                </div>
            </div>

            <div class="profile-image-section">
                <div class="profile-image">
                    <img src="${pageContext.request.contextPath}/images/profilePicAvatar.jpg" alt="Instructor" id="instructorImage">
                    <div class="image-upload">
                        <label for="uploadImage">
                            <i class="fas fa-camera"></i>
                        </label>
                        <input type="file" id="uploadImage" accept="image/*">
                    </div>
                </div>
            </div>

            <form id="instructorForm" class="card-body">
                <div class="form-group">
                    <label class="form-label" for="fullName">Full Name</label>
                    <input type="text" class="form-control" id="fullName" name="fullName" value="John Doe">
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label class="form-label" for="email">Email</label>
                        <input type="email" class="form-control" id="email" name="email" value="john.doe@example.com">
                    </div>

                    <div class="form-group">
                        <label class="form-label" for="phone">Phone Number</label>
                        <input type="tel" class="form-control" id="phone" name="phone" value="+94 77 123 4567">
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label class="form-label" for="specialization">Specialization</label>
                        <input type="text" class="form-control" id="specialization" name="specialization" value="Strength Training">
                    </div>

                    <div class="form-group">
                        <label class="form-label" for="experience">Years of Experience</label>
                        <input type="number" class="form-control" id="experience" name="experience" value="5">
                    </div>
                </div>

                <div class="form-group">
                    <label class="form-label" for="availability">Weekly Availability (Hours)</label>
                    <input type="number" class="form-control" id="availability" name="availability" value="40">
                </div>

                <div class="form-group">
                    <label class="form-label" for="bio">Biography</label>
                    <textarea class="form-control" id="bio" name="bio" rows="4">Certified personal trainer with expertise in strength training and nutrition. Passionate about helping clients achieve their fitness goals.</textarea>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">Save Changes</button>
                    <button type="button" class="btn btn-danger" id="deactivateBtn">Deactivate Instructor</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    // Placeholder JavaScript for functionality
    document.getElementById('deactivateBtn').addEventListener('click', function() {
        const confirmed = confirm('Are you sure you want to deactivate this instructor?');
        if (confirmed) {
            // Here you'll add the backend integration later
            document.getElementById('statusIndicator').textContent = 'Inactive';
            this.textContent = 'Activate Instructor';
            this.classList.toggle('btn-danger');
            this.classList.toggle('btn-success');
        }
    });

    document.getElementById('instructorForm').addEventListener('submit', function(e) {
        e.preventDefault();
        // Here you'll add the form submission logic later
        alert('Changes saved successfully!');
    });
</script>
</body>
</html>