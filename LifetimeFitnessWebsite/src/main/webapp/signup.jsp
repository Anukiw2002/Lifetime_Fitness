<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Sign Up | Lifetime Fitness</title>
<link rel="stylesheet" href="css/styles.css">
</head>

<body>
<div class="sign-up-container">
<div class = "form-section">
<img src="images/logo.png" alt="Logo" class="logo1">
<h2 class="sign-up-heading">Sign Up</h2>
<form action="#" method="post" class="sign-up-form">
<input type="text" name="name" placeholder="Enter your Name" required>
<input type="email" name="email" placeholder="Enter your email address" required>
<input type="text" name="username" placeholder="Enter your username" required>
<input type="password" name="password" placeholder="Create password" required>
<input type="password" name="confirmPassword" placeholder="Confirm password" required>
<button type="submit" class="register-btn">Register</button> 
</form>
<p>Already have an account? <a href="#">Log in</a><p>
<label>
<input type="checkbox" name="agreeTerms" required>
I agree to the T&C
</label>
</div>
<div class="image-section">
<img src="images/sign-up.png" alt="Sign Up Image">
</div>
</div>
</body>
</html>