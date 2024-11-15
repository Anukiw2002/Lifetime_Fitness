<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Booking Calendar</title>
    <link rel="stylesheet" href="path/to/calendar.css">
</head>
<body>

<div class="calendar-container">
    <div class="calendar-header">
        <span class="month-nav">&#x25C0;</span>
        <h2>October 2024</h2>
        <span class="month-nav">&#x25B6;</span>
    </div>
    <table class="calendar-table">
        <!-- Day Headers -->
        <tr>
            <th class="time-slot-header">Time Slot</th>
            <c:forEach var="day" begin="3" end="9">
                <th>Sun ${day}</th>
            </c:forEach>
        </tr>

        <!-- Define time slots -->
        <c:set var="timeSlots" value="${['4 am - 5 am', '5 am - 6 am', '6 am - 7 am', '7 am - 8 am', '8 am - 9 am', '9 am - 10 am', '10 am - 11 am', '11 am - 12 pm', '12 pm - 1 pm', '1 pm - 2 pm', '2 pm - 3 pm', '3 pm - 4 pm', '4 pm - 5 pm', '5 pm - 6 pm', '6 pm - 7 pm', '7 pm - 8 pm', '8 pm - 9 pm', '9 pm - 10 pm', '10 pm - 11 pm', '11 pm - 12 am']}" />

        <!-- Generate rows for each time slot -->
        <c:forEach var="timeSlot" items="${timeSlots}">
            <tr>
                <td class="time-slot">${timeSlot}</td>
                <c:forEach var="day" begin="3" end="9">
                    <td class="slot available">
                        <a href="book?date=2024-10-${day}&time=${timeSlot}">Book</a>
                    </td>
                </c:forEach>
            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>
