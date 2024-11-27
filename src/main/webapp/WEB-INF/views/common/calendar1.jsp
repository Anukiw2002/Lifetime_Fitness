<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gym Booking Calendar</title>
    <!-- FullCalendar CSS and JS -->

    <!-- FullCalendar CSS -->
    <link href="https://cdn.jsdelivr.net/npm/fullcalendar@5.11.3/main.min.css" rel="stylesheet" />
    <!-- FullCalendar JS -->
    <script src="https://cdn.jsdelivr.net/npm/fullcalendar@5.11.3/main.min.js"></script>
    <!-- jQuery (optional but required if you're using jQuery for AJAX requests) -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

</head>
<body>

<div id="calendar"></div>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        var calendarEl = document.getElementById('calendar');

        // Initialize FullCalendar
        var calendar = new FullCalendar.Calendar(calendarEl, {
            initialView: 'dayGridMonth', // Change to month view
            selectable: true,
            editable: true,
            events: '/events', // Fetch events from the servlet

            // Select event to add new booking
            select: function(info) {
                var title = prompt("Enter workout type:");
                if (title) {
                    $.ajax({
                        url: '/booking',
                        method: 'POST',
                        data: JSON.stringify({
                            title: title,
                            start: info.startStr,
                            end: info.endStr
                        }),
                        contentType: 'application/json',
                        success: function() {
                            calendar.refetchEvents(); // Refresh calendar after booking
                        }
                    });
                }
                calendar.unselect();
            },

            // Click an event to update it
            eventClick: function(info) {
                // Prompt for new details or show a modal to edit the event
                var newTitle = prompt("Update workout type:", info.event.title);
                if (newTitle && newTitle !== info.event.title) {
                    $.ajax({
                        url: '/update', // Update endpoint
                        method: 'PUT',
                        data: JSON.stringify({
                            id: info.event.id,
                            title: newTitle,
                            start: info.event.startStr,
                            end: info.event.endStr
                        }),
                        contentType: 'application/json',
                        success: function() {
                            info.event.setProp('title', newTitle); // Update the title of the event on the calendar
                        },
                        error: function(xhr, status, error) {
                            console.error("Error updating the booking:", xhr, status, error);
                            alert("Error updating the booking.");
                        }
                    });
                }
            },

            // Delete event on click
            eventDelete: function(info) {
                if (confirm("Are you sure you want to delete this booking?")) {
                    $.ajax({
                        url: '/delete', // Delete endpoint
                        method: 'DELETE',
                        data: JSON.stringify({ id: info.event.id }),
                        contentType: 'application/json',
                        success: function() {
                            calendar.refetchEvents(); // Refresh calendar after deletion
                        },
                        error: function(xhr, status, error) {
                            console.error("Error deleting the booking:", xhr, status, error);
                            alert("Error deleting the booking.");
                        }
                    });
                }
            }
        });

        calendar.render();
    });
</script>

</body>
</html>
