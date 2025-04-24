<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Bookings Management</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/viewBookings.css">
</head>
<script>
    function filterBookings() {
        var timeFrameFilter = document.getElementById("timeFrameFilter").value.toLowerCase();
        var statusFilter = document.getElementById("statusFilter").value.toLowerCase();
        var table = document.getElementById("bookingsTable");
        var rows = table.getElementsByTagName("tbody")[0].getElementsByTagName("tr");

        // Track which date headers have visible bookings
        var dateHeadersWithVisibleBookings = {};
        var currentDateHeader = null;

        // First pass: Apply filters to booking rows and track visible bookings per date
        for (var i = 0; i < rows.length; i++) {
            var row = rows[i];

            // Process date headers
            if (row.classList.contains("date-header")) {
                currentDateHeader = row;
                continue;
            }

            // Process booking rows
            var dateStr = row.getAttribute("data-date");
            var status = row.cells[2].textContent.trim().toLowerCase(); // Adjust the index to match your table structure

            var showRow = true;

            // Apply timeframe filter
            if (timeFrameFilter !== "") {
                var currentDate = new Date();
                var bookingDate = new Date(dateStr); // Make sure date format is parseable

                if (timeFrameFilter === "today") {
                    if (bookingDate.toDateString() !== currentDate.toDateString()) {
                        showRow = false;
                    }
                } else if (timeFrameFilter === "week") {
                    var startOfWeek = new Date(currentDate);
                    startOfWeek.setDate(currentDate.getDate() - currentDate.getDay());
                    var endOfWeek = new Date(startOfWeek);
                    endOfWeek.setDate(startOfWeek.getDate() + 6);

                    if (bookingDate < startOfWeek || bookingDate > endOfWeek) {
                        showRow = false;
                    }
                } else if (timeFrameFilter === "month") {
                    if (bookingDate.getMonth() !== currentDate.getMonth() ||
                        bookingDate.getFullYear() !== currentDate.getFullYear()) {
                        showRow = false;
                    }
                }
            }

            // Apply status filter
            if (statusFilter !== "") {
                if (statusFilter === "upcoming") {
                    if (status === "rescheduled" || status === "cancelled") {
                        showRow = false;
                    }
                } else if (status !== statusFilter) {
                    showRow = false;
                }
            }

            // Update row visibility
            row.style.display = showRow ? "" : "none";

            // Track which date headers have visible bookings
            if (showRow && currentDateHeader) {
                dateHeadersWithVisibleBookings[currentDateHeader.rowIndex] = true;
            }
        }

        // Second pass: Update date header visibility
        for (var i = 0; i < rows.length; i++) {
            var row = rows[i];
            if (row.classList.contains("date-header")) {
                row.style.display = dateHeadersWithVisibleBookings[row.rowIndex] ? "" : "none";
            }
        }
    }

    function resetFilters() {
        document.getElementById("timeFrameFilter").selectedIndex = 0;
        document.getElementById("statusFilter").selectedIndex = 0;

        var table = document.getElementById("bookingsTable");
        var rows = table.getElementsByTagName("tbody")[0].getElementsByTagName("tr");
        for (var i = 0; i < rows.length; i++) {
            rows[i].style.display = "";
        }
    }
</script>
<body>
<c:choose>
    <c:when test="${sessionScope.userRole == 'instructor'}">
        <jsp:include page="../instructor/instructorVerticalNavbar.jsp" />
    </c:when>
    <c:when test="${sessionScope.userRole == 'owner'}">
        <jsp:include page="../common/verticalNavBar.jsp" />
    </c:when>
</c:choose>
<div class="main-content">
    <div class="container">
        <c:if test="${sessionScope.userRole == 'owner'}">
            <div class="flex justify-end items-center mb-4">
                <button class="btn btn-primary" onclick="location.href='/booking/constraints'">
                    <i class="fas fa-calendar-xmark"></i> Booking Constraints
                </button>
            </div>
        </c:if>
        <!-- Filter section -->
        <div class="card mb-4">
            <div class="card-body">
                <div class="filters">
                    <div class="filter-group">
                        <select id="timeFrameFilter" class="form-control filter-control">
                            <option value="">Time Frame</option>
                            <option value="today">Today</option>
                            <option value="week">This Week</option>
                            <option value="month">This Month</option>
                        </select>

                        <select id="statusFilter" class="form-control filter-control">
                            <option value="">Session Status</option>
                            <option value="upcoming">Upcoming</option>
                            <option value="rescheduled">Rescheduled</option>
                            <option value="cancelled">Cancelled</option>
                        </select>
                    </div>
                    <button class="btn btn-secondary" onclick="filterBookings()">
                        <i class="fas fa-filter"></i> Apply
                    </button>
                    <button class="btn btn-outline-secondary" onclick="resetFilters()">
                        <i class="fas fa-undo"></i> Reset
                    </button>
                </div>
            </div>
        </div>

        <!-- Bookings section -->
        <div class="card">
            <div class="card-header">
                <h2 class="bookings-title">Bookings</h2>
                <p class="bookings-subtitle">List of total number of bookings</p>
            </div>
            <div class="card-body">
                <div class="table-wrapper">
                    <table id="bookingsTable">
                        <thead>
                        <tr>
                            <th>Client</th>
                            <th>Time</th>
                            <th>STATUS</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:set var="lastDate" value="" />
                        <c:forEach var="booking" items="${allSessions}">
                            <c:if test="${lastDate != booking.date}">
                                <tr class="date-header">
                                    <td colspan="4">
                                            ${booking.displayDateLabel}
                                    </td>
                                </tr>

                                <c:set var="lastDate" value="${booking.date}" />
                            </c:if>
                            <tr data-date="${booking.date}">
                                <td>${booking.fname} ${booking.lname}</td>
                                <td>
                                <fmt:formatDate value="${booking.timeSlot}" pattern="hh:mm a" />
                                </td>
                                <td>
                                    <span class="status-badge ${booking.status}">${booking.status}</span>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
