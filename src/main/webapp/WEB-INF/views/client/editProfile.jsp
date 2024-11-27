<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>LIFETIME FITNESS - Edit Profile</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/generalStyles.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/editProfile.css">
</head>
<body>
<jsp:include page="../client/clientVerticalNavbar.jsp" />
<div class="main-content">
    <div class="container">
        <div class="card">
            <h1 class="text-center mb-4">Edit Profile</h1>

            <div class="flex flex-col items-center mb-4">
                <div class="profile-picture">
                    <img src="${pageContext.request.contextPath}/images/profilePicAvatar.jpg" alt="Profile Picture">
                </div>
                <button class="btn btn-secondary">Change Picture</button>
            </div>

            <form>
                <div class="card mb-4">
                    <h2 class="mb-3">Personal Information</h2>
                    <div class="form-group">
                        <label class="form-label" for="fullName">Full Name</label>
                        <input type="text" id="fullName" class="form-control" value="Mihindu Dharamasena" required>
                    </div>
                    <div class="form-row">
                        <div class="form-group">
                            <label class="form-label" for="age">Age</label>
                            <input type="number" id="age" class="form-control" value="25" required>
                        </div>
                        <div class="form-group">
                            <label class="form-label" for="gender">Gender</label>
                            <select id="gender" class="form-control">
                                <option value="male" selected>Male</option>
                                <option value="female">Female</option>
                                <option value="other">Other</option>
                            </select>
                        </div>
                    </div>
                </div>

                <div class="card mb-4">
                    <h2 class="mb-3">Contact Information</h2>
                    <div class="form-group">
                        <label class="form-label" for="email">Email Address</label>
                        <input type="email" id="email" class="form-control" value="mihindu@example.com" required>
                    </div>
                    <div class="form-group">
                        <label class="form-label" for="phone">Phone Number</label>
                        <input type="tel" id="phone" class="form-control" value="+94 77 123 4567" required>
                    </div>
                    <div class="form-group">
                        <label class="form-label" for="address">Address</label>
                        <textarea id="address" class="form-control">123 Gym Street, Colombo</textarea>
                    </div>
                </div>

                <div class="card mb-4">
                    <h2 class="mb-3">Health Information</h2>
                    <div class="form-row">
                        <div class="form-group">
                            <label class="form-label" for="weight">Weight (kg)</label>
                            <input type="number" id="weight" class="form-control" value="75" step="0.1">
                        </div>
                        <div class="form-group">
                            <label class="form-label" for="height">Height (cm)</label>
                            <input type="number" id="height" class="form-control" value="175">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-label" for="healthConditions">Health Conditions</label>
                        <textarea id="healthConditions" class="form-control" placeholder="List any health conditions or injuries..."></textarea>
                    </div>
                </div>

                <div class="card mb-4">
                    <h2 class="mb-3">Emergency Contact</h2>
                    <div class="form-group">
                        <label class="form-label" for="emergencyName">Contact Name</label>
                        <input type="text" id="emergencyName" class="form-control" value="Emergency Contact">
                    </div>
                    <div class="form-group">
                        <label class="form-label" for="emergencyPhone">Contact Phone</label>
                        <input type="tel" id="emergencyPhone" class="form-control" value="+94 77 987 6543">
                    </div>
                </div>

                <div class="flex justify-end gap-md">
                    <button type="button" class="btn btn-secondary" onclick="location.href='${pageContext.request.contextPath}/memberProfile'">Cancel</button>
                    <button type="submit" class="btn btn-primary">Save Changes</button>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>