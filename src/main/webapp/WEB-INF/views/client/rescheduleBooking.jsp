<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, java.text.*" %>
<%@ page import="org.example.demo2.model.BookingConstraints" %>
<%@ page import="org.example.demo2.dao.BookingConstraintsDAO" %>
<%@ page import="java.util.Calendar" %>

<html>
<head>
  <title>Reschedule Session</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bookSession.css">

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

          // Format time slots to match the grid layout
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

      // Update the form's hidden date field
      document.getElementById("newDate").value = selectedDate;
    }

    // Function to send the selected date and the slot
    function selectSlot(selectedDate, selectedTime, displayTime) {
      // Update the form's hidden fields
      document.getElementById("newDate").value = selectedDate;
      document.getElementById("newTime").value = selectedTime;
      document.getElementById("newTimeDisplay").value = displayTime;

      // Show the confirmation modal instead of the bottom section
      var formattedDate = new Date(selectedDate).toLocaleDateString('en-US',
              { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' });
      showRescheduleModal(formattedDate, displayTime);
    }

    // Show the reschedule confirmation modal
    function showRescheduleModal(formattedDate, displayTime) {
      document.getElementById('selectedDateTimeDisplay').textContent = formattedDate + " at " + displayTime;
      document.getElementById('rescheduleModal').style.display = 'block';
    }

    // Hide the reschedule confirmation modal
    function hideRescheduleModal() {
      document.getElementById('rescheduleModal').style.display = 'none';
    }

    // Show success modal after reschedule
    function showRescheduleSuccessModal() {
      document.getElementById('rescheduleSuccessModal').style.display = 'block';
    }

    // Modify the window.onclick handler to include the new modal
    window.onclick = function(event) {
      if (event.target == document.getElementById('rescheduleModal')) {
        hideRescheduleModal();
      }
      if (event.target == document.getElementById('rescheduleSuccessModal')) {
        document.getElementById('rescheduleSuccessModal').style.display = 'none';
      }
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

    // Format the time slots into a grid, matching the styling from bookSession.jsp
    function formatTimeSlots() {
      const timeSlotsContainer = document.getElementById("timeSlots");
      const content = timeSlotsContainer.innerHTML;

      // Check if it's already in our grid format
      if (timeSlotsContainer.querySelector('.time-slots-grid')) {
        return;
      }

      // Create a temporary container to parse the HTML
      const tempDiv = document.createElement('div');
      tempDiv.innerHTML = content;

      // Look for elements with data-availability attribute or regular list items
      const slotElements = tempDiv.querySelectorAll('li');

      if (slotElements.length > 0) {
        // Create the grid container
        const grid = document.createElement('div');
        grid.className = 'time-slots-grid';

        // Process each slot element
        slotElements.forEach(slotElement => {
          // Extract the onclick attribute
          const onclickAttr = slotElement.getAttribute('onclick');
          const availability = slotElement.getAttribute('data-availability') || "Available";

          // Create a new time slot element with proper styling
          const timeSlot = document.createElement('div');
          timeSlot.className = 'time-slot';

          // Add availability class
          if (availability === "Available") {
            timeSlot.classList.add('availability-available');
          } else if (availability === "Filling Fast") {
            timeSlot.classList.add('availability-filling-fast');
          } else if (availability === "Almost Full") {
            timeSlot.classList.add('availability-almost-full');
          } else if (availability === "Fully Booked") {
            timeSlot.classList.add('availability-fully-booked');
          } else if (availability === "Already Booked") {
            timeSlot.classList.add('availability-already-booked');
          }

          if (onclickAttr) {
            timeSlot.setAttribute('onclick', onclickAttr);
            timeSlot.style.cursor = onclickAttr.includes('alreadyBookedAlert') ? 'not-allowed' : 'pointer';
          } else {
            timeSlot.style.cursor = 'not-allowed';
          }

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

    function confirmReschedule() {
      // Hide the confirmation modal first
      hideRescheduleModal();

      // Show the success modal
      showRescheduleSuccessModal();

      // Set a timeout to allow the user to see the success message
      // before redirecting through form submission
      setTimeout(function() {
        document.getElementById('rescheduleForm').submit();
      }, 3500);
    }

    // Function to show alert when user tries to select a slot they already booked
    function alreadyBookedAlert() {
      alert("You have already booked this time slot. Please select another time slot.");
    }

    // Close modal when clicking outside
    window.onclick = function(event) {
      if (event.target == document.getElementById('rescheduleModal')) {
        hideRescheduleModal();
      }
    }
  </script>
</head>
<body>
<div class="main-content">
  <jsp:include page="../client/clientVerticalNavbar.jsp" />
  <div class="container">
    <div class="page-header">
      <h1>Reschedule Gym Session</h1>
      <p class="text-muted">Select a new date and time for your session</p>
    </div>

    <% if (session.getAttribute("originalDate") != null && session.getAttribute("originalTime") != null) {
      java.sql.Date origDate = (java.sql.Date) session.getAttribute("originalDate");
      java.sql.Time origTime = (java.sql.Time) session.getAttribute("originalTime");

      SimpleDateFormat dateFormatter = new SimpleDateFormat("EEEE, MMMM d, yyyy");
      SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a");

      String formattedDate = dateFormatter.format(origDate);
      String formattedTime = timeFormatter.format(origTime);
    %>
    <div class="card mb-4">
      <div class="card-header">
        <h3>Original Booking</h3>
      </div>
      <div class="card-body">
        <p>Your currently scheduled session: <strong><%= formattedDate %> at <%= formattedTime %></strong></p>
        <p class="text-muted">Please select a new date and time below</p>
      </div>
    </div>
    <% } %>

    <% if (request.getAttribute("error") != null) { %>
    <div class="alert alert-danger">
      <%= request.getAttribute("error") %>
    </div>
    <% } %>

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
        <div class="availability-legend">
          <span><span class="availability-indicator indicator-available"></span> Available</span>
          <span><span class="availability-indicator indicator-filling-fast"></span> Filling Fast</span>
          <span><span class="availability-indicator indicator-almost-full"></span> Almost Full</span>
          <span><span class="availability-indicator indicator-fully-booked"></span> Fully Booked</span>
          <span><span class="availability-indicator indicator-already-booked"></span> Already Booked</span>
        </div>
      </div>
      <div class="card-body">
        <div id="timeSlots" class="time-slots-container">
          <!-- This will be dynamically updated -->
          <p class="text-muted">Please select a date to view available time slots</p>
        </div>
      </div>
    </div>

    <!-- Hidden form fields to store selected values -->
    <form id="rescheduleForm" action="rescheduleSession" method="post" style="display: none;">
      <input type="hidden" id="bookingId" name="bookingId" value="<%= session.getAttribute("bookingId") %>">
      <input type="hidden" id="newDate" name="newDate" value="">
      <input type="hidden" id="newTime" name="newTime" value="">
      <input type="hidden" id="newTimeDisplay" name="newTimeDisplay" value="">
    </form>

    <!-- Reschedule Confirmation Modal -->
    <div id="rescheduleModal" class="modal">
      <div class="card-modal">
        <div class="card-modal-header">
          <h3><i class="fas fa-calendar-alt"></i> Confirm Reschedule</h3>
        </div>
        <div class="card-modal-body">
          <p>You are about to reschedule your session to: <strong><span id="selectedDateTimeDisplay"></span></strong></p>
          <p class="text-muted">Please confirm your new booking time.</p>
        </div>
        <div class="flex justify-end gap-md">
          <button class="btn btn-secondary" onclick="hideRescheduleModal()">Cancel</button>
          <button type="button" class="btn btn-primary" onclick="confirmReschedule();">
            Confirm Reschedule
          </button>
        </div>
      </div>
    </div>

    <div id="rescheduleSuccessModal" class="modal">
      <div class="card-modal">
        <div class="card-modal-header">
          <h3 style="color: var(--success-color);"><i class="fas fa-check-circle"></i> Session Rescheduled</h3>
        </div>
        <div class="card-modal-body">
          <p>Your session has been successfully rescheduled!</p>
          <p class="text-muted">Your calendar has been updated with the new time.</p>
        </div>
        <div class="flex justify-center mt-3">
          <button class="btn btn-success" onclick="window.location.href='clientBookings'">View My Sessions</button>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>