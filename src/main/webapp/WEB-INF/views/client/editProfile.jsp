<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

            <form action="clientEditProfile" method="post" enctype="multipart/form-data" id="profileForm" novalidate>
                <div class="flex flex-col items-center mb-4">
                    <div class="profile-picture">
                        <c:choose>
                            <c:when test="${empty client.profilePictureBase64}">
                                <img src="${pageContext.request.contextPath}/images/profilePicAvatar.jpg" alt="Default Profile Picture">
                            </c:when>
                            <c:otherwise>
                                <img src="data:image/jpeg;base64,${client.profilePictureBase64}" alt="Profile Picture">
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <label for="profilePicture" class="btn btn-secondary">Change Picture</label>
                    <input type="file" id="profilePicture" name="profilePicture" style="display: none;" accept="image/*" onchange="previewImage(this);">
                </div>
                <div class="card mb-4">
                    <h2 class="mb-3">Personal Information</h2>
                    <div class="form-row">
                        <div class="form-group">
                            <label class="form-label" for="name">First Name</label>
                            <input type="text" id="name" name="name" class="form-control" value="${client.firstName}" required pattern="[A-Za-z]+" title="Only letters are allowed">
                            <div class="error-message" id="nameError"></div>
                        </div>
                        <div class="form-group">
                            <label class="form-label" for="username">Username</label>
                            <input type="text" id="username" name="username" class="form-control" value="${client.username}" required>
                            <div class="error-message" id="usernameError"></div>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group">
                            <label class="form-label" for="dateOfBirth">Date of Birth</label>
                            <input type="date" id="dateOfBirth" name="dateOfBirth" class="form-control" value="${client.dateOfBirth}" required>
                            <div class="error-message" id="dateOfBirthError"></div>
                        </div>
                        <div class="form-group">
                            <label class="form-label" for="gender">Gender</label>
                            <select id="gender" name="gender" class="form-control" required>
                                <option value="Male" ${client.gender == 'Male' ? 'selected' : ''}>Male</option>
                                <option value="Female" ${client.gender == 'Female' ? 'selected' : ''}>Female</option>
                            </select>
                        </div>
                    </div>
                </div>

                <div class="card mb-4">
                    <h2 class="mb-3">Contact Information</h2>
                    <div class="form-row">
                        <div class="form-group">
                            <label class="form-label" for="emailAddress">Email Address</label>
                            <input type="email" id="emailAddress" name="emailAddress" class="form-control" value="${client.email}" required>
                            <div class="error-message" id="emailError"></div>
                        </div>
                        <div class="form-group">
                            <label class="form-label" for="phoneNumber">Phone Number</label>
                            <input type="tel" id="phoneNumber" name="phoneNumber" class="form-control" value="${client.phoneNumber}" required pattern="[0-9]{10}" title="Phone number must be 10 digits">
                            <div class="error-message" id="phoneError"></div>
                        </div>
                    </div>
                    <h3 class="mt-3 mb-2">Address</h3>
                    <div class="form-row">
                        <div class="form-group">
                            <label class="form-label" for="houseNo">House Number</label>
                            <input type="text" id="houseNo" name="houseNo" class="form-control" value="${client.houseNo}" required>
                            <div class="error-message" id="houseNoError"></div>
                        </div>
                        <div class="form-group">
                            <label class="form-label" for="streetName">Street Name</label>
                            <input type="text" id="streetName" name="streetName" class="form-control" value="${client.streetName}" required>
                            <div class="error-message" id="streetNameError"></div>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group">
                            <label class="form-label" for="city">City</label>
                            <input type="text" id="city" name="city" class="form-control" value="${client.city}" required pattern="[A-Za-z\s]+" title="Only letters are allowed">
                            <div class="error-message" id="cityError"></div>
                        </div>
                    </div>
                </div>

                <div class="card mb-4">
                    <h2 class="mb-3">Emergency Contact</h2>
                    <div class="form-row">
                        <div class="form-group">
                            <label class="form-label" for="emergencyContactName">Contact Name</label>
                            <input type="text" id="emergencyContactName" name="emergencyContactName" class="form-control" value="${client.emergencyContactName}" required pattern="[A-Za-z\s]+" title="Only letters are allowed">
                            <div class="error-message" id="emergencyNameError"></div>
                        </div>
                        <div class="form-group">
                            <label class="form-label" for="emergencyContactNumber">Contact Phone</label>
                            <input type="tel" id="emergencyContactNumber" name="emergencyContactNumber" class="form-control" value="${client.emergencyContactNumber}" required pattern="[0-9]{10}" title="Phone number must be 10 digits">
                            <div class="error-message" id="emergencyPhoneError"></div>
                        </div>
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
<script>
    function previewImage(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function(e) {
                // Just update the preview image
                document.querySelector('.profile-picture img').src = e.target.result;
                // Don't store in hidden field anymore
            }

            reader.readAsDataURL(input.files[0]);
        }
    }

    // Set max date for date of birth (before 2015)
    window.addEventListener('DOMContentLoaded', function() {
        const dobInput = document.getElementById('dateOfBirth');
        const maxDate = new Date('2014-12-31');
        const formattedDate = maxDate.toISOString().split('T')[0];
        dobInput.setAttribute('max', formattedDate);
    });

    // Form validation
    document.getElementById('profileForm').addEventListener('submit', function(event) {
        let isValid = true;
        const errorMessages = {};

        // First name validation (letters only)
        const nameInput = document.getElementById('name');
        if (!nameInput.value.match(/^[A-Za-z]+$/)) {
            errorMessages.nameError = "First name must contain only letters";
            isValid = false;
        }

        // Username validation (not empty)
        const usernameInput = document.getElementById('username');
        if (usernameInput.value.trim() === '') {
            errorMessages.usernameError = "Username is required";
            isValid = false;
        }

        // Date of birth validation (before 2015)
        const dobInput = document.getElementById('dateOfBirth');
        const selectedDate = new Date(dobInput.value);
        const maxDate = new Date('2015-01-01');
        if (isNaN(selectedDate.getTime()) || selectedDate >= maxDate) {
            errorMessages.dateOfBirthError = "Date of birth must be before 2015";
            isValid = false;
        }

        // Email validation
        const emailInput = document.getElementById('emailAddress');
        if (!emailInput.checkValidity()) {
            errorMessages.emailError = "Please enter a valid email address";
            isValid = false;
        }

        // Phone number validation (10 digits)
        const phoneInput = document.getElementById('phoneNumber');
        if (!phoneInput.value.match(/^\d{10}$/)) {
            errorMessages.phoneError = "Phone number must contain exactly 10 digits";
            isValid = false;
        }

        // House number validation (not empty)
        const houseNoInput = document.getElementById('houseNo');
        if (houseNoInput.value.trim() === '') {
            errorMessages.houseNoError = "House number is required";
            isValid = false;
        }

        // Street name validation (not empty)
        const streetNameInput = document.getElementById('streetName');
        if (streetNameInput.value.trim() === '') {
            errorMessages.streetNameError = "Street name is required";
            isValid = false;
        }

        // City validation (letters only)
        const cityInput = document.getElementById('city');
        if (!cityInput.value.match(/^[A-Za-z\s]+$/)) {
            errorMessages.cityError = "City must contain only letters";
            isValid = false;
        }

        // Emergency contact name validation (letters only)
        const emergencyNameInput = document.getElementById('emergencyContactName');
        if (!emergencyNameInput.value.match(/^[A-Za-z\s]+$/)) {
            errorMessages.emergencyNameError = "Contact name must contain only letters";
            isValid = false;
        }

        // Emergency contact phone validation (10 digits)
        const emergencyPhoneInput = document.getElementById('emergencyContactNumber');
        if (!emergencyPhoneInput.value.match(/^\d{10}$/)) {
            errorMessages.emergencyPhoneError = "Contact phone must contain exactly 10 digits";
            isValid = false;
        }

        // Clear previous error messages
        document.querySelectorAll('.error-message').forEach(el => {
            el.textContent = '';
        });

        // Display error messages if any
        if (!isValid) {
            event.preventDefault();
            for (const [id, message] of Object.entries(errorMessages)) {
                const errorElement = document.getElementById(id);
                if (errorElement) {
                    errorElement.textContent = message;
                }
            }
        }
    });
</script>
</body>
</html>