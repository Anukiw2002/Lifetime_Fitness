<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Booking Calendar</title>
    <style>
        .booked { color: red; }
        .available { color: green; cursor: pointer; }
    </style>
</head>
<body>
<h1>Booking Calendar</h1>
<table border="1">
    <tr>
        <th>Date</th>
        <th>Time Slot</th>
        <th>Status</th>
    </tr>
    <c:forEach var="day" begin="1" end="30">
        <c:set var="date" value="${'2024-11-' + (day < 10 ? '0' + day : day)}" />
        <c:set var="isBooked" value="false" />
        <tr>
            <td>${date}</td>
            <td>10:00 AM - 11:00 AM</td>
            <td>
                <c:choose>
                    <c:when test="${not empty bookedSlots}">
                        <c:forEach var="slot" items="${bookedSlots}">
                            <c:if test="${slot.date == date && slot.timeSlot == '10:00 AM - 11:00 AM'}">
                                <span class="booked">Booked</span>
                                <c:set var="isBooked" value="true" />
                            </c:if>
                        </c:forEach>
                        <c:if test="${empty isBooked}">
                            <a href="book?date=${date}&time=10:00 AM - 11:00 AM" class="available">Book Now</a>
                        </c:if>
                    </c:when>
                    <c:otherwise>
                        <a href="book?date=${date}&time=10:00 AM - 11:00 AM" class="available">Book Now</a>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
