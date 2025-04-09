<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, java.text.*" %>
<%@ page import="org.example.demo2.model.BookingConstraints" %>
<%@ page import="org.example.demo2.dao.BookingConstraintsDAO" %>
<%@ page import="org.example.demo2.dao.BookSessionDAO" %>
<%@ page import="java.util.Calendar" %>
<html>
<head>
  <title>Book a session</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bookSession.css">

  <script>
    // Function to handle date click and fetch available time slots via AJAX
    // Function to handle date click and fetch available time slots via AJAX
    function selectDate(selectedDate) {
      var xhr = new XMLHttpRequest();
      xhr.open("POST", "bookSession", true);
      xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
      xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
          // Parse the response (HTML) and update the slots section
          document.getElementById("timeSlots").innerHTML = xhr.responseText;

          // Call formatTimeSlots() to apply the grid styling
          formatTimeSlots();
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

    // Function to send the selected date and the slot to the bookingConfirmation.jsp page
    function selectSlot(selectedDate, selectedSlot) {
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

    // Format the time slots into a grid
    function formatTimeSlots() {
      const timeSlotsContainer = document.getElementById("timeSlots");
      const content = timeSlotsContainer.innerHTML;

      // Check if it's already in our grid format
      if (timeSlotsContainer.querySelector('.time-slots-grid')) {
        return;
      }

      // Parse out the time slots from the response HTML
      // This assumes the server returns time slots as elements with onclick attributes
      // containing "selectSlot" function calls

      // Create a temporary container to parse the HTML
      const tempDiv = document.createElement('div');
      tempDiv.innerHTML = content;

      // Look for elements with selectSlot function calls
      const slotElements = tempDiv.querySelectorAll('[onclick*="selectSlot"]');

      if (slotElements.length > 0) {
        // Create the grid container
        const grid = document.createElement('div');
        grid.className = 'time-slots-grid';

        // Process each slot element
        slotElements.forEach(slotElement => {
          // Extract the onclick attribute
          const onclickAttr = slotElement.getAttribute('onclick');

          // Create a new time slot element with proper styling
          const timeSlot = document.createElement('div');
          timeSlot.className = 'time-slot';
          timeSlot.setAttribute('onclick', onclickAttr);
          timeSlot.textContent = slotElement.textContent.trim();

          // Add to the grid
          grid.appendChild(timeSlot);
        });

        // Replace the content with our properly formatted grid
        timeSlotsContainer.innerHTML = '';
        timeSlotsContainer.appendChild(grid);
      } else {
        // If no slots were found, check if there's a message like "No available slots"
        const noSlotsMessage = tempDiv.textContent.trim();
        if (noSlotsMessage) {
          timeSlotsContainer.innerHTML = `<p class="text-muted">${noSlotsMessage}</p>`;
        }
      }
    }

  </script>
</head>
<body>
<div class="main-content">
  <jsp:include page="../client/clientVerticalNavbar.jsp" />
  <div class="container">
    <div class="page-header">
      <h1>Book a Gym Session</h1>
      <p class="text-muted">Select your preferred date and time slot</p>
    </div>

    <div class="card mb-4">
      <div class="card-header">
        <h3>Select a Date</h3>
        <p class="text-muted">Scroll horizontally to see more available dates</p>
      </div>
      <div class="card-body">
        <div class="dates-container">
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
            <div class="date-label"><%= label %></div>
            <div class="date-number"><%= day %> <%= month %></div>
          </div>
          <%
              calendar.add(Calendar.DATE, 1); // Move to the next day
            }
          %>
        </div>
      </div>
    </div>

    <div class="card">
      <div class="card-header">
        <h3>Available Time Slots</h3>
        <p>Filling Fast          Almost Full              Fully Booked</p>
      </div>
      <div class="card-body">
        <div id="timeSlots" class="time-slots-container">
          <!-- This will be dynamically updated -->
          <p class="text-muted">Please select a date to view available time slots</p>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>