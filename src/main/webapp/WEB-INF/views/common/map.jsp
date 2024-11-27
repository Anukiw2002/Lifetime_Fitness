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
      <button type="submit">Send Message</button>
    </form>
    <div class="map-container" data-aos="fade-left">
      <iframe
              src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3169.8379415244725!2d-122.084249684887!3d37.42206597982553!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x808fba027e856c6d%3A0xd89f7c3ed7a1ed9b!2sGoogleplex!5e0!3m2!1sen!2sus!4v1617218739819!5m2!1sen!2sus"
              allowfullscreen=""
              loading="lazy">
      </iframe>
    </div>
  </div>
</div>
</body>
</html>