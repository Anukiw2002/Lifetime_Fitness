<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>LIFETIME FITNESS - Edit Profile</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/editProfile.css">
</head>
<body>

<div class="edit-profile-container">
    <h1>Edit Profile</h1>

    <div class="profile-picture-section">
        <div class="profile-picture">
            <img src="${pageContext.request.contextPath}/images/profilePicAvatar.jpg" alt="Profile Picture">
        </div>
        <button class="change-picture-btn">Change Picture</button>
    </div>

    <form class="edit-profile-form">
        <div class="form-section">
            <h2>Personal Information</h2>
            <div class="form-group">
                <label for="fullName">Full Name</label>
                <input type="text" id="fullName" value="Mihindu Dharamasena" required>
            </div>
            <div class="form-row">
                <div class="form-group">
                    <label for="age">Age</label>
                    <input type="number" id="age" value="25" required>
                </div>
                <div class="form-group">
                    <label for="gender">Gender</label>
                    <select id="gender">
                        <option value="male" selected>Male</option>
                        <option value="female">Female</option>
                        <option value="other">Other</option>
                    </select>
                </div>
            </div>
        </div>

        <div class="form-section">
            <h2>Contact Information</h2>
            <div class="form-group">
                <label for="email">Email Address</label>
                <input type="email" id="email" value="mihindu@example.com" required>
            </div>
            <div class="form-group">
                <label for="phone">Phone Number</label>
                <input type="tel" id="phone" value="+94 77 123 4567" required>
            </div>
            <div class="form-group">
                <label for="address">Address</label>
                <textarea id="address" rows="3">123 Gym Street, Colombo</textarea>
            </div>
        </div>

        <div class="form-section">
            <h2>Health Information</h2>
            <div class="form-row">
                <div class="form-group">
                    <label for="weight">Weight (kg)</label>
                    <input type="number" id="weight" value="75" step="0.1">
                </div>
                <div class="form-group">
                    <label for="height">Height (cm)</label>
                    <input type="number" id="height" value="175">
                </div>
            </div>
            <div class="form-group">
                <label for="healthConditions">Health Conditions</label>
                <textarea id="healthConditions" rows="3" placeholder="List any health conditions or injuries..."></textarea>
            </div>
        </div>

        <div class="form-section">
            <h2>Emergency Contact</h2>
            <div class="form-group">
                <label for="emergencyName">Contact Name</label>
                <input type="text" id="emergencyName" value="Emergency Contact">
            </div>
            <div class="form-group">
                <label for="emergencyPhone">Contact Phone</label>
                <input type="tel" id="emergencyPhone" value="+94 77 987 6543">
            </div>
        </div>

        <div class="button-group">
            <button type="submit" class="save-btn">Save Changes</button>
            <button type="button" class="cancel-btn" onclick="location.href='profile.jsp'">Cancel</button>
        </div>
    </form>
</div>
</body>
</html>