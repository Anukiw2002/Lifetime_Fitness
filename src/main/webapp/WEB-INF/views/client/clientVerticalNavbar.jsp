<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Enhanced Navigation Bar</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/verticalNavbar.css">
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
        <a href="leaderBoard" class="nav-link">
            <i class="fas fa-chart-line icon"></i>Leaderboard
        </a>
        <a href="leaderBoardDetails" class="nav-link">
            <i class="fas fa-users icon"></i>Leaderboard Details
        </a>
        <a href="instructor/clientWorkouts" class="nav-link">
            <i class="fas fa-dumbbell icon"></i>Workout
        </a>
        <a href="viewNotification" class="nav-link">
            <i class="fas fa-bell icon"></i>Notifications
        </a>
        <a href="" class="nav-link">
            <i class="fas fa-video icon"></i>Videos
        </a>
        <a href="GetAllBlogsClient" class="nav-link">
            <i class="fas fa-book icon"></i>Blogs
        </a>
    </div>

    <div class="bottom-links">
        <a href="#settings" class="nav-link">
            <i class="fas fa-cog icon"></i>Settings
        </a>
        <a href="logout" class="nav-link">
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