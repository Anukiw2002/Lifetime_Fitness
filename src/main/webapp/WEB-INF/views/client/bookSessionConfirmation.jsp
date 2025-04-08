<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %><%--
  Created by IntelliJ IDEA.
  User: anukiwanniarachchi
  Date: 2025-04-07
  Time: 3:00 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cofirm Session Booking</title>
</head>
<body>
<h1>Confirm Your Booking</h1>
<p>Date & Time</p>

<%
  String selectedDate = request.getParameter("selectedDate");
  String selectedSlot = request.getParameter("selectedSlot");

  if (selectedDate != null && selectedSlot != null) {
    try {

      // Parse the date from yyyy-MM-dd format
      LocalDate date = LocalDate.parse(selectedDate);

      // Format the date as "Monday, April 28, 2025"
      String formattedDate = date.format(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy"));

      out.println("<p>" + formattedDate + "</p>");
      out.println("<p>" + selectedSlot + "</p>");
    } catch (Exception e) {
      out.println("<p>Error formatting date: " + selectedDate + "</p>");
      out.println("<p>" + selectedSlot + "</p>");
    }
  } else {
    out.println("<p>Invalid booking details.</p>");
  }
%>
<form method="POST" action="bookSessionConfirmation">
  <input type="hidden" name="selectedDate" value="<%= selectedDate %>" required>
  <input type="hidden" name="selectedSlot" value="<%= selectedSlot %>" required>
  <p>Recurrence</p>
  <select id="frequency" name="frequency" required>
    <option id="one-time">One time only</option>
    <option id="daily">Daily</option>
    <option id="every-other-day">Every other day</option>
    <option id="weekly">Weekly</option>
  </select>

  <button type="button" onclick="window.location.href='bookSession'">Cancel</button>
  <button type="submit">Confirm Booking</button>

</form>

</body>
</html>
