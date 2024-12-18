<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gym Management - Instructor Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.7.0/chart.min.js"></script>
    <style>
        .dashboard-container {
            padding: 2rem;
            max-width: 1400px;
            margin: 0 auto;
        }

        .dashboard-header {
            margin-bottom: 2.5rem;
        }

        .stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 1.5rem;
            margin-bottom: 2.5rem;
        }

        .content-grid {
            display: grid;
            grid-template-columns: 2fr 1fr;
            gap: 1.5rem;
            margin-bottom: 2.5rem;
        }

        .chart-section {
            background-color: var(--card-bg);
            border-radius: 0.75rem;
            padding: 1.5rem;
            margin-bottom: 2.5rem;
            border: 1px solid var(--border-color);
        }

        .upcoming-sessions {
            background-color: var(--card-bg);
            border-radius: 0.75rem;
            padding: 1.5rem;
            height: fit-content;
        }

        .session-card {
            background-color: rgba(255, 255, 255, 0.05);
            padding: 1.25rem;
            border-radius: 0.5rem;
            margin-bottom: 1rem;
            border: 1px solid var(--border-color);
            transition: all 0.2s ease;
        }

        .session-card:hover {
            transform: translateY(-2px);
            border-color: var(--primary-color);
            box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1);
        }

        .session-card:last-child {
            margin-bottom: 0;
        }

        .session-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 0.75rem;
        }

        .session-time {
            background-color: var(--primary-color);
            padding: 0.35rem 0.75rem;
            border-radius: 2rem;
            font-size: 0.875rem;
            font-weight: 500;
        }

        .activity-feed {
            background-color: var(--card-bg);
            border-radius: 0.75rem;
            padding: 1.5rem;
            margin-bottom: 2.5rem;
        }

        .activity-list {
            margin-top: 1rem;
        }

        .activity-item {
            padding: 1rem;
            border-bottom: 1px solid var(--border-color);
            display: flex;
            align-items: center;
            gap: 1rem;
        }

        .activity-item:last-child {
            border-bottom: none;
        }

        .activity-icon {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            background-color: var(--primary-color);
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            flex-shrink: 0;
        }

        .stat-card {
            background-color: var(--card-bg);
            padding: 1.5rem;
            border-radius: 0.75rem;
            border: 1px solid var(--border-color);
            display: flex;
            align-items: center;
            gap: 1.25rem;
        }

        .stat-icon {
            width: 48px;
            height: 48px;
            border-radius: 0.5rem;
            background-color: rgba(37, 99, 235, 0.1);
            display: flex;
            align-items: center;
            justify-content: center;
            flex-shrink: 0;
        }

        h1, h2, h3, h4 {
            margin: 0;
            line-height: 1.4;
        }

        .text-muted {
            color: var(--text-muted);
            margin-top: 0.25rem;
            font-size: 0.875rem;
        }

        @media (max-width: 1024px) {
            .content-grid {
                grid-template-columns: 1fr;
            }
        }

        @media (max-width: 768px) {
            .dashboard-container {
                padding: 1rem;
            }

            .stats-grid {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>
<jsp:include page="../instructor/clientVerticalNavbar.jsp" />

<div class="main-content">
    <div class="dashboard-container">
        <div class="dashboard-header">
            <h1>Gym Overview</h1>
            <p class="text-muted">Today's Activity Summary</p>
        </div>

        <div class="stats-grid">
            <div class="stat-card">
                <div class="stat-icon">
                    <i class="fas fa-users-line fa-lg" style="color: var(--primary-color)"></i>
                </div>
                <div>
                    <h3>247</h3>
                    <p class="text-muted">Active Members</p>
                </div>
            </div>
            <div class="stat-card">
                <div class="stat-icon">
                    <i class="fas fa-dumbbell fa-lg" style="color: var(--success-color)"></i>
                </div>
                <div>
                    <h3>28</h3>
                    <p class="text-muted">Today's Workouts</p>
                </div>
            </div>
            <div class="stat-card">
                <div class="stat-icon">
                    <i class="fas fa-chart-line fa-lg" style="color: var(--warning-color)"></i>
                </div>
                <div>
                    <h3>85%</h3>
                    <p class="text-muted">Gym Capacity</p>
                </div>
            </div>
        </div>

        <div class="content-grid">
            <div class="chart-section">
                <h2>Gym Traffic Today</h2>
                <canvas id="trafficChart"></canvas>
            </div>

            <div class="upcoming-sessions">
                <h2>Upcoming Sessions</h2>
                <div style="margin-top: 1rem;">
                    <div class="session-card">
                        <div class="session-header">
                            <h3>HIIT Training</h3>
                            <span class="session-time">9:00 AM</span>
                        </div>
                        <p class="text-muted">Client: John Doe</p>
                        <p class="text-muted">Phone: +94 77 123 4567</p>
                    </div>
                    <div class="session-card">
                        <div class="session-header">
                            <h3>Strength Training</h3>
                            <span class="session-time">10:30 AM</span>
                        </div>
                        <p class="text-muted">Client: Jane Smith</p>
                        <p class="text-muted">Phone: +94 77 987 6543</p>
                    </div>
                    <div class="session-card">
                        <div class="session-header">
                            <h3>Cardio Session</h3>
                            <span class="session-time">2:00 PM</span>
                        </div>
                        <p class="text-muted">Client: Mike Johnson</p>
                        <p class="text-muted">Phone: +94 77 456 7890</p>
                    </div>
                </div>
            </div>
        </div>

        <div class="activity-feed">
            <h2>Recent Activity</h2>
            <div class="activity-list">
                <div class="activity-item">
                    <div class="activity-icon">
                        <i class="fas fa-plus"></i>
                    </div>
                    <div>
                        <h4>New Workout Created</h4>
                        <p class="text-muted">Upper Body Strength • Client: #1234 • 10 mins ago</p>
                    </div>
                </div>
                <div class="activity-item">
                    <div class="activity-icon">
                        <i class="fas fa-user-check"></i>
                    </div>
                    <div>
                        <h4>Member Check-in</h4>
                        <p class="text-muted">Client: #5678 • 15 mins ago</p>
                    </div>
                </div>
                <div class="activity-item">
                    <div class="activity-icon">
                        <i class="fas fa-clipboard-check"></i>
                    </div>
                    <div>
                        <h4>Workout Completed</h4>
                        <p class="text-muted">HIIT Training • Client: #9012 • 30 mins ago</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        const ctx = document.getElementById('trafficChart').getContext('2d');
        new Chart(ctx, {
            type: 'line',
            data: {
                labels: ['6AM', '8AM', '10AM', '12PM', '2PM', '4PM', '6PM', '8PM', '10PM'],
                datasets: [{
                    label: 'Number of Members',
                    data: [15, 30, 45, 35, 25, 40, 50, 35, 20],
                    borderColor: '#2563eb',
                    tension: 0.4,
                    fill: true,
                    backgroundColor: 'rgba(37, 99, 235, 0.1)'
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: true,
                plugins: {
                    legend: {
                        labels: {
                            color: '#f3f4f6'
                        }
                    }
                },
                scales: {
                    y: {
                        grid: {
                            color: 'rgba(55, 65, 81, 0.1)'
                        },
                        ticks: {
                            color: '#f3f4f6'
                        }
                    },
                    x: {
                        grid: {
                            color: 'rgba(55, 65, 81, 0.1)'
                        },
                        ticks: {
                            color: '#f3f4f6'
                        }
                    }
                }
            }
        });
    });
</script>
</body>
</html>