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
            initialView: 'timeGridWeek', // Weekly view with time slots
            selectable: true,
            editable: true,
            events: '/events', // Fetch events from the servlet

            // Customize time slot labels
            slotLabelContent: function(info) {
                const startTime = new Date(info.date);
                const endTime = new Date(startTime.getTime() + 60 * 60 * 1000); // Add 1 hour for the end time

                // Format the time in 12-hour format with 'am/pm'
                const formatTime = (time) => {
                    const options = { hour: 'numeric', hour12: true };
                    return time.toLocaleString('en-US', options);
                };

                // Return the formatted slot (e.g., "12am-1am")
                return { html: `\${formatTime(startTime)}-\${formatTime(endTime)}` };

            },

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

            eventClick: function(info) {
                if (confirm("Are you sure you want to delete this booking?")) {
                    $.ajax({
                        url: '/delete',
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