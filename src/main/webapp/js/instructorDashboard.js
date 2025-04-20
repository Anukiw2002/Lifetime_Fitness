document.addEventListener("DOMContentLoaded", function() {
    // Log to verify Chart.js is loaded
    console.log("Chart.js loaded:", typeof Chart !== 'undefined');

    const workoutDataElement = document.getElementById("workout-data");
    console.log("Found workout data element:", workoutDataElement !== null);

    if(workoutDataElement) {
        // Parse the data values with parseInt to ensure they're numbers
        const today = parseInt(workoutDataElement.dataset.today) || 0;
        const yesterday = parseInt(workoutDataElement.dataset.yesterday) || 0;
        const tomorrow = parseInt(workoutDataElement.dataset.tomorrow) || 0;

        // Log the data for debugging
        console.log("Chart data:", {yesterday, today, tomorrow});

        const ctx = document.getElementById('workoutTrendChart').getContext('2d');

        const data = {
            labels: ['Yesterday', 'Today', 'Tomorrow'],
            datasets: [{
                label: 'Workout Sessions',
                data: [yesterday, today, tomorrow],
                fill: false,
                borderColor: '#36b9cc',
                tension: 0.3,
                pointBackgroundColor: ['#f6c23e', '#4e73df', '#1cc88a'],
                pointRadius: 5,
            }]
        };

        const config = {
            type: 'line',
            data: data,
            options: {
                responsive: true,
                maintainAspectRatio: false,  // Allows the chart to fill the container
                plugins: {
                    legend: { display: false },
                    tooltip: {
                        callbacks: {
                            label: function(context) {
                                return context.parsed.y + ' sessions';
                            }
                        }
                    }
                },
                scales: {
                    y: {
                        beginAtZero: true,
                        ticks: {
                            precision: 0  // Only show whole numbers
                        },
                        title: {
                            display: true,
                            text: 'Workout Count'
                        }
                    }
                }
            }
        };

        try {
            new Chart(ctx, config);
            console.log("Chart created successfully");
        } catch (error) {
            console.error("Error creating chart:", error);
        }
    } else {
        console.error("Could not find workout-data element");
    }

    // Add any additional dashboard functionality here
});