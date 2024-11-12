<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Notification Details</title>
  <style>
    /* Basic page styling */
    body {
      font-family: Arial, sans-serif;
      margin: 0;
      padding: 0;
      background-color: #f9f9f9;
    }

    .container {
      max-width: 800px;
      margin: 50px auto;
      padding: 20px;
      background-color: #fff;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
      border-radius: 8px;
    }

    h1 {
      text-align: center;
      color: #333;
      margin-bottom: 20px;
    }

    .notification-detail {
      padding: 15px;
      border-bottom: 1px solid #ddd;
    }

    .notification-detail:last-child {
      border-bottom: none;
    }

    .notification-title {
      font-size: 18px;
      color: #333;
      font-weight: bold;
    }

    .notification-description {
      margin-top: 5px;
      font-size: 15px;
      color: #555;
    }

    .notification-time {
      font-size: 14px;
      color: #888;
      margin-top: 5px;
    }

    .back-link {
      display: inline-block;
      margin-top: 20px;
      font-size: 14px;
      color: #007BFF;
      text-decoration: none;
    }

    .back-link:hover {
      text-decoration: underline;
    }
  </style>
</head>
<body>

<div class="container">
  <h1>Notification Details</h1>

  <div class="notification-detail">
    <div class="notification-title">Upcoming Session Due</div>
    <div class="notification-description">You have an upcoming session scheduled on <strong>Monday, Nov 15, 2024</strong> at <strong>10:00 AM</strong>.</div>
    <div class="notification-time">3 days 10 hours ago</div>
  </div>

  <div class="notification-detail">
    <div class="notification-title">Upcoming Session Due</div>
    <div class="notification-description">You have an upcoming session scheduled on <strong>Wednesday, Nov 17, 2024</strong> at <strong>2:00 PM</strong>.</div>
    <div class="notification-time">4 days 15 hours ago</div>
  </div>

  <div class="notification-detail">
    <div class="notification-title">Upcoming Session Due</div>
    <div class="notification-description">You have an upcoming session scheduled on <strong>Friday, Nov 19, 2024</strong> at <strong>11:00 AM</strong>.</div>
    <div class="notification-time">7 days 2 hours ago</div>
  </div>

  <a href="/loggedNavbar" class="back-link">&larr; Back to Home</a>
</div>

</body>
</html>
