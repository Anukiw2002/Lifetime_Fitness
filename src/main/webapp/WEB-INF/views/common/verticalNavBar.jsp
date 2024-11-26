<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Enhanced Navigation Bar</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        /* ... (previous root and base styles remain the same) ... */
        :root {
            --bg-color: #333;
            --bg-dark: #1a1a1a;
            --bg-darker: #151515;
            --primary-color: #0052CC;
            --primary-hover: #0747A6;
            --text-color: #fff;
            --text-muted: #888;
            --border-color: rgba(255, 255, 255, 0.2);
            --border-radius: 6px;
            --shadow: 0 4px 12px rgba(0, 0, 0, 0.3);
            --transition: all 0.2s ease-in-out;
            --font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, sans-serif;
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: var(--font-family);
            background-color: var(--bg-color);
            color: var(--text-color);
            line-height: 1.6;
        }

        .navbar-container {
            background-color: var(--bg-darker);
            color: var(--text-color);
            display: flex;
            flex-direction: column;
            height: 100vh;
        }

        .navbar {
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            width: 250px;
            height: 100vh;
            padding: 1.5rem;
            background-color: var(--bg-dark);
            border-right: 1px solid var(--border-color);
            box-shadow: var(--shadow);
            position: fixed;
            top: 0;
            left: 0;
            transition: var(--transition);
        }

        /* Updated logo container styles */
        .logo-container {
            display: flex;
            justify-content: center;
            align-items: center;
            width: 100%;
            padding: 1rem 0;
            margin-bottom: 1.5rem;
        }

        /* Updated logo styles */
        .logo {
            width: 150px;
            height: auto;
            display: block;
            filter: brightness(0.9);
        }

        .nav-link {
            background-color: transparent;
            border: 1px solid var(--border-color);
            border-radius: var(--border-radius);
            padding: 0.875rem 1.25rem;
            margin: 0.5rem 0;
            width: 100%;
            text-decoration: none;
            color: var(--text-color);
            font-weight: 500;
            display: flex;
            align-items: center;
            transition: var(--transition);
        }

        .nav-link:hover {
            background-color: var(--primary-color);
            border-color: var(--primary-color);
            transform: translateY(-1px);
            box-shadow: var(--shadow);
        }

        .icon {
            font-size: 1.1rem;
            margin-right: 1rem;
            color: var(--text-muted);
            transition: var(--transition);
        }

        .nav-link:hover .icon {
            color: var(--text-color);
        }

        .bottom-links {
            margin-top: auto;
            border-top: 1px solid var(--border-color);
            padding-top: 1rem;
        }

        .menu-icon {
            display: none;
            position: fixed;
            top: 1.25rem;
            left: 1.25rem;
            width: 2rem;
            height: 2rem;
            cursor: pointer;
            z-index: 1000;
            padding: 0.5rem;
            background-color: var(--bg-dark);
            border-radius: var(--border-radius);
            border: 1px solid var(--border-color);
        }

        .menu-icon div {
            width: 100%;
            height: 2px;
            background-color: var(--text-color);
            margin: 4px 0;
            transition: var(--transition);
        }

        .menu-icon.active div:nth-child(1) {
            transform: rotate(-45deg) translate(-5px, 6px);
        }

        .menu-icon.active div:nth-child(2) {
            opacity: 0;
        }

        .menu-icon.active div:nth-child(3) {
            transform: rotate(45deg) translate(-5px, -6px);
        }

        @media (max-width: 768px) {
            .navbar {
                transform: translateX(-100%);
                z-index: 999;
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
                width: 100%;
            }

            .nav-link {
                padding: 0.75rem 1rem;
                font-size: 0.9rem;
            }
        }
    </style>
</head>
<body>
<div class="menu-icon">
    <div></div>
    <div></div>
    <div></div>
</div>

<div class="navbar">
    <!-- Updated logo container -->
    <div class="logo-container">
        <img src="${pageContext.request.contextPath}/images/LogoWhite.png" alt="Lifetime Fitness" class="logo">
    </div>
    <div>
        <a href="#dashboard" class="nav-link">
            <i class="fas fa-chart-line icon"></i>Dashboard
        </a>
        <a href="#members" class="nav-link">
            <i class="fas fa-users icon"></i>Members
        </a>
        <a href="/instructor/searchClient" class="nav-link">
            <i class="fas fa-dumbbell icon"></i>Workout
        </a>
        <a href="#notifications" class="nav-link">
            <i class="fas fa-bell icon"></i>Notifications
        </a>
        <a href="#reports" class="nav-link">
            <i class="fas fa-file-alt icon"></i>Reports
        </a>
        <a href="#videos" class="nav-link">
            <i class="fas fa-video icon"></i>Videos
        </a>
        <a href="#blogs" class="nav-link">
            <i class="fas fa-book icon"></i>Blogs
        </a>
        <a href="/membership/view" class="nav-link">
            <i class="fas fa-credit-card icon"></i>Membership
        </a>
    </div>

    <div class="bottom-links">
        <a href="#settings" class="nav-link">
            <i class="fas fa-cog icon"></i>Settings
        </a>
        <a href="#logout" class="nav-link">
            <i class="fas fa-sign-out-alt icon"></i>Log out
        </a>
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