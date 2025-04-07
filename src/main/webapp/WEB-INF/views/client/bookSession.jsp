<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, java.text.*, java.time.*" %>
<%@ page import="org.example.demo2.model.BookingConstraints" %>
<%@ page import="org.example.demo2.dao.BookingConstraintsDAO" %>
<%@ page import="java.time.format.DateTimeParseException" %>
<html>
<head>
  <title>Book a session</title>
</head>
<body>
<h2>Book a Gym Session</h2>
<p>Select a Date</p>
<div style="display: flex; gap: 10px;">
  <%
    SimpleDateFormat dayNameFormat = new SimpleDateFormat("EEEE");
    SimpleDateFormat dayFormat = new SimpleDateFormat("d");
    SimpleDateFormat monthFormat = new SimpleDateFormat("MMM");

    Calendar calendar = Calendar.getInstance();

    // Get the booking constraints from the database
    BookingConstraintsDAO constraintsDAO = new BookingConstraintsDAO();
    BookingConstraints constraints = constraintsDAO.getLatestConstraints();

    int maxDays = (constraints != null) ? constraints.getMaxBookingAdvanceWeeks() * 7 : 14;

    // Generate date labels for the next 'maxDays' days
    for (int i = 0; i < maxDays; i++) {
      Date date = calendar.getTime();
      String label;

      if (i == 0) {
        label = "Today";
      } else if (i == 1) {
        label = "Tomorrow";
      } else {
        label = dayNameFormat.format(date);
      }

      String day = dayFormat.format(date);
      String month = monthFormat.format(date);
  %>
  <div style="border: 1px solid #ccc; padding: 10px; border-radius: 5px;">
    <strong><%= label %></strong><br>
    <span><%= day %> <%= month %></span>
  </div>
  <%
      calendar.add(Calendar.DATE, 1); // Move to the next day
    }
  %>
</div>

<%
  // Retrieve start and end times from the session
  String startTimeStr = (String) session.getAttribute("startTime");
  String endTimeStr = (String) session.getAttribute("endTime");

  // Check for null session values
  if (startTimeStr != null && endTimeStr != null) {
    try {
      LocalTime start = LocalTime.parse(startTimeStr);
      LocalTime end = LocalTime.parse(endTimeStr);

      // Calculate the time duration (in hours)
      long timeDuration = Duration.between(start, end).toHours();
%>
<p>Available Time Slots</p>
<ul>
  <%
    // Generate time slots
    LocalTime currentSlot = start;

    for (int i = 0; i < timeDuration+1; i++) {
      LocalTime slotEnd = currentSlot.plusHours(1);
      if (slotEnd.isAfter(end)) {
        slotEnd = end;
      }
  %>
  <li><%= currentSlot.format(java.time.format.DateTimeFormatter.ofPattern("h:mm a")) %> -
    <%= slotEnd.format(java.time.format.DateTimeFormatter.ofPattern("h:mm a")) %></li>
  <%
      currentSlot = currentSlot.plusHours(1);
    }
  %>
</ul>
<%
    } catch (DateTimeParseException e) {
      out.println("<p>Error: Invalid start or end time format.</p>");
    }
  } else {
    out.println("<p>Error: Start and end times are not set.</p>");
  }
%>

</body>
</html>
