document.addEventListener("DOMContentLoaded", () => {
    // Add event listeners to each cell for booking
    document.querySelectorAll(".available").forEach(cell => {
        cell.addEventListener("click", () => bookSlot(cell));
    });
});

function bookSlot(cell) {
    if (cell.classList.contains("available")) {
        const time = cell.dataset.time;
        const day = cell.dataset.day;

        // Optimistically mark the slot as booked on the UI
        cell.classList.replace("available", "booked");

        // Send booking request to the server
        fetch('<%= request.getContextPath() %>/bookSlot', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ time: time, day: day })
        })
            .then(response => {
                if (response.ok) {
                    alert(`Booking successful for ${day} at ${time}`);
                } else {
                    // Revert to available if booking failed
                    alert("Booking failed. Please try again.");
                    cell.classList.replace("booked", "available");
                }
            })
            .catch(error => {
                console.error("Error:", error);
                alert("Booking failed. Please try again.");
                // Revert to available if there's a network error
                cell.classList.replace("booked", "available");
            });
    }
}
