<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Responsive Navbar with Dropdown</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navbar.css">
</head>
<body>
<div class="navbar-container">
    <!-- Update the path to the logo image -->
    <img src="${pageContext.request.contextPath}/images/LogoWhite.png" alt="Lifetime Fitness Logo" class="logo">
    <nav class="navbar">
        <ul class="nav-links">
            <li><a href="#home">Home</a></li>
            <li><a href="#about">About</a></li>
            <li><a href="#contact us">Contact Us</a></li>
            <li><a href="#pricing">Pricing</a></li>
            <li><a href="#reviews">Reviews</a></li>
        </ul>
        <div class="nav-buttons">
            <a href="testView?page=page1" class="btn register">Register</a>
            <a href="testView?page=login" class="btn login">Login</a>
        </div>
        <div class="hamburger">
            <div class="line"></div>
            <div class="line"></div>
            <div class="line"></div>
        </div>
    </nav>
    <div class="dropdown-menu">
        <ul class="dropdown-links">
            <li><a href="#home">Home</a></li>
            <li><a href="#about">About</a></li>
            <li><a href="#contact us">Contact Us</a></li>
            <li><a href="#pricing">Pricing</a></li>
            <li><a href="#reviews">Reviews</a></li>
            <div class="dropdown-buttons">
                <a href="testView?page=page1" class="btn-register">Register</a>
                <a href="testView?page=login" class="btn-login">Login</a>
            </div>
        </ul>
    </div>
</div>

<!-- JavaScript code added directly to the HTML file -->
<script src="<%= request.getContextPath() %>/js/loggedNavbar.js"></script>
</body>
</html>
