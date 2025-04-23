document.addEventListener("DOMContentLoaded", function () {
    const weightDataElement = document.getElementById("weight-data");

    if (weightDataElement && typeof weightEntries !== "undefined") {
        const beginningWeight = parseFloat(weightDataElement.dataset.beginningWeight);
        const currentWeight = parseFloat(weightDataElement.dataset.currentWeight);
        const targetWeight = parseFloat(weightDataElement.dataset.targetWeight);

        const ctx = document.getElementById('weightTrendChart');

        // Extract dates and weights from weightEntries
        const labels = weightEntries.map(entry => entry.date);
        const dataPoints = weightEntries.map(entry => parseFloat(entry.weight));

        // Create two separate datasets:
        // 1. Connected line for beginning weight through current weights
        // 2. Separate point for target weight

        // Connected line dataset (beginning + all recorded weights)
        const connectedData = {
            label: 'Weight Progress (kg)',
            data: [beginningWeight, ...dataPoints],
            labels: ['Start', ...labels],
            fill: false,
            borderColor: '#4e73df',
            backgroundColor: '#4e73df',
            tension: 0.3,
            pointBackgroundColor: (context) => {
                // Different colors for beginning and current
                if (context.dataIndex === 0) return '#36b9cc'; // Beginning - blue
                if (context.dataIndex === dataPoints.length) return '#f6c23e'; // Current - yellow
                return '#4e73df'; // Others - default indigo
            },
            pointRadius: (context) => {
                // Make beginning and current points larger
                if (context.dataIndex === 0 || context.dataIndex === dataPoints.length) return 6;
                return 4;
            },
            pointHoverRadius: 8
        };

        // Target weight as a separate point (not connected)
        const targetPoint = {
            label: 'Target Weight',
            data: [null, ...Array(dataPoints.length).fill(null), targetWeight],
            borderColor: '#1cc88a',
            backgroundColor: '#1cc88a',
            pointRadius: 6,
            pointHoverRadius: 8,
            showLine: false
        };

        // Horizontal target weight line
        const targetLine = {
            label: 'Target Goal',
            data: Array(dataPoints.length + 2).fill(targetWeight),
            borderColor: '#1cc88a',
            borderWidth: 2,
            borderDash: [5, 5],
            pointRadius: 0,
            fill: false,
            tension: 0
        };

        const data = {
            labels: ['Start', ...labels, 'Target'],
            datasets: [connectedData, targetPoint, targetLine]
        };

        const config = {
            type: 'line',
            data: data,
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        display: true,
                        labels: {
                            filter: function(legendItem) {
                                // Only show "Weight Progress" and "Target Goal" in legend
                                return legendItem.text === 'Weight Progress (kg)' ||
                                    legendItem.text === 'Target Goal';
                            }
                        }
                    },
                    tooltip: {
                        callbacks: {
                            label: function(context) {
                                const datasetIndex = context.datasetIndex;
                                const pointIndex = context.dataIndex;

                                if (datasetIndex === 0) { // Weight progress dataset
                                    if (pointIndex === 0) {
                                        return 'Starting Weight: ' + context.parsed.y + ' kg';
                                    } else if (pointIndex === dataPoints.length) {
                                        return 'Current Weight: ' + context.parsed.y + ' kg';
                                    }
                                    return 'Weight: ' + context.parsed.y + ' kg on ' + labels[pointIndex-1];
                                } else if (datasetIndex === 1 && context.parsed.y !== null) { // Target point
                                    return 'Target Weight: ' + context.parsed.y + ' kg';
                                } else if (datasetIndex === 2) { // Target line
                                    return 'Target Goal: ' + context.parsed.y + ' kg';
                                }
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
                        },
                        // Adjust y-axis to show slight padding above and below actual data range
                        suggestedMin: Math.min(beginningWeight, currentWeight, targetWeight) - 2,
                        suggestedMax: Math.max(beginningWeight, currentWeight, targetWeight) + 2
                    },
                    x: {
                        title: {
                            display: true,
                            text: 'Date'
                        }
                    }
                }
            }
        };

        new Chart(ctx, config);
    }
});