<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.demo2.model.*" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gym Management Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.7.0/chart.min.js"></script>
    <style>
        .dashboard-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: var(--spacing-lg);
            margin-bottom: var(--spacing-2xl);
        }

        .stat-card {
            background: linear-gradient(145deg, var(--bg-dark), var(--bg-darker));
            border-radius: var(--border-radius);
            padding: var(--spacing-xl);
            box-shadow: var(--shadow);
            border: 1px solid var(--border-color);
            transition: transform 0.3s ease;
        }

        .stat-card:hover {
            transform: translateY(-5px);
        }

        .stat-value {
            font-size: 2.5rem;
            font-weight: bold;
            color: var(--primary-color);
            margin-bottom: var(--spacing-xs);
        }

        .stat-label {
            color: var(--text-muted);
            font-size: var(--font-size-sm);
            text-transform: uppercase;
            letter-spacing: 1px;
        }

        .chart-container {
            background: var(--bg-dark);
            border-radius: var(--border-radius);
            padding: var(--spacing-xl);
            margin-bottom: var(--spacing-2xl);
            border: 1px solid var(--border-color);
        }

        .charts-grid {
            display: grid;
            grid-template-columns: 2fr 1fr;
            gap: var(--spacing-lg);
            margin-bottom: var(--spacing-2xl);
        }

        .revenue-chart-container {
            height: 400px;
            display: flex;
            flex-direction: column;
        }

        .recent-activity {
            background: var(--bg-dark);
            border-radius: var(--border-radius);
            padding: var(--spacing-xl);
            border: 1px solid var(--border-color);
        }

        .activity-item {
            display: flex;
            align-items: center;
            padding: var(--spacing-md);
            border-bottom: 1px solid var(--border-color);
        }

        .activity-icon {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            margin-right: var(--spacing-md);
        }

        .icon-new-member { background-color: var(--primary-color); }
        .icon-workout { background-color: var(--success-color); }
        .icon-plan { background-color: var(--accent-color); }

        .quick-actions {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: var(--spacing-md);
            margin-bottom: var(--spacing-2xl);
        }

        .action-btn {
            padding: var(--spacing-lg);
            text-align: center;
            background: linear-gradient(145deg, var(--primary-color), var(--primary-hover));
            border-radius: var(--border-radius);
            color: white;
            text-decoration: none;
            transition: transform 0.3s ease;
        }

        .action-btn:hover {
            transform: translateY(-3px);
        }

        .notification-badge {
            position: absolute;
            top: -5px;
            right: -5px;
            background: var(--danger-color);
            color: white;
            border-radius: 50%;
            width: 20px;
            height: 20px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 12px;
        }

        @media (max-width: 1200px) {
            .charts-grid {
                grid-template-columns: 1fr;
            }

            .revenue-chart-container {
                height: 300px;
            }
        }

        @media (max-width: 768px) {
            .dashboard-grid {
                grid-template-columns: 1fr;
            }

            .quick-actions {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>
<jsp:include page="../common/verticalNavBar.jsp" />
<div class="main-content">
    <div class="container">
        <h1>Dashboard</h1>

        <div class="quick-actions">
            <a href="addMembershipPlan.jsp" class="action-btn">
                <i class="fas fa-plus"></i>
                Add Membership Plan
            </a>
            <a href="/clientMemberships" class="action-btn">
                <i class="fas fa-dumbbell"></i>
                Member Management
            </a>
            <a href="manageInstructors.jsp" class="action-btn">
                <i class="fas fa-users"></i>
                Manage Instructors
            </a>
            <a href="reports.jsp" class="action-btn">
                <i class="fas fa-chart-line"></i>
                View Reports
            </a>
        </div>

        <div class="dashboard-grid">
            <div class="stat-card">
                <div class="stat-value">254</div>
                <div class="stat-label">Active Members</div>
            </div>
            <div class="stat-card">
                <div class="stat-value">12</div>
                <div class="stat-label">Active Plans</div>
            </div>
            <div class="stat-card">
                <div class="stat-value">8</div>
                <div class="stat-label">Instructors</div>
            </div>
            <div class="stat-card">
                <div class="stat-value">Rs.125K</div>
                <div class="stat-label">Monthly Revenue</div>
            </div>
        </div>

        <div class="charts-grid">
            <div class="chart-container">
                <h3>Membership Growth</h3>
                <canvas id="membershipChart"></canvas>
            </div>

            <div class="chart-container revenue-chart-container">
                <h3>Revenue by Plan Type</h3>
                <canvas id="revenueChart"></canvas>
            </div>
        </div>

        <div class="recent-activity">
            <h3>Recent Activity</h3>
            <div class="activity-item">
                <div class="activity-icon icon-new-member">
                    <i class="fas fa-user-plus"></i>
                </div>
                <div>
                    <strong>New Member Joined</strong>
                    <p>John Doe enrolled in Platinum Plan</p>
                    <small class="text-muted">2 hours ago</small>
                </div>
            </div>
            <div class="activity-item">
                <div class="activity-icon icon-workout">
                    <i class="fas fa-dumbbell"></i>
                </div>
                <div>
                    <strong>New Workout Added</strong>
                    <p>Instructor Mike added a new workout routine</p>
                    <small class="text-muted">5 hours ago</small>
                </div>
            </div>
            <div class="activity-item">
                <div class="activity-icon icon-plan">
                    <i class="fas fa-clipboard-list"></i>
                </div>
                <div>
                    <strong>Plan Updated</strong>
                    <p>Gold Membership plan was modified</p>
                    <small class="text-muted">1 day ago</small>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    // Membership Growth Chart
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
</script>
</body>
</html>