document.addEventListener("DOMContentLoaded", function () {
    const weightDataElement = document.getElementById("weight-data");

    if (weightDataElement) {
        const beginningWeight = parseFloat(weightDataElement.dataset.beginningWeight);
        const currentWeight = parseFloat(weightDataElement.dataset.currentWeight);
        const targetWeight = parseFloat(weightDataElement.dataset.targetWeight);

        const ctx = document.getElementById('weightTrendChart');

        const data = {
            labels: ['Start', 'Current', 'Target'],
            datasets: [
                {
                    label: 'Weight Progress (kg)',
                    data: [beginningWeight, currentWeight, null], // only connect start and current
                    fill: false,
                    borderColor: '#4e73df',
                    backgroundColor: '#4e73df',
                    tension: 0.3,
                    pointBackgroundColor: ['#36b9cc', '#f6c23e', null],
                    pointRadius: 5,
                },
                {
                    label: 'Target Weight',
                    data: [null, null, targetWeight], // only show target as a dot
                    pointBackgroundColor: '#1cc88a',
                    pointRadius: 6,
                    pointHoverRadius: 7,
                    backgroundColor: '#1cc88a',
                    borderColor: '#1cc88a',
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
                plugins: {
                    legend: { display: false },
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
                    }
                }
            },
        };

        new Chart(ctx, config);
    }
});
