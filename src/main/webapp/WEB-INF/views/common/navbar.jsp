<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Responsive Navbar with Dropdown</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/navbar.css">
</head>
<body>
<div class="navbar-container">
    <!-- Update the path to the logo image -->
    <img src="<%= request.getContextPath() %>/images/LogoWhite.png" alt="Lifetime Fitness Logo" class="logo">
    <nav class="navbar">
        <ul class="nav-links">
            <li><a href="#">Home</a></li>
            <li><a href="#">About</a></li>
            <li><a href="#">Contact Us</a></li>
            <li><a href="#">Pricing</a></li>
            <li><a href="#">Reviews</a></li>
        </ul>
        <div class="nav-buttons">
            <a href="#" class="btn register">Register</a>
            <a href="#" class="btn login">Login</a>
        </div>
        <div class="hamburger">
            <div class="line"></div>
            <div class="line"></div>
            <div class="line"></div>
        </div>
    </nav>
    <div class="dropdown-menu">
        <ul class="dropdown-links">
            <li><a href="#">Home</a></li>
            <li><a href="#">About</a></li>
            <li><a href="#">Contact Us</a></li>
            <li><a href="#">Pricing</a></li>
            <li><a href="#">Reviews</a></li>
            <div class="dropdown-buttons">
                <a href="#" class="btn-register">Register</a>
                <a href="#" class="btn-login">Login</a>
            </div>
        </ul>
    </div>
</div>

<script src="<%= request.getContextPath() %>/js/navbar.js"></script>
</body>
</html>
