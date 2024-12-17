<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Contact Us</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/map.css">
</head>
<body>
<div class="contact-section" data-aos="fade-up">
  <h1 class="contact-title" data-aos="fade-up">CONTACT US</h1>
  <div class="contact-container">
    <form class="contact-form" data-aos="fade-right">
      <input type="text" placeholder="Name" required>
      <input type="email" placeholder="Email" required>
      <textarea placeholder="Message" required></textarea>
      <button type="button" onclick="showSuccessMessage()">Send Message</button>    </form>
    <div class="map-container" data-aos="fade-left">
      <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3961.1118374267335!2d79.93241407448255!3d6.8772023189530636!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3ae250966951753f%3A0x237abed848821c37!2sLifetime%20Fitness!5e0!3m2!1sen!2slk!4v1734017834326!5m2!1sen!2slk" width="600" height="450" style="border:0;" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
    </div>
  </div>
</div>
<script>
  function showSuccessMessage() {
    alert("Successfully sent");
  }
</script>
</body>
</html>