<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Responsive Navbar with Dropdown</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
  <link rel="stylesheet" href="/css/loggedNavbar.css">
</head>
<body>
<div class="navbar-container">
  <img src="/images/logo.png" alt="Lifetime Fitness Logo" class="logo">
  <nav class="navbar">
    <ul class="nav-links">
      <li><a href="#">Home</a></li>
      <li><a href="#">About</a></li>
      <li><a href="#">Contact Us</a></li>
      <li><a href="#">Pricing</a></li>
      <li><a href="#">Reviews</a></li>
    </ul>
    <div class="nav-buttons">
      <!-- Profile Image -->
      <img src="Assets/profile-image.png" alt="Profile Image" class="profile-image">
    </div>

    <!-- Notification Bell with Dropdown -->
    <div class="notification-bell-wrapper">
      <div class="notification-container">
        <span class="notification-dot"></span> <!-- Red dot for new notifications -->
        <i class="fas fa-bell notification-icon"></i> <!-- Bell icon for notifications -->
        <div class="notification-dropdown">
          <div class="notification-item">
            <span class="notification-icon">&#x1F4CC;</span> <!-- Pin icon -->
            <span class="notification-text">You have upcoming sessions due</span>
            <span class="notification-time">3 days 10 hours ago</span>
            <a href="/viewNotification" class="notification-link">View full notification</a>
          </div>
          <div class="notification-item">
            <span class="notification-icon">&#x1F4CC;</span>
            <span class="notification-text">You have upcoming sessions due</span>
            <span class="notification-time">4 days 15 hours ago</span>
            <a href="/viewNotification" class="notification-link">View full notification</a>
          </div>
          <div class="notification-item">
            <span class="notification-icon">&#x1F4CC;</span>
            <span class="notification-text">You have upcoming sessions due</span>
            <span class="notification-time">7 days 2 hours ago</span>
            <a href="/viewNotification" class="notification-link">View full notification</a>
          </div>
        </div>
      </div>
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
      <br>
      <div class="dropdown-buttons">
        <a href="#" class="nav-buttons1"><img src="Assets/profile-image.png" alt="" style="width: 100%; height: 100%; border-radius: 70%;"></a>
      </div>
    </ul>
  </div>
</div>

<!-- Combined JavaScript for Hamburger Menu and Notification Dropdown -->
<script>
  // Hamburger menu toggle for mobile view
  const hamburger = document.querySelector('.hamburger');
  const navbar = document.querySelector('.navbar');
  const dropdownMenu = document.querySelector('.dropdown-menu');

  hamburger.addEventListener('click', () => {
    navbar.classList.toggle('active');
    dropdownMenu.classList.toggle('active');
  });

  // Notification dropdown toggle for bell icon
  const notificationIcon = document.querySelector('.notification-icon');
  const notificationDropdown = document.querySelector('.notification-dropdown');

  notificationIcon.addEventListener('click', (event) => {
    event.stopPropagation(); // Prevent the event from bubbling up
    notificationDropdown.classList.toggle('active');
  });

  // Close notification dropdown when clicking outside
  document.addEventListener('click', (event) => {
    if (!notificationIcon.contains(event.target) && !notificationDropdown.contains(event.target)) {
      notificationDropdown.classList.remove('active');
    }
  });
</script>
</body>
</html>
