// Weight Progress Chart
document.addEventListener("DOMContentLoaded", function () {
    const weightDataElement = document.getElementById("weight-data");

    if (weightDataElement) {
        const beginningWeight = parseFloat(weightDataElement.dataset.beginningWeight);
        const currentWeight = parseFloat(weightDataElement.dataset.currentWeight);
        const targetWeight = parseFloat(weightDataElement.dataset.targetWeight);

        const ctx = document.getElementById('weightTrendChart');

        // Colors matching your site's design
        const primaryColor = getComputedStyle(document.documentElement).getPropertyValue('--primary-color').trim() || '#0052cc';
        const accentColor = getComputedStyle(document.documentElement).getPropertyValue('--accent-color').trim() || '#F57C00';
        const successColor = getComputedStyle(document.documentElement).getPropertyValue('--success-color').trim() || '#2E7D32';

        const data = {
            labels: ['Start', 'Current', 'Target'],
            datasets: [
                {
                    label: 'Weight Progress (kg)',
                    data: [beginningWeight, currentWeight, null], // only connect start and current
                    borderColor: primaryColor,
                    backgroundColor: primaryColor,
                    tension: 0.3,
                    pointBackgroundColor: [primaryColor, accentColor, null],
                    pointBorderColor: [primaryColor, accentColor, null],
                    pointRadius: 6,
                    pointHoverRadius: 8,
                    borderWidth: 2
                },
                {
                    label: 'Target Weight',
                    data: [null, null, targetWeight], // only show target as a dot
                    pointBackgroundColor: successColor,
                    pointBorderColor: successColor,
                    pointRadius: 6,
                    pointHoverRadius: 8,
                    backgroundColor: successColor,
                    borderColor: successColor,
                    fill: false,
                    showLine: false, // no line for target
                }
            ]
        };

        const config = {
            type: 'line',
            data: data,
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        display: false
                    },
                    tooltip: {
                        backgroundColor: 'rgba(42, 42, 42, 0.9)',
                        titleColor: '#ffffff',
                        bodyColor: '#ffffff',
                        borderColor: 'rgba(255, 255, 255, 0.2)',
                        borderWidth: 1,
                        padding: 10,
                        cornerRadius: 6,
                        callbacks: {
                            label: function (context) {
                                return context.parsed.y + ' kg';
                            }
                        }
                    }
                },
                scales: {
                    y: {
                        beginAtZero: false,
                        grid: {
                            color: 'rgba(255, 255, 255, 0.05)',
                            borderColor: 'rgba(255, 255, 255, 0.2)'
                        },
                        ticks: {
                            color: 'rgba(255, 255, 255, 0.7)'
                        },
                        title: {
                            display: true,
                            text: 'Weight (kg)',
                            color: 'rgba(255, 255, 255, 0.9)',
                            font: {
                                size: 12
                            }
                        }
                    },
                    x: {
                        grid: {
                            display: false
                        },
                        ticks: {
                            color: 'rgba(255, 255, 255, 0.7)'
                        }
                    }
                }
            },
        };

        new Chart(ctx, config);
    }
});

// Weekly Workout Sessions Chart
document.addEventListener('DOMContentLoaded', function() {
    // Check if the workout chart canvas exists
    const workoutChartCanvas = document.getElementById('workoutTrendChart');
    if (!workoutChartCanvas) return;

    // Get the weekly workout data from the JSP/Servlet
    if (typeof weeklyWorkoutData === 'undefined') return;

    const labels = [];
    const data = [];

    // Colors matching your site's design
    const accentColor = getComputedStyle(document.documentElement).getPropertyValue('--primary-color').trim() || '#0052cc';

    // Parse the data
    for (const [date, count] of Object.entries(weeklyWorkoutData)) {
        // Convert the date to a more readable format
        const formattedDate = new Date(date);
        labels.push(formattedDate.toLocaleDateString('en-US', { month: 'short', day: 'numeric' }));
        data.push(count);
    }

    // Create the chart
    new Chart(workoutChartCanvas, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: 'Weekly Workouts',
                data: data,
                backgroundColor: `${accentColor}80`, // with opacity
                borderColor: accentColor,
                borderWidth: 1,
                borderRadius: 4,
                hoverBackgroundColor: accentColor
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                y: {
                    beginAtZero: true,
                    grid: {
                        color: 'rgba(255, 255, 255, 0.05)',
                        borderColor: 'rgba(255, 255, 255, 0.2)'
                    },
                    ticks: {
                        precision: 0,  // Show only whole numbers
                        color: 'rgba(255, 255, 255, 0.7)'
                    },
                    title: {
                        display: true,
                        text: 'Number of Sessions',
                        color: 'rgba(255, 255, 255, 0.9)',
                        font: {
                            size: 12
                        }
                    }
                },
                x: {
                    grid: {
                        display: false
                    },
                    ticks: {
                        color: 'rgba(255, 255, 255, 0.7)'
                    },
                    title: {
                        display: true,
                        text: 'Date',
                        color: 'rgba(255, 255, 255, 0.9)',
                        font: {
                            size: 12
                        }
                    }
                }
            },
            plugins: {
                legend: {
                    display: false
                },
                tooltip: {
                    backgroundColor: 'rgba(42, 42, 42, 0.9)',
                    titleColor: '#ffffff',
                    bodyColor: '#ffffff',
                    borderColor: 'rgba(255, 255, 255, 0.2)',
                    borderWidth: 1,
                    padding: 10,
                    cornerRadius: 6
                }
            }
        }
    });
});