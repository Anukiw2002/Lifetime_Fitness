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
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/owner-dashboard.css">
</head>
<body>
<jsp:include page="../common/verticalNavBar.jsp" />
<div class="main-content">
    <div class="container">
        <h1>Dashboard</h1>

        <div class="quick-actions">
            <a href="/membership/view" class="action-btn">
                <i class="fas fa-credit-card"></i>
                Membership Plans
            </a>
            <a href="/clientMemberships" class="action-btn">
                <i class="fas fa-users"></i>
                Member Management
            </a>
            <a href="/instructorManagement" class="action-btn">
                <i class="fas fa-user"></i>
                Manage Instructors
            </a>
            <a href="/first" class="action-btn">
                <i class="fas fa-file-alt"></i>
                View Reports
            </a>
        </div>

        <div class="dashboard-grid">
            <div class="stat-card">
                <div class="stat-value">
                    <c:out value="${count}" />
                </div>
                <div class="stat-label">Active Members</div>
            </div>
            <div class="stat-card">
                <div class="stat-value">
                    <c:out value="${membershipPlanCount}" />
                </div>
                <div class="stat-label">Active Plans</div>
            </div>
            <div class="stat-card">
                <div class="stat-value">
                    <c:out value="${instructors}" />
                </div>
                <div class="stat-label">Instructors</div>
            </div>
            <div class="stat-card">
                <div class="stat-value">${revenue}</div>
                <div class="stat-label">Monthly Revenue</div>
            </div>
        </div>

        <div class="charts-grid">
            <div class="chart-container">
                <h3>Membership Growth</h3>
                <!-- Adding the canvas for the membership chart -->
                <canvas id="membershipChart"></canvas>
            </div>

            <div class="chart-container revenue-chart-container">
                <h3>Revenue by Plan Type</h3>
                <!-- Adding the canvas for the revenue chart -->
                <canvas id="revenueChart"></canvas>
            </div>
        </div>

    </div>
</div>

<script>
    // Fetch data from the /dashboard endpoint
    fetch("/dashboard", {
        method: 'GET',
        headers: {
            'X-Requested-With': 'XMLHttpRequest' // This is crucial for the server to recognize the request as AJAX
        }
    })
        .then(response => response.json()) // Parse JSON response
        .then(data => {
            // Get the context of the canvas elements for the charts
            const membershipCtx = document.getElementById("membershipChart").getContext("2d");
            const revenueCtx = document.getElementById("revenueChart").getContext("2d");

            // Membership Growth Chart
            new Chart(membershipCtx, {
                type: 'line', // Line chart for membership growth
                data: {
                    labels: data.months, // Use months from the JSON response
                    datasets: [{
                        label: 'Membership Growth',
                        data: data.userCounts, // Use userCounts from the JSON response
                        borderColor: 'blue',
                        backgroundColor: 'rgba(0, 0, 255, 0.2)',
                        borderWidth: 1
                    }]
                },
                options: {
                    responsive: true,
                    scales: {
                        x: {
                            title: {
                                display: true,
                                text: 'Months'
                            }
                        },
                        y: {
                            title: {
                                display: true,
                                text: 'Number of Users'
                            }
                        }
                    }
                }
            });

            // Revenue Growth Chart (You can create the second chart for revenue similarly)

        })
        .catch(error => {
            console.error('Error:', error); // Handle any errors that occur during the fetch
        });
    const revenueData = {
        labels: [],
        data: []
    };

    <c:forEach var="entry" items="${revenueForFourMonths}">
    revenueData.labels.push("${entry.key}");
    revenueData.data.push(${entry.value});
    </c:forEach>

    const revenueCtx = document.getElementById("revenueChart").getContext("2d");

    new Chart(revenueCtx, {
        type: 'line',
        data: {
            labels: revenueData.labels,
            datasets: [{
                label: 'Revenue Growth',
                data: revenueData.data,
                borderColor: 'green',
                backgroundColor: 'rgba(0, 255, 0, 0.2)',
                borderWidth: 2,
                tension: 0.4
            }]
        },
        options: {
            responsive: true,
            scales: {
                x: {
                    title: {
                        display: true,
                        text: 'Month'
                    }
                },
                y: {
                    title: {
                        display: true,
                        text: 'Revenue'
                    },
                    beginAtZero: true
                }
            }
        }
    });
</script>

<script src="${pageContext.request.contextPath}/js/owner-dashboard.js"></script>
</body>
</html>
