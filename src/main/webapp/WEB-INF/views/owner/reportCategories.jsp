<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Ishan Maduranga
  Date: 4/22/2025
  Time: 6:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reportCategories.css">

</head>
<body>
<jsp:include page="../common/verticalNavBar.jsp" />
<div class="main-content">
    <div class="container">
        <h2>Membership Plans Consumed</h2>
        <canvas id="revenueByTypeChart" width="400" height="200"></canvas>
    </div>
    <div class="container">
        <h2>New Members Encountered (Month wise)</h2>
        <canvas id="membersEncounteredChart" width="400" height="200"></canvas>
    </div>
    <div class="container">
        <h2>Revenue By Plan Type</h2>
        <canvas id="revenueChart" width="400" height="200"></canvas>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
    const revenueByTypeData = {
        labels:[],
        data: []
    };

    <c:forEach var="entry" items="${revenueByType}">
        revenueByTypeData.labels.push("${entry.key}");
        revenueByTypeData.data.push(${entry.value});
    </c:forEach>

    const ctx = document.getElementById('revenueByTypeChart').getContext('2d');
    new Chart(ctx, {
        type: 'bar', // You can change to 'pie', 'line', etc.
        data: {
            labels: revenueByTypeData.labels,
            datasets: [{
                label: 'Revenue by Plan Type (Rs)',
                data: revenueByTypeData.data,
                backgroundColor: 'rgba(0, 0, 255, 0.2)',
                borderColor: 'blue',
                borderWidth: 1,
                color: '#FFFFFF'
            }]
        },
        options: {
            responsive: true,
            scales: {
                y: {
                    beginAtZero: true,
                    title: {
                        display: true,
                        text: 'Revenue (Rs)',
                        color: '#FFFFFF'
                    },
                    ticks : {
                        color : '#FFFFFF'
                    }
                },
                x: {
                    title: {
                        display: true,
                        text: 'Plan Type',
                        color: '#FFFFFF'
                    },
                    ticks: {
                        color: '#FFFFFF'
                    }

                }
            }
        }
    });

    const membersEncountered = {
        labels:[],
        data: []
    };

    <c:forEach var="entry" items="${userCount}">
    membersEncountered.labels.push("${entry.key}");
    membersEncountered.data.push(${entry.value});
    </c:forEach>

    const ctx1 = document.getElementById('membersEncounteredChart').getContext('2d');
    new Chart(ctx1, {
        type: 'line', // You can change to 'pie', 'line', etc.
        data: {
            labels: membersEncountered.labels,
            datasets: [{
                label: 'New Members Encountered',
                data: membersEncountered.data,
                backgroundColor: 'rgba(0, 0, 255, 0.2)',
                borderColor: 'blue',
                borderWidth: 1,
                color: '#FFFFFF'
            }]
        },
        options: {
            responsive: true,
            scales: {
                y: {
                    beginAtZero: true,
                    title: {
                        display: true,
                        text: 'Members (Rs)',
                        color: '#FFFFFF'
                    },
                    ticks : {
                        color : '#FFFFFF'
                    }
                },
                x: {
                    title: {
                        display: true,
                        text: 'Month',
                        color: '#FFFFFF'
                    },
                    ticks: {
                        color: '#FFFFFF'
                    }

                }
            }
        }
    });
    const revenueEncountered = {
        labels:[],
        data: []
    };

    <c:forEach var="entry" items="${revenueForFourMonths}">
    revenueEncountered.labels.push("${entry.key}");
    revenueEncountered.data.push(${entry.value});
    </c:forEach>

    const ctx2 = document.getElementById('revenueChart').getContext('2d');
    new Chart(ctx2, {
        type: 'line', // You can change to 'pie', 'line', etc.
        data: {
            labels: revenueEncountered.labels,
            datasets: [{
                label: 'Revenue By Type',
                data: revenueEncountered.data,
                backgroundColor: 'rgba(0, 0, 255, 0.2)',
                borderColor: 'blue',
                borderWidth: 1,
                color: '#FFFFFF'
            }]
        },
        options: {
            responsive: true,
            scales: {
                y: {
                    beginAtZero: true,
                    title: {
                        display: true,
                        text: 'Members (Rs)',
                        color: '#FFFFFF'
                    },
                    ticks : {
                        color : '#FFFFFF'
                    }
                },
                x: {
                    title: {
                        display: true,
                        text: 'Month',
                        color: '#FFFFFF'
                    },
                    ticks: {
                        color: '#FFFFFF'
                    }

                }
            }
        }
    });

</script>
</body>
</html>
