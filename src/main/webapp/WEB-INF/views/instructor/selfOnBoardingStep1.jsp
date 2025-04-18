<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Set New Password - Lifetime Fitness</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/firstLogInPage.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body>
<div class="container">
    <div class="content-card">
            <div class="welcome-section">
                <h1>Welcome to Lifetime Fitness</h1>
                <p class="subtitle">Please set a new password to continue</p>
            </div>

            <form id="passwordChangeForm" action="${pageContext.request.contextPath}/selfOnboarding/step1" method="post">
                <div class="password-fields">
                    <div class="form-group">
                        <label for="newPassword">New Password</label>
                        <div class="password-input-container">
                            <input type="password" id="newPassword" name="newPassword" required>
                            <button type="button" class="toggle-password" onclick="togglePassword('newPassword', 'toggleIcon1')">
                                <i class="fa-solid fa-eye" id="toggleIcon1"></i>
                            </button>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="confirmPassword">Confirm New Password</label>
                        <div class="password-input-container">
                            <input type="password" id="confirmPassword" name="confirmPassword" required>
                            <button type="button" class="toggle-password" onclick="togglePassword('confirmPassword', 'toggleIcon2')">
                                <i class="fa-solid fa-eye" id="toggleIcon2"></i>
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
        function togglePassword(inputId, iconId) {
            const passwordInput = document.getElementById(inputId);
            const toggleIcon = document.getElementById(iconId);

            if (passwordInput.type === 'password') {
                passwordInput.type = 'text';
                toggleIcon.className = 'fa-solid fa-eye-slash';
            } else {
                passwordInput.type = 'password';
                toggleIcon.className = 'fa-solid fa-eye';
            }
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

            const newPassword = document.getElementById('newPassword').value;

            // Here you would typically make an AJAX call to validate the current password
            // For now, we'll just submit the form
            document.getElementById('passwordChangeForm').submit();
            return true;
        }
    </script>
</body>
</html>