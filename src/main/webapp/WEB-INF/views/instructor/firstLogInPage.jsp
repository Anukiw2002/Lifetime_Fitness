<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Set New Password - Lifetime Fitness</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/firstLogInPage.css">
</head>
<body>
<jsp:include page="../common/verticalNavBar.jsp" />
<div class="main-content">
<div class="container">

    <div class="content-card">
        <div class="welcome-section">
            <h1>Welcome to Lifetime Fitness</h1>
            <p class="subtitle">Please set a new password to continue</p>
        </div>

        <form id="passwordChangeForm" action="${pageContext.request.contextPath}/instructor/update-password" method="post" onsubmit="return validateForm(event)">
            <div class="password-fields">
                <div class="form-group">
                    <label for="currentPassword">Current Password</label>
                    <div class="password-input-container">
                        <input type="password" id="currentPassword" name="currentPassword" required>
                        <button type="button" class="toggle-password" onclick="togglePasswordVisibility('currentPassword')">
                            <span class="eye-icon">👁️</span>
                        </button>
                    </div>
                </div>

                <div class="form-group">
                    <label for="newPassword">New Password</label>
                    <div class="password-input-container">
                        <input type="password" id="newPassword" name="newPassword" required>
                        <button type="button" class="toggle-password" onclick="togglePasswordVisibility('newPassword')">
                            <span class="eye-icon">👁️</span>
                        </button>
                    </div>
                </div>

                <div class="form-group">
                    <label for="confirmPassword">Confirm New Password</label>
                    <div class="password-input-container">
                        <input type="password" id="confirmPassword" name="confirmPassword" required>
                        <button type="button" class="toggle-password" onclick="togglePasswordVisibility('confirmPassword')">
                            <span class="eye-icon">👁️</span>
                        </button>
                    </div>
                </div>
            </div>

            <div class="password-requirements">
                <h3>Password Requirements:</h3>
                <ul>
                    <li id="lengthCheck">At least 8 characters long</li>
                    <li id="upperCheck">Contains at least one uppercase letter</li>
                    <li id="lowerCheck">Contains at least one lowercase letter</li>
                    <li id="numberCheck">Contains at least one number</li>
                    <li id="specialCheck">Contains at least one special character</li>
                    <li id="matchCheck">Passwords match</li>
                </ul>
            </div>

            <div class="form-actions">
                <button type="submit" class="submit-button" id="submitButton" disabled>Continue to Profile Setup</button>
            </div>
        </form>
    </div>
</div>

<script>
    function togglePasswordVisibility(inputId) {
        const input = document.getElementById(inputId);
        input.type = input.type === 'password' ? 'text' : 'password';
    }

    const passwordChecks = {
        length: false,
        upper: false,
        lower: false,
        number: false,
        special: false,
        match: false
    };

    function updateRequirements(password, confirmPassword) {
        // Length check
        passwordChecks.length = password.length >= 8;
        document.getElementById('lengthCheck').className = passwordChecks.length ? 'valid' : '';

        // Uppercase check
        passwordChecks.upper = /[A-Z]/.test(password);
        document.getElementById('upperCheck').className = passwordChecks.upper ? 'valid' : '';

        // Lowercase check
        passwordChecks.lower = /[a-z]/.test(password);
        document.getElementById('lowerCheck').className = passwordChecks.lower ? 'valid' : '';

        // Number check
        passwordChecks.number = /[0-9]/.test(password);
        document.getElementById('numberCheck').className = passwordChecks.number ? 'valid' : '';

        // Special character check
        passwordChecks.special = /[!@#$%^&*(),.?":{}|<>]/.test(password);
        document.getElementById('specialCheck').className = passwordChecks.special ? 'valid' : '';

        // Match check
        passwordChecks.match = password === confirmPassword && password !== '';
        document.getElementById('matchCheck').className = passwordChecks.match ? 'valid' : '';

        // Enable/disable submit button
        document.getElementById('submitButton').disabled = !Object.values(passwordChecks).every(Boolean);
    }

    // Add event listeners
    document.getElementById('newPassword').addEventListener('input', function() {
        updateRequirements(this.value, document.getElementById('confirmPassword').value);
    });

    document.getElementById('confirmPassword').addEventListener('input', function() {
        updateRequirements(document.getElementById('newPassword').value, this.value);
    });

    function validateForm(event) {
        event.preventDefault();

        if (!Object.values(passwordChecks).every(Boolean)) {
            return false;
        }

        const currentPassword = document.getElementById('currentPassword').value;
        const newPassword = document.getElementById('newPassword').value;

        // Here you would typically make an AJAX call to validate the current password
        // For now, we'll just submit the form
        document.getElementById('passwordChangeForm').submit();
        return true;
    }
</script>
</body>
</html>