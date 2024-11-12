<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Instructor Profile | Lifetime Fitness</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/editInstructor.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
<jsp:include page="../common/verticalNavBar.jsp" />
<div class="main-content">
<div class="container">
    <div class="profile-header">
        <h1>Edit Instructor Profile</h1>
        <div class="instructor-status" id="statusIndicator">
            Active
        </div>
    </div>

    <div class="profile-content">
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

        <form id="instructorForm" class="edit-form">
            <div class="form-group">
                <label for="fullName">Full Name</label>
                <input type="text" id="fullName" name="fullName" value="John Doe">
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" value="john.doe@example.com">
                </div>

                <div class="form-group">
                    <label for="phone">Phone Number</label>
                    <input type="tel" id="phone" name="phone" value="+94 77 123 4567">
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="specialization">Specialization</label>
                    <input type="text" id="specialization" name="specialization" value="Strength Training">
                </div>

                <div class="form-group">
                    <label for="experience">Years of Experience</label>
                    <input type="number" id="experience" name="experience" value="5">
                </div>
            </div>

            <div class="form-group">
                <label for="availability">Weekly Availability (Hours)</label>
                <input type="number" id="availability" name="availability" value="40">
            </div>

            <div class="form-group">
                <label for="bio">Biography</label>
                <textarea id="bio" name="bio" rows="4">Certified personal trainer with expertise in strength training and nutrition. Passionate about helping clients achieve their fitness goals.</textarea>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn-save">Save Changes</button>
                <button type="button" class="btn-deactivate" id="deactivateBtn">Deactivate Instructor</button>
            </div>
        </form>
    </div>
</div>

<script >
    // Placeholder JavaScript for functionality
    document.getElementById('deactivateBtn').addEventListener('click', function() {
        const confirmed = confirm('Are you sure you want to deactivate this instructor?');
        if (confirmed) {
            // Here you'll add the backend integration later
            document.getElementById('statusIndicator').textContent = 'Inactive';
            this.textContent = 'Activate Instructor';
            this.classList.toggle('btn-deactivate');
            this.classList.toggle('btn-activate');
        }
    });

    document.getElementById('instructorForm').addEventListener('submit', function(e) {
        e.preventDefault();
        // Here you'll add the form submission logic later
        alert('Changes saved successfully!');
    });
</script>
</div>
</body>
</html>