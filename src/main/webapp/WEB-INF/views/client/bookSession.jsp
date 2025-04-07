<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, java.text.*" %>
<%@ page import="org.example.demo2.model.BookingConstraints" %>
<%@ page import="org.example.demo2.dao.BookingConstraintsDAO" %>

<html>
<head>
  <title>Book a session</title>
  <script>
      // Function to handle date click and fetch available time slots via AJAX
      function selectDate(selectedDate) {
      var xhr = new XMLHttpRequest();
      xhr.open("POST", "bookSession", true);
      xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
      xhr.onreadystatechange = function() {
      if (xhr.readyState == 4 && xhr.status == 200) {
      // Parse the response (HTML) and update the slots section
      document.getElementById("timeSlots").innerHTML = xhr.responseText;
    }
    };
      xhr.send("selectedDate=" + selectedDate);

      // Highlight the selected date
      var dateElements = document.getElementsByClassName("date-box");
      for (var i = 0; i < dateElements.length; i++) {
      dateElements[i].classList.remove("selected");
    }
      document.getElementById("date-" + selectedDate).classList.add("selected");
    }

      //function to send the selected date and the slot to the bookingConfirmation.jsp page
      function selectSlot(selectedDate, selectedSlot){
      window.location.href = "bookSessionConfirmation?selectedDate=" + encodeURIComponent(selectedDate) + "&selectedSlot=" + encodeURIComponent(selectedSlot);
    }

      // Load today's slots when the page loads
      window.onload = function() {
      // Get today's date in yyyy-MM-dd format
      var today = new Date();
      var dd = String(today.getDate()).padStart(2, '0');
      var mm = String(today.getMonth() + 1).padStart(2, '0'); // January is 0!
      var yyyy = today.getFullYear();
      var todayFormatted = yyyy + '-' + mm + '-' + dd;

      // Auto-select today's date
      selectDate(todayFormatted);
    }
  </script>
</head>
<body>
<h2>Book a Gym Session</h2>
<p>Select a Date</p>
<div style="display: flex; gap: 10px;">
  <%
    SimpleDateFormat dayNameFormat = new SimpleDateFormat("EEEE");
    SimpleDateFormat dayFormat = new SimpleDateFormat("d");
    SimpleDateFormat monthFormat = new SimpleDateFormat("MMM");
    SimpleDateFormat fullDateFormat = new SimpleDateFormat("yyyy-MM-dd");

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
      String formattedDate = fullDateFormat.format(date);
  %>
  <div id="date-<%= formattedDate %>" class="date-box" style="border: 1px solid #ccc; padding: 10px; border-radius: 5px; cursor: pointer;" onClick="selectDate('<%= formattedDate %>')">
    <strong><%= label %></strong><br>
    <span><%= day %> <%= month %></span>
  </div>
  <%
      calendar.add(Calendar.DATE, 1); // Move to the next day
    }
  %>
</div>

<p>Available Time Slots:</p>
<div id="timeSlots"></div> <!-- This will be dynamically updated -->

</body>
</html>
