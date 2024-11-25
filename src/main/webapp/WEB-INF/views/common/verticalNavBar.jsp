<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Vertical Navbar</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        /* Basic Reset */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Arial', sans-serif;
            background-color: #2E2E2E;
            color: #000;
            display: flex;
            flex-direction: column;
            height: 100vh;
            align-items: flex-start;
            font-size: 1rem;
        }

        /* Navbar Styles */
        .navbar {
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            width: 100%;
            max-width: 250px;
            height: 100vh;
            padding: 20px;
            background-color: #D9D9D9;
            border-right: 1px solid #ddd;
            box-shadow: 2px 0 4px rgba(0, 0, 0, 0.1);
            position: fixed;
            top: 0;
            left: 0;
            transition: transform 0.3s ease-in-out;
        }

        .navbar h1 {
            font-size: 1.5rem;
            text-transform: uppercase;
            margin-bottom: 20px;
        }

        .logo {
            width: 150px;
            margin: 0 auto 30px;
            align-self: center;
        }

        .nav-link {
            background-color: #fff;
            border: 2px solid #000;
            border-radius: 15px;
            padding: 10px 20px;
            margin: 20px 0;
            width: 100%;
            text-align: center;
            text-decoration: none;
            color: #000;
            font-weight: bold;
            font-size: 1rem;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .nav-link:hover {
            background-color: #e9e9e9;
        }

        .bottom-links {
            margin-top: auto;
            display: flex;
            flex-direction: column;
        }

        .icon {
            font-size: 1.25rem;
            margin-right: 10px;
        }

        /* Hamburger Menu Icon */
        .menu-icon {
            display: none;
            position: fixed;
            top: 20px;
            left: 20px;
            width: 30px;
            height: 30px;
            cursor: pointer;
            z-index: 1000;
        }

        .menu-icon div {
            width: 100%;
            height: 4px;
            background-color: #0f0202;
            margin: 6px 0;
            transition: 0.4s;
        }

        /* Animation for the hamburger icon */
        .menu-icon.active div:nth-child(1) {
            transform: rotate(-45deg) translate(-5px, 6px);
        }

        .menu-icon.active div:nth-child(2) {
            opacity: 0;
        }

        .menu-icon.active div:nth-child(3) {
            transform: rotate(45deg) translate(-5px, -6px);
        }

        /* Media Queries for Responsiveness */
        @media (max-width: 768px) {
            .navbar {
                position: fixed;
                transform: translateX(-100%);
                height: 100vh;
                overflow: auto;
            }

            .navbar.active {
                transform: translateX(0);
            }

            .menu-icon {
                display: block;
            }
        }

        @media (max-width: 480px) {
            .navbar {
                height: auto;
                padding: 10px;
            }

            .nav-link {
                margin: 8px 0;
                padding: 8px 12px;
                font-size: 0.9rem;
            }

            .navbar h1 {
                font-size: 1.2rem;
                margin-bottom: 10px;
            }
        }
    </style>
</head>
<body>
<!-- Menu Icon for smaller screens -->
<div class="menu-icon">
    <div></div>
    <div></div>
    <div></div>
</div>
<!-- Navbar -->
<div class="navbar">
    <div>
        <img src="${pageContext.request.contextPath}/images/logo.png" alt="Lifetime Fitness" class="logo">
    </div>
    <div>
        <a href="#dashboard" class="nav-link"><i class="fas fa-chart-line icon"></i>Dashboard</a>
        <a href="#members" class="nav-link"><i class="fas fa-users icon"></i>Members</a>
        <a href="#workout" class="nav-link"><i class="fas fa-dumbbell icon"></i>Workout</a>
        <a href="#notifications" class="nav-link"><i class="fas fa-bell icon"></i>Notifications</a>
        <a href="#reports" class="nav-link"><i class="fas fa-file-alt icon"></i>Reports</a>
        <a href="#videos" class="nav-link"><i class="fas fa-video icon"></i>Videos</a>
        <a href="#blogs" class="nav-link"><i class="fas fa-book icon"></i>Blogs</a>

    </div>

    <!-- Bottom links for Settings and Log Out -->
    <div class="bottom-links">
        <a href="#settings" class="nav-link"><i class="fas fa-cog icon"></i>Settings</a>
        <a href="#logout" class="nav-link"><i class="fas fa-sign-out-alt icon"></i>Log out</a>
    </div>
</div>

<script>
    const menuIcon = document.querySelector('.menu-icon');
    const navbar = document.querySelector('.navbar');
    menuIcon.addEventListener('click', () => {
        menuIcon.classList.toggle('active');
        navbar.classList.toggle('active');
    });
</script>
</body>
</html>
