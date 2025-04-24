<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>Client Sessions</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/clientSession.css">
</head>
<body>
<div class="main-content">
  <jsp:include page="../client/clientVerticalNavbar.jsp" />

  <div class="container">
    <h1 class="text-center mb-4">Client Sessions</h1>

    <div class="grid grid-2">
      <!-- Upcoming Sessions-->
      <div class="card">
        <div class="card-header">
          <h2 class="mb-0">Upcoming Sessions</h2>
        </div>
        <div id="upcomingSessionsContainer">
          <c:forEach var="formattedSession" items="${formattedSessions}" varStatus="status">
            <div class="card mb-4 ${status.index >= 10 ? 'hidden-session' : ''}">
              <div class="card-body">
                <div class="session-date">${formattedSession.formattedDate}</div>
                <div class="session-time">${formattedSession.startTime} - ${formattedSession.endTime}</div>
                <div class="flex gap-md justify-center mt-3">
                  <button class="btn btn-danger" onclick="showDeleteModal('${formattedSession.bookingId}', '${formattedSession.formattedDate} ${formattedSession.startTime}')">
                    Cancel
                  </button>
                  <a href="rescheduleSession?bookingId=${formattedSession.bookingId}" class="btn btn-secondary" style="text-decoration: none;">Reschedule</a>
                </div>
              </div>
            </div>
          </c:forEach>
          <c:if test="${fn:length(formattedSessions) > 10}">
            <div class="text-center mt-3">
              <button id="viewMoreUpcoming" class="btn btn-outline-custom">View More</button>
            </div>
          </c:if>
        </div>
      </div>

      <!-- Completed Sessions -->
      <div class="card">
        <div class="card-header">
          <h2 class="mb-0">Completed Sessions</h2>
        </div>
        <div class="card-body">
          <div id="completedSessionsContainer">
            <c:forEach var="session" items="${workoutSessions}" varStatus="status">
              <div class="completed-session ${status.index >= 10 ? 'hidden-session' : ''}">
                <div class="completed-date">
                  <fmt:formatDate value="${session.started_at}" pattern="MMMM d, yyyy" />
                </div>
                <div class="workout-name">${session.workoutName}</div>
                <div class="workout-duration">Duration: ${session.duration} minutes</div>
              </div>
            </c:forEach>
            <c:if test="${fn:length(workoutSessions) > 10}">
              <div class="text-center mt-3">
                <button id="viewMoreCompleted" class="btn btn-outline-custom">View More</button>
              </div>
            </c:if>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<!-- Delete Confirmation Modal -->
<div id="deleteModal" class="modal">
  <div class="card-modal">
    <div class="card-modal-header">
      <h3><i class="fas fa-exclamation-triangle"></i> Cancel Booking</h3>
    </div>
    <div class="card-modal-body">
      <p>Are you sure you want to cancel your session on <span id="sessionInfo"></span>?</p>
      <p class="text-muted">This action cannot be undone.</p>
    </div>
    <div class="flex justify-end gap-md">
      <button class="btn btn-secondary" onclick="hideDeleteModal()">No, Keep Booking</button>
      <form id="cancelForm" action="cancelBooking" method="POST" style="display: inline;">
        <input type="hidden" id="bookingId" name="bookingId" value="">
        <button type="submit" class="btn btn-danger">Yes, Cancel Booking</button>
      </form>
    </div>
  </div>
</div>

<script>
  // Get the modal
  const modal = document.getElementById('deleteModal');

  // Show the delete confirmation modal
  function showDeleteModal(bookingId, sessionInfo) {
    document.getElementById('bookingId').value = bookingId;
    document.getElementById('sessionInfo').textContent = sessionInfo;
    modal.style.display = 'block';
  }

  // Hide the delete confirmation modal
  function hideDeleteModal() {
    modal.style.display = 'none';
  }

  // Close modal when clicking outside
  window.onclick = function(event) {
    if (event.target == modal) {
      hideDeleteModal();
    }
  }

  document.addEventListener('DOMContentLoaded', function() {
    // View more upcoming sessions
    const viewMoreUpcomingBtn = document.getElementById('viewMoreUpcoming');
    if (viewMoreUpcomingBtn) {
      viewMoreUpcomingBtn.addEventListener('click', function() {
        const hiddenUpcomingSessions = document.querySelectorAll('#upcomingSessionsContainer .hidden-session');
        hiddenUpcomingSessions.forEach(session => {
          session.classList.remove('hidden-session');
        });
        viewMoreUpcomingBtn.style.display = 'none';
      });
    }

    // View more completed sessions
    const viewMoreCompletedBtn = document.getElementById('viewMoreCompleted');
    if (viewMoreCompletedBtn) {
      viewMoreCompletedBtn.addEventListener('click', function() {
        const hiddenCompletedSessions = document.querySelectorAll('#completedSessionsContainer .hidden-session');
        hiddenCompletedSessions.forEach(session => {
          session.classList.remove('hidden-session');
        });
        viewMoreCompletedBtn.style.display = 'none';
      });
    }
  });
</script>
</body>
</html>