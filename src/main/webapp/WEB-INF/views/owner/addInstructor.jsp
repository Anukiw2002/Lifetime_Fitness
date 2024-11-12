<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add New Instructor - Basic Info</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/addInstructor.css">
</head>
<body>
<jsp:include page="../common/verticalNavBar.jsp" />
<div class="main-content">
<div class="container">
    <div class="header">
        <h1>Add New Instructor</h1>
        <p>Enter basic information to create instructor account</p>
    </div>

    <form action="#" method="POST" id="instructorForm">
        <div class="form-group">
            <label for="fullName">Full Name*</label>
            <input type="text" id="fullName" name="fullName" required>
        </div>

        <div class="form-row">
            <div class="form-group">
                <label for="email">Email*</label>
                <input type="email" id="email" name="email" required>
            </div>
            <div class="form-group">
                <label for="phone">Phone Number*</label>
                <input type="tel" id="phone" name="phone" required>
            </div>
        </div>

        <div class="form-group">
            <label for="tempPassword">Temporary Password*</label>
            <div class="password-field">
                <input type="text" id="tempPassword" name="tempPassword" required readonly>
                <button type="button" class="generate-btn" onclick="generatePassword()">Generate</button>
            </div>
            <div class="password-indicator" id="passwordGenerated" style="display: none;">
                ✓ Password generated
            </div>
        </div>

        <div class="credentials-box" id="credentialsBox" style="display: none;">
            <div class="credentials-header">
                <h3>Login Credentials</h3>
                <button type="button" class="copy-btn" onclick="copyCredentials()">
                    Copy Credentials
                </button>
            </div>
            <div class="credentials-content">
                <p><strong>Website:</strong> lifetimefitness.lk/login</p>
                <p><strong>Email:</strong> <span id="credEmail"></span></p>
                <p><strong>Password:</strong> <span id="credPassword"></span></p>
            </div>
        </div>

        <div class="btn-container">
            <button type="button" class="btn btn-secondary" onclick="window.location.href='dashboard.jsp'">Cancel</button>
            <button type="submit" class="btn btn-primary">Create Account</button>
        </div>
    </form>
</div>

<div class="toast" id="toast">Credentials copied to clipboard!</div>

<script>
    function generatePassword() {
        const length = 10;
        const charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*";
        let password = "";
        for (let i = 0; i < length; i++) {
            password += charset.charAt(Math.floor(Math.random() * charset.length));
        }
        document.getElementById('tempPassword').value = password;
        document.getElementById('passwordGenerated').style.display = 'block';

        // Update credentials box
        document.getElementById('credPassword').textContent = password;
        document.getElementById('credEmail').textContent = document.getElementById('email').value;
        document.getElementById('credentialsBox').style.display = 'block';
    }

    function copyCredentials() {
        const emailInput = document.getElementById('email');
        const passwordInput = document.getElementById('tempPassword');

        if (!emailInput.value || !passwordInput.value) {
            alert('Please fill in email and generate password first.');
            return;
        }

        // Get the CURRENT values from the form fields
        const currentEmail = emailInput.value;
        const currentPassword = passwordInput.value;

        const credentials =
            `Lifetime Fitness - Login Credentials

Website: lifetimefitness.lk/login
Email: ${currentEmail}
Temporary Password: ${currentPassword}

Please change your password after first login.`;

        navigator.clipboard.writeText(credentials)
            .then(() => {
                const toast = document.getElementById('toast');
                toast.classList.add('show');
                setTimeout(() => {
                    toast.classList.remove('show');
                }, 3000);
            })
            .catch(err => {
                console.error('Failed to copy: ', err);
                alert('Failed to copy credentials. Please try again.');
            });
    }

    // Update credentials box when email changes
    document.getElementById('email').addEventListener('input', function() {
        if (document.getElementById('credentialsBox').style.display === 'block') {
            document.getElementById('credEmail').textContent = this.value;
        }
    });

    // Form submission handler
    document.getElementById('instructorForm').addEventListener('submit', function(e) {
        const password = document.getElementById('tempPassword').value;
        if (!password) {
            e.preventDefault();
            alert('Please generate a password first.');
        }
    });
</script>
</div>
</body>
</html>