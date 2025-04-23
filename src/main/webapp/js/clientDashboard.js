document.addEventListener("DOMContentLoaded", function () {
    const weightDataElement = document.getElementById("weight-data");

    if (weightDataElement && typeof weightEntries !== "undefined") {
        const beginningWeight = parseFloat(weightDataElement.dataset.beginningWeight);
        const currentWeight = parseFloat(weightDataElement.dataset.currentWeight);
        const targetWeight = parseFloat(weightDataElement.dataset.targetWeight);

        const ctx = document.getElementById('weightTrendChart');

        const labels = weightEntries.map(entry => entry.date);
        const dataPoints = weightEntries.map(entry => entry.weight);

        const beginningIndex = 0;
        const currentIndex = dataPoints.length - 1;
        const targetIndex = currentIndex;

        const pointColors = dataPoints.map((_, idx) => {
            if (idx === beginningIndex) return '#36b9cc';      // Start - blue
            if (idx === currentIndex) return '#f6c23e';         // Current - yellow
            return '#4e73df';                                   // Others - default
        });

        const pointRadii = dataPoints.map((_, idx) => {
            if (idx === beginningIndex || idx === currentIndex) return 6;
            return 4;
        });

        // Target data only at the end
        const targetData = Array(labels.length).fill(null);
        targetData[targetIndex] = targetWeight;

        const data = {
            labels: labels,
            datasets: [
                {
                    label: 'Weight Progress (kg)',
                    data: dataPoints,
                    fill: false,
                    borderColor: '#4e73df',
                    backgroundColor: '#4e73df',
                    tension: 0.3,
                    pointBackgroundColor: pointColors,
                    pointRadius: pointRadii,
                },
                {
                    label: 'Target Weight',
                    data: targetData,
                    pointBackgroundColor: '#1cc88a',
                    pointRadius: 7,
                    pointHoverRadius: 8,
                    backgroundColor: '#1cc88a',
                    borderColor: '#1cc88a',
                    fill: false,
                    showLine: false,
                }
            ]
        };

        const config = {
            type: 'line',
            data: data,
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        display: true
                    },
                    tooltip: {
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
                        title: {
                            display: true,
                            text: 'Weight (kg)'
                        }
                    },
                    x: {
                        title: {
                            display: true,
                            text: 'Date'
                        }
                    }
                }
            },
        };

        new Chart(ctx, config);
    }
});
