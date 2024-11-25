<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Vertical Navbar</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/verticalNavbar.css">
</head>
<body>
<!-- Menu Icon for smaller screens -->
<div class="menu-icon">
    <div></div>
    <div></div>
    <div></div>
</div>
<!-- Navbar -->
<div class="navbar-container">
    <div class="navbar">
        <div>
            <img src="${pageContext.request.contextPath}/images/logo.png" alt="Lifetime Fitness" class="logo">
        </div>
        <div>
            <a href="leaderBoard" class="nav-link"><i class="fas fa-chart-line icon"></i>Leaderboard</a>
            <a href="leaderBoardDetails" class="nav-link"><i class="fas fa-dumbbell icon"></i>Leaderboard details</a>
            <a href="#reports" class="nav-link"><i class="fas fa-file-alt icon"></i>Notifications</a>
            <a href="#reports" class="nav-link"><i class="fas fa-file-alt icon"></i>Reviews</a>
            <!-- New Buttons for Videos and Blogs -->
            <a href="#videos" class="nav-link"><i class="fas fa-video icon"></i>Videos</a>
            <a href="#blogs" class="nav-link"><i class="fas fa-book icon"></i>Blogs</a>
        </div>

    <!-- Bottom links for Settings and Log Out -->
    <div class="bottom-links">
        <a href="#settings" class="nav-link"><i class="fas fa-cog icon"></i>Settings</a>
        <a href="landingPage" class="nav-link"><i class="fas fa-sign-out-alt icon"></i>Log out</a>
    </div>
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
