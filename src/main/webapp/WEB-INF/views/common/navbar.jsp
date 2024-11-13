<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Responsive Navbar with Dropdown</title>
    <link rel="stylesheet" href="css/navbar.css">
</head>
<body>
<div class="navbar-container">
    <!-- Update the path to the logo image -->
    <img src="images/LogoWhite.png" alt="Lifetime Fitness Logo" class="logo">
    <nav class="navbar">
        <ul class="nav-links">
            <li><a href="#home">Home</a></li>
            <li><a href="#about">About</a></li>
            <li><a href="#contact us">Contact Us</a></li>
            <li><a href="#pricing">Pricing</a></li>
            <li><a href="#reviews">Reviews</a></li>
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

<!-- JavaScript code added directly to the HTML file -->
<script>
    const hamburger = document.querySelector('.hamburger');
    const navbar = document.querySelector('.navbar');
    const dropdownMenu = document.querySelector('.dropdown-menu');

    hamburger.addEventListener('click', () => {
        navbar.classList.toggle('active');
        dropdownMenu.classList.toggle('active');
    });
</script>
</body>
</html>
