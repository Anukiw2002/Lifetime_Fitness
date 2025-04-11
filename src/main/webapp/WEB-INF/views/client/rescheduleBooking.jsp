<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, java.text.*" %>
<%@ page import="org.example.demo2.model.BookingConstraints" %>
<%@ page import="org.example.demo2.dao.BookingConstraintsDAO" %>
<%@ page import="java.util.Calendar" %>

<html>
<head>
  <title>Reschedule Session</title>
  <script>
    // Function to handle date click and fetch available time slots via AJAX
    function selectDate(selectedDate) {
      var xhr = new XMLHttpRequest();
      xhr.open("POST", "rescheduleSession", true);
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

      // Update the form's hidden date field
      document.getElementById("newDate").value = selectedDate;
    }

    // Function to send the selected date and the slot
    function selectSlot(selectedDate, selectedTime, displayTime) {
      // Update the form's hidden fields
      document.getElementById("newDate").value = selectedDate;
      document.getElementById("newTime").value = selectedTime;
      document.getElementById("newTimeDisplay").value = displayTime;

      // Show the confirmation section
      document.getElementById("confirmationSection").style.display = "block";
      document.getElementById("selectedDateTimeDisplay").innerHTML =
              new Date(selectedDate).toLocaleDateString('en-US', { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' })
              + " at " + displayTime;
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
  <style>
    .date-box {
      border: 1px solid #ccc;
      padding: 10px;
      border-radius: 5px;
      cursor: pointer;
      margin-right: 10px;
    }
    .date-box.selected {
      background-color: #e0e0ff;
      border-color: #6060ff;
    }
    .container {
      max-width: 800px;
      margin: 0 auto;
      padding: 20px;
    }
    .date-container {
      display: flex;
      overflow-x: auto;
      padding: 10px 0;
      margin-bottom: 20px;
    }
    #timeSlots ul {
      list-style-type: none;
      padding: 0;
    }
    #timeSlots li {
      padding: 10px;
      margin: 5px 0;
      background-color: #f0f0f0;
      border-radius: 4px;
    }
    #timeSlots li:hover {
      background-color: #e0e0ff;
    }
    .error-message {
      color: red;
      margin-bottom: 15px;
    }
    #confirmationSection {
      display: none;
      margin-top: 30px;
      padding: 15px;
      background-color: #f9f9f9;
      border: 1px solid #ddd;
      border-radius: 5px;
    }
    .btn {
      display: inline-block;
      padding: 8px 16px;
      margin-right: 10px;
      background-color: #4CAF50;
      color: white;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      text-decoration: none;
    }
    .btn-cancel {
      background-color: #f44336;
    }
  </style>
</head>
<body>
<div class="container">
  <h2>Reschedule Booking</h2>
  <%
    // Format the original booking details if available
    if (session.getAttribute("originalDate") != null && session.getAttribute("originalTime") != null) {
      java.sql.Date origDate = (java.sql.Date) session.getAttribute("originalDate");
      java.sql.Time origTime = (java.sql.Time) session.getAttribute("originalTime");

      SimpleDateFormat dateFormatter = new SimpleDateFormat("EEEE, MMMM d, yyyy");
      SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a");

      String formattedDate = dateFormatter.format(origDate);
      String formattedTime = timeFormatter.format(origTime);
  %>
  <div class="original-booking-info">
    <p>Original session: <strong><%= formattedDate %> at <%= formattedTime %></strong></p>
    <p>Select a new date and time for your gym session.</p>
  </div>
  <% } else { %>
  <p>Select a new date and time for your gym session.</p>
  <% } %>

  <% if (request.getAttribute("error") != null) { %>
  <div class="error-message"><%= request.getAttribute("error") %></div>
  <% } %>

  <div class="date-container">
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
    <div id="date-<%= formattedDate %>" class="date-box" onClick="selectDate('<%= formattedDate %>')">
      <strong><%= label %></strong><br>
      <span><%= day %> <%= month %></span>
    </div>
    <%
        calendar.add(Calendar.DATE, 1); // Move to the next day
      }
    %>
  </div>

  <p>Available Time Slots:</p>
  <div id="timeSlots"><!-- This will be dynamically updated --></div>

  <!-- Confirmation Section -->
  <div id="confirmationSection">
    <h3>Confirm Reschedule</h3>
    <p>You are about to reschedule your session to: <span id="selectedDateTimeDisplay"></span></p>

    <form action="rescheduleSession" method="post">
      <input type="hidden" id="bookingId" name="bookingId" value="<%= session.getAttribute("bookingId") %>">
      <input type="hidden" id="newDate" name="newDate" value="">
      <input type="hidden" id="newTime" name="newTime" value="">
      <input type="hidden" id="newTimeDisplay" name="newTimeDisplay" value="">

      <button type="submit" class="btn">Confirm Reschedule</button>
      <a href="clientBookings" class="btn btn-cancel">Cancel</a>
    </form>
  </div>
</div>
</body>
</html>