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
      <select id="frequency" name="frequency" onchange="toggleCustomOptions()" required>
        <option value="one-time">One time only</option>
        <option value="daily">Daily</option>
        <option value="weekly">Weekly</option>
        <option value="custom">Custom</option>
      </select>

      <div id="customOptions" style="display:none;">
        <p class="sub-label">Repeat on:</p>
        <div class="weekday-selector">
          <label><input type="checkbox" name="weekday" value="MONDAY"> Monday</label>
          <label><input type="checkbox" name="weekday" value="TUESDAY"> Tuesday</label>
          <label><input type="checkbox" name="weekday" value="WEDNESDAY"> Wednesday</label>
          <label><input type="checkbox" name="weekday" value="THURSDAY"> Thursday</label>
          <label><input type="checkbox" name="weekday" value="FRIDAY"> Friday</label>
          <label><input type="checkbox" name="weekday" value="SATURDAY"> Saturday</label>
          <label><input type="checkbox" name="weekday" value="SUNDAY"> Sunday</label>
        </div>

        <p class="sub-label">End recurrence:</p>
        <div class="end-recurrence">
          <select id="endRecurrenceType" name="endRecurrenceType" onchange="toggleEndDateOptions()">
            <option value="max">Maximum allowed (until <%= request.getAttribute("maxEndDateFormatted") %>)</option>
            <option value="custom-date">On specific date</option>
            <option value="occurrences">After number of occurrences</option>
          </select>

          <div id="endDateOption" style="display:none; margin-top: var(--spacing-md);">
            <input type="date" name="endDate" id="endDate" min="<%= selectedDate %>">
          </div>

          <div id="occurrencesOption" style="display:none; margin-top: var(--spacing-md);">
            <input type="number" name="occurrences" id="occurrences" min="1" max="<%= request.getAttribute("maxOccurrences") %>" value="1">
            <span>occurrence(s)</span>
          </div>
        </div>
      </div>
    </div>

    <div class="button-group">
      <button type="button" class="btn-cancel" onclick="window.location.href='bookSession'">Cancel</button>
      <button type="submit" class="btn-confirm">Confirm Booking</button>
    </div>
  </form>
</div>

<!-- Success Confirmation Modal -->
<div id="successModal" class="modal">
  <div class="card-modal">
    <div class="card-modal-header">
      <h3 style="color: var(--success-color);"><i class="fas fa-check-circle"></i> Booking Confirmed</h3>
    </div>
    <div class="card-modal-body">
      <p>Your session has been successfully booked!</p>
      <p class="text-muted">Thank you for scheduling with us.</p>
    </div>
    <div class="flex justify-center mt-3">
      <button class="btn btn-success" onclick="redirectToConfirmation()">Okay</button>
    </div>
  </div>
</div>

<script>
  function toggleCustomOptions() {
    const frequency = document.getElementById('frequency').value;
    const customOptions = document.getElementById('customOptions');

    if (frequency === 'custom') {
      customOptions.style.display = 'block';
      // Pre-select the day of the selected date
      const selectedDate = new Date('<%= selectedDate %>');
      const dayOfWeek = selectedDate.getDay();
      const weekdayNames = ['SUNDAY', 'MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY'];
      const checkboxes = document.querySelectorAll('input[name="weekday"]');
      checkboxes.forEach(cb => {
        if (cb.value === weekdayNames[dayOfWeek]) {
          cb.checked = true;
        }
      });
    } else {
      customOptions.style.display = 'none';
    }
  }

  function toggleEndDateOptions() {
    const endType = document.getElementById('endRecurrenceType').value;
    document.getElementById('endDateOption').style.display = endType === 'custom-date' ? 'block' : 'none';
    document.getElementById('occurrencesOption').style.display = endType === 'occurrences' ? 'block' : 'none';

    // Set max date for endDate
    if (endType === 'custom-date') {
      const endDate = document.getElementById('endDate');
      endDate.max = '<%= request.getAttribute("maxEndDate") %>';

      // Ensure minimum date is today
      const today = new Date().toISOString().split('T')[0];
      if (endDate.value < today) {
        endDate.value = today;
      }
    }
  }

  // Show success modal after form submission
  function showSuccessModal() {
    const successModal = document.getElementById('successModal');
    successModal.style.display = 'block';
  }

  // Redirect to confirmation page
  function redirectToConfirmation() {
    window.location.href = '/bookSessionConfirmation';
  }

  // Update your existing window.onclick function
  window.onclick = function(event) {
    const successModal = document.getElementById('successModal');
    if (event.target == successModal) {
      successModal.style.display = 'none';
    }
  }

  document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('form');
    if (form) {
      form.addEventListener('submit', function(e) {
        // Don't prevent default submission - let it go through normally
        // Instead, just show the success modal
        showSuccessModal();

        // Allow form to submit naturally after a short delay
        return true;
      });
    }
  });

  // Initialize date picker with proper restrictions when the page loads
  window.onload = function() {
    const endDatePicker = document.getElementById('endDate');
    if (endDatePicker) {
      const today = new Date().toISOString().split('T')[0];
      endDatePicker.min = today;
      endDatePicker.max = '<%= request.getAttribute("maxEndDate") %>';
    }
  };
</script>
</body>
</html>