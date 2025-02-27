const membershipCtx = document.getElementById('membershipChart').getContext('2d');
new Chart(membershipCtx, {
    type: 'line',
    data: {
        labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],
        datasets: [{
            label: 'New Members',
            data: [65, 78, 90, 85, 99, 105],
            borderColor: '#0052CC',
            tension: 0.4,
            fill: true,
            backgroundColor: 'rgba(0, 82, 204, 0.1)'
        }]
    },
    options: {
        responsive: true,
        maintainAspectRatio: true,
        plugins: {
            legend: {
                position: 'top',
                labels: {
                    color: '#fff'
                }
            }
        },
        scales: {
            y: {
                beginAtZero: true,
                grid: {
                    color: 'rgba(255, 255, 255, 0.1)'
                },
                ticks: {
                    color: '#fff'
                }
            },
            x: {
                grid: {
                    color: 'rgba(255, 255, 255, 0.1)'
                },
                ticks: {
                    color: '#fff'
                }
            }
        }
    }
});

// Revenue Chart
const revenueCtx = document.getElementById('revenueChart').getContext('2d');
new Chart(revenueCtx, {
    type: 'doughnut',
    data: {
        labels: ['Platinum', 'Gold', 'Silver', 'Day Pass'],
        datasets: [{
            data: [45000, 35000, 25000, 15000],
            backgroundColor: [
                '#2c2c2c',
                '#966600',
                '#666666',
                '#0052CC'
            ]
        }]
    },
    options: {
        responsive: true,
        maintainAspectRatio: true,
        plugins: {
            legend: {
                position: 'bottom',
                labels: {
                    color: '#fff'
                }
            }
        }
    }
});

// Simulated real-time updates
setInterval(() => {
    const stats = document.querySelectorAll('.stat-value');
    stats.forEach(stat => {
        const currentValue = parseInt(stat.textContent);
        const variation = Math.floor(Math.random() * 5) - 2; // Random variation between -2 and 2
        stat.textContent = currentValue + variation;
    });
}, 5000);