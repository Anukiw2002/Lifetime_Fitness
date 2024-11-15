<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Bookings Management</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/viewBookings.css">
</head>
<body>
<jsp:include page="../common/verticalNavBar.jsp" />
<div class="main-content">
    <!-- Container for filters and booking content -->
    <div class="container">

        <!-- Filter section -->
        <div class="filters">
            <div class="filter-dropdown">
                <select id="instructorFilter">
                    <option value="">Instructor</option>
                    <option value="bonnie">Bonnie Green</option>
                    <!-- Add more instructors as needed -->
                </select>
            </div>

            <div class="filter-dropdown">
                <select id="timeFrameFilter">
                    <option value="">Time Frame</option>
                    <option value="today">Today</option>
                    <option value="week">This Week</option>
                    <option value="month">This Month</option>
                    <option value="30days">Next 30 Days</option>
                    <option value="custom">Custom Range</option>
                </select>
            </div>

            <div class="filter-dropdown">
                <select id="membershipFilter">
                    <option value="">Membership Type</option>
                    <option value="platinum-gents">Platinum - Gents</option>
                    <option value="platinum-ladies">Platinum - Ladies</option>
                    <option value="platinum-couples">Platinum - Couples</option>
                    <option value="gold-gents">Gold - Gents</option>
                    <option value="gold-ladies">Gold - Ladies</option>
                    <option value="silver-6">Silver - 6 months</option>
                    <option value="silver-3">Silver - 3 months</option>
                    <option value="silver-1">Silver - 1 month</option>
                </select>
            </div>

            <div class="filter-dropdown">
                <select id="statusFilter">
                    <option value="">Session Status</option>
                    <option value="upcoming">Upcoming</option>
                    <option value="rescheduled">Rescheduled</option>
                    <option value="cancelled">Cancelled</option>
                    <option value="completed">Completed</option>
                </select>
            </div>

            <button class="clear-filters" onclick="clearFilters()">Clear Filters</button>
        </div>

        <!-- Bookings table section -->
        <div class="bookings-container">
            <h2 class="bookings-title">Bookings</h2>
            <p class="bookings-subtitle">List of total number of bookings</p>

            <table id="bookingsTable">
                <thead>
                <tr>
                    <th>Client</th>
                    <th>DATE</th>
                    <th>Time</th>
                    <th>Instructor</th>
                    <th>BOOKING ID</th>
                    <th>STATUS</th>
                </tr>
                </thead>
                <tbody>
                <!-- Sample data - you would typically populate this dynamically -->
                <tr>
                    <td>Rachel Green</td>
                    <td>Oct 23, 2024</td>
                    <td>4:00 - 5:00 am</td>
                    <td>Bonnie Green</td>
                    <td>12234565</td>
                    <td><span class="status-badge status-in-progress">In progress</span></td>
                </tr>
                <!-- Add more rows as needed -->
                </tbody>
            </table>
        </div>
    </div>
</div>

<script>
    function clearFilters() {
        const selects = document.querySelectorAll('select');
        selects.forEach(select => {
            select.value = '';
        });
        filterBookings();
    }

    function filterBookings() {
        const instructor = document.getElementById('instructorFilter').value;
        const timeFrame = document.getElementById('timeFrameFilter').value;
        const membership = document.getElementById('membershipFilter').value;
        const status = document.getElementById('statusFilter').value;

        // Add your filtering logic here
        console.log('Filtering with:', { instructor, timeFrame, membership, status });
    }

    document.querySelectorAll('select').forEach(select => {
        select.addEventListener('change', filterBookings);
    });

    document.querySelectorAll('.copy-icon').forEach(icon => {
        icon.addEventListener('click', function() {
            const bookingId = this.getAttribute('data-booking-id');
            navigator.clipboard.writeText(bookingId)
                .then(() => alert('Booking ID copied!'))
                .catch(err => console.error('Failed to copy:', err));
        });
    });
</script>
</body>
</html>
