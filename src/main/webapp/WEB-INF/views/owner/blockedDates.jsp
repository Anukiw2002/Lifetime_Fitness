<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Blocked Dates</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/blockDates.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
<jsp:include page="../common/verticalNavBar.jsp" />
<div class="main-content">
  <div class="container">
    <div class="flex justify-between items-center mb-4">
      <h2>Blocked Dates</h2>
      <button class="btn btn-primary" onclick="location.href='${pageContext.request.contextPath}/booking/constraints'">
        <i class="fas fa-cog"></i> Block Slot
      </button>
    </div>

    <c:if test="${param.status eq 'deleteSuccess'}">
      <div class="alert alert-success mb-3">
        Blocked date successfully removed.
      </div>
    </c:if>

    <div class="card">
      <div class="card-header">
        <h3>All Blocked Time Slots</h3>
      </div>
      <div class="card-body">
        <c:choose>
          <c:when test="${empty allBlockedDates}">
            <div class="empty-state">
              <i class="fas fa-calendar-times"></i>
              <h3>No Blocked Dates</h3>
              <p>You haven't blocked any dates or time slots yet.</p>
              <div class="mt-3">
                <button class="btn btn-primary" onclick="location.href='${pageContext.request.contextPath}/booking/constraints'">
                  <i class="fas fa-plus"></i> Block a Date
                </button>
              </div>
            </div>
          </c:when>
          <c:otherwise>
            <div class="grid grid-auto-fit">
              <c:forEach var="date" items="${allBlockedDates}">
                <div class="blocked-date-card">
                  <div class="blocked-date-header">
                    <div>
                      <c:choose>
                        <c:when test="${date.fullDay}">
                          <span class="badge badge-fullday">Full Day Block</span>
                        </c:when>
                        <c:otherwise>
                          <span class="badge badge-partial">Partial Day Block</span>
                        </c:otherwise>
                      </c:choose>
                    </div>
                    <form action="${pageContext.request.contextPath}/booking/deleteBlockedDate" method="POST" onsubmit="return confirm('Are you sure you want to delete this blocked date?');">
                      <input type="hidden" name="blockDateId" value="${date.blockId}">
                      <button type="submit" class="delete-btn" title="Delete this blocked date">
                        <i class="fas fa-trash-alt"></i>
                      </button>
                    </form>
                  </div>
                  <div class="blocked-date-body">
                    <div>
                      <div class="date-label">Date</div>
                      <div class="date-value">
                        <i class="fas fa-calendar-day"></i>
                          ${date.blockDate}
                      </div>
                    </div>

                    <c:if test="${not date.fullDay}">
                      <div class="time-row">
                        <div class="time-block">
                          <div class="date-label">Start Time</div>
                          <div class="date-value">
                            <i class="fas fa-clock"></i>
                              ${date.startTime}
                          </div>
                        </div>
                        <div class="time-block">
                          <div class="date-label">End Time</div>
                          <div class="date-value">
                            <i class="fas fa-hourglass-end"></i>
                              ${date.endTime}
                          </div>
                        </div>
                      </div>
                    </c:if>

                    <div class="reason-section">
                      <div class="date-label">Reason</div>
                      <div>
                        <i class="fas fa-comment-alt"></i>
                          ${date.reason}
                      </div>
                    </div>
                  </div>
                </div>
              </c:forEach>
            </div>
          </c:otherwise>
        </c:choose>
      </div>
    </div>
  </div>
</div>

<!-- Delete confirmation modal -->
<div class="modal" id="deleteModal">
  <div class="card-modal">
    <div class="card-modal-header">
      <h3><i class="fas fa-exclamation-triangle"></i> Delete Blocked Date</h3>
      <button class="delete-icon" onclick="closeDeleteModal()">
        <i class="fas fa-times"></i>
      </button>
    </div>
    <div class="card-modal-body">
      <p>Are you sure you want to delete this blocked date? This action cannot be undone.</p>
      <form id="deleteForm" action="${pageContext.request.contextPath}/booking/deleteBlockedDate" method="POST">
        <input type="hidden" id="deleteBlockDateId" name="blockDateId" value="">
        <div class="flex justify-end gap-md mt-3">
          <button type="button" class="btn btn-secondary" onclick="closeDeleteModal()">Cancel</button>
          <button type="submit" class="btn btn-danger">Delete</button>
        </div>
      </form>
    </div>
  </div>
</div>

<script>
  // Delete modal functions
  function openDeleteModal(id) {
    document.getElementById('deleteBlockDateId').value = id;
    document.getElementById('deleteModal').style.display = 'block';
  }

  function closeDeleteModal() {
    document.getElementById('deleteModal').style.display = 'none';
  }

  // Close modal when clicking outside of it
  window.onclick = function(event) {
    const modal = document.getElementById('deleteModal');
    if (event.target === modal) {
      closeDeleteModal();
    }
  }
</script>
</body>
</html>