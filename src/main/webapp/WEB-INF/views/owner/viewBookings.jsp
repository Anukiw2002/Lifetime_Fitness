<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

        for (var i = 0; i < rows.length; i++) {
            var row = rows[i];
            var date = row.cells[1].textContent.toLowerCase(); // DATE column
            var status = row.cells[3].textContent.trim().toLowerCase(); // STATUS column

            var showRow = true;

            // Apply timeframe filter
            if (timeFrameFilter !== "") {
                var currentDate = new Date();
                var bookingDate = new Date(date);

                if (timeFrameFilter === "today") {
                    if (bookingDate.toDateString() !== currentDate.toDateString()) {
                        showRow = false;
                    }
                } else if (timeFrameFilter === "week") {
                    // Get start and end of current week
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
                    // For "upcoming", show all booked sessions
                    // This assumes that "booked" or any status that isn't "rescheduled" or "cancelled" is upcoming
                    if (status === "rescheduled" || status === "cancelled") {
                        showRow = false;
                    }
                } else if (status !== statusFilter) {
                    showRow = false;
                }
            }

            // Show or hide row based on filters
            if (showRow) {
                row.style.display = "";
            } else {
                row.style.display = "none";
            }
        }
    }

    // Reset all filters
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
<jsp:include page="../common/verticalNavBar.jsp" />
<div class="main-content">
    <div class="container">
        <div class="flex justify-end items-center mb-4">
            <button class="btn btn-primary" onclick="location.href='/booking/constraints'">
                <i class="fas fa-calendar-xmark"></i> Booking Constraints
            </button>
        </div>
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
                            <th>DATE</th>
                            <th>Time</th>
                            <th>STATUS</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="booking" items="${allSessions}">
                            <tr>
                                <td>${booking.fname} ${booking.lname}</td>
                                <td>${booking.date}</td>
                                <td>${booking.timeSlot}</td>
                                <td><span class="status-badge ${booking.status}">${booking.status}</span></td>
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