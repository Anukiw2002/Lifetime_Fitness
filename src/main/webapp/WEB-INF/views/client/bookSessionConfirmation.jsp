<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Confirm Session Booking</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bookSessionConfirmation.css">
</head>
<body>
<div class="booking-card">
  <h1>Confirm Your Booking</h1>

  <div class="form-section">
    <p class="section-label">Date & Time</p>
    <div class="date-display">
      <%
        String selectedDate = request.getParameter("selectedDate");
        String selectedSlot = request.getParameter("selectedSlot");

        if (selectedDate != null && selectedSlot != null) {
          try {
            // Parse the date from yyyy-MM-dd format
            LocalDate date = LocalDate.parse(selectedDate);

            // Format the date as "Monday, April 28, 2025"
            String formattedDate = date.format(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy"));

            out.println("<p class='date-value'>" + formattedDate + "</p>");
            out.println("<p class='time-value'>" + selectedSlot + "</p>");
          } catch (Exception e) {
            out.println("<p class='date-value'>Error formatting date: " + selectedDate + "</p>");
            out.println("<p class='time-value'>" + selectedSlot + "</p>");
          }
        } else {
          out.println("<p class='date-value'>Invalid booking details.</p>");
        }
      %>
    </div>
  </div>

  <form method="POST" action="bookSessionConfirmation">
    <input type="hidden" name="selectedDate" value="<%= selectedDate %>" required>
    <input type="hidden" name="selectedSlot" value="<%= selectedSlot %>" required>

    <div class="form-section">
      <p class="section-label">Recurrence</p>
      <select id="frequency" name="frequency" required>
        <option id="one-time">One time only</option>
        <option id="daily">Daily</option>
        <option id="every-other-day">Every other day</option>
        <option id="weekly">Weekly</option>
      </select>
    </div>

    <div class="button-group">
      <button type="button" class="btn-cancel" onclick="window.location.href='bookSession'">Cancel</button>
      <button type="submit" class="btn-confirm">Confirm Booking</button>
    </div>
  </form>
</div>
</body>
</html>