document.addEventListener("DOMContentLoaded", function () {
    const weightDataElement = document.getElementById("weight-data");

    if (weightDataElement) {
        const beginningWeight = parseFloat(weightDataElement.dataset.beginningWeight);
        const currentWeight = parseFloat(weightDataElement.dataset.currentWeight);
        const targetWeight = parseFloat(weightDataElement.dataset.targetWeight);

        const ctx = document.getElementById('weightTrendChart');

        const data = {
            labels: ['Start', 'Current', 'Target'],
            datasets: [{
                label: 'Weight Progress (kg)',
                data: [beginningWeight, currentWeight, targetWeight],
                fill: false,
                borderColor: '#4e73df',
                tension: 0.3,
                pointBackgroundColor: ['#36b9cc', '#f6c23e', '#1cc88a'],
                pointRadius: 5,
            }]
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
