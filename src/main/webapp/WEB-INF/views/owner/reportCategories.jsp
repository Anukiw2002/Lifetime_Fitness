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
    <style>
        .print-btn {
            background-color: #4CAF50;
            border: none;
            color: white;
            padding: 8px 16px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 14px;
            margin: 4px 2px;
            cursor: pointer;
            border-radius: 4px;
        }

        .data-table {
            border-collapse: collapse;
            width: 100%;
            margin-top: 10px;
        }

        .data-table th, .data-table td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
            color: #FFFFFF;
        }

        .data-table th {
            background-color: rgba(0, 0, 255, 0.2);
        }

        @media print {
            body * {
                visibility: hidden;
            }
            #printSection, #printSection * {
                visibility: visible;
            }
            #printSection {
                position: absolute;
                left: 0;
                top: 0;
            }
        }
    </style>

</head>
<body>
<jsp:include page="../common/verticalNavBar.jsp" />
<div class="main-content">
    <div class="container">
        <h2>Plan Enrollment Overdue</h2>
        <button id="printPieChart" class="print-btn">Print Report</button>
        <canvas id="planChart" width="200" height="100"></canvas>
    </div>
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
        type: 'bar',
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

    const ctx3 = document.getElementById('membersEncounteredChart').getContext('2d');
    new Chart(ctx3, {
        type: 'line',
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

    <c:forEach var="entry" items="${planCount}">
    revenueEncountered.labels.push("${entry.key}");
    revenueEncountered.data.push(${entry.value});
    </c:forEach>

    // Define the pie chart variable so we can reference it later
    const ctx2 = document.getElementById('planChart').getContext('2d');
    const pieChart = new Chart(ctx2, {
        type: 'pie',
        data: {
            labels: revenueEncountered.labels,
            datasets: [{
                label: 'Revenue By Plan Type',
                data: revenueEncountered.data,
                backgroundColor: [
                    'rgba(255, 99, 132, 0.6)',
                    'rgba(54, 162, 235, 0.6)',
                    'rgba(255, 206, 86, 0.6)',
                    'rgba(75, 192, 192, 0.6)',
                    'rgba(153, 102, 255, 0.6)',
                ],
                borderColor: 'white',
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: true,
            aspectRatio: 2,
            plugins: {
                legend: {
                    position: 'bottom',
                    labels: {
                        color: '#FFFFFF'
                    }
                }
            }
        }
    });

    // Add print functionality
    document.getElementById('printPieChart').addEventListener('click', function() {
        // Create a printable section
        const printContent = document.createElement('div');
        printContent.id = 'printSection';
        printContent.style.width = '100%';
        printContent.style.padding = '20px';

        // Add a title
        const title = document.createElement('h2');
        title.textContent = 'Financial Report';
        title.style.fontSize = '24px';
        title.style.color = '#000000';
        title.style.marginBottom = '20px';
        title.style.textAlign = 'center';
        printContent.appendChild(title);

        // Create a new canvas for the chart to print - make it larger
        const printCanvas = document.createElement('canvas');
        printCanvas.width = 600;
        printCanvas.height = 400;
        printCanvas.style.marginBottom = '30px';
        printContent.appendChild(printCanvas);

        // Create a table for the data
        const table = document.createElement('table');
        table.style.width = '80%';
        table.style.margin = '20px auto';
        table.style.borderCollapse = 'collapse';
        table.style.fontSize = '16px';

        // Add table header
        const thead = document.createElement('thead');
        const headerRow = document.createElement('tr');

        const planHeader = document.createElement('th');
        planHeader.textContent = 'Plan';
        planHeader.style.padding = '10px';
        planHeader.style.backgroundColor = '#f2f2f2';
        planHeader.style.border = '1px solid #ddd';
        planHeader.style.color = '#000000';

        const countHeader = document.createElement('th');
        countHeader.textContent = 'Count';
        countHeader.style.padding = '10px';
        countHeader.style.backgroundColor = '#f2f2f2';
        countHeader.style.border = '1px solid #ddd';
        countHeader.style.color = '#000000';

        headerRow.appendChild(planHeader);
        headerRow.appendChild(countHeader);
        thead.appendChild(headerRow);
        table.appendChild(thead);

        // Add table body with data
        const tbody = document.createElement('tbody');
        for (let i = 0; i < revenueEncountered.labels.length; i++) {
            const row = document.createElement('tr');

            const planCell = document.createElement('td');
            planCell.textContent = revenueEncountered.labels[i];
            planCell.style.padding = '10px';
            planCell.style.border = '1px solid #ddd';
            planCell.style.color = '#000000';

            const countCell = document.createElement('td');
            countCell.textContent = revenueEncountered.data[i];
            countCell.style.padding = '10px';
            countCell.style.border = '1px solid #ddd';
            countCell.style.color = '#000000';

            row.appendChild(planCell);
            row.appendChild(countCell);
            tbody.appendChild(row);
        }
        table.appendChild(tbody);
        printContent.appendChild(table);

        // Apply print styles
        document.body.appendChild(printContent);

        // Draw chart on new canvas with larger size and black text
        new Chart(printCanvas.getContext('2d'), {
            type: 'pie',
            data: {
                labels: revenueEncountered.labels,
                datasets: [{
                    label: 'Revenue By Plan Type',
                    data: revenueEncountered.data,
                    backgroundColor: [
                        'rgba(255, 99, 132, 0.6)',
                        'rgba(54, 162, 235, 0.6)',
                        'rgba(255, 206, 86, 0.6)',
                        'rgba(75, 192, 192, 0.6)',
                        'rgba(153, 102, 255, 0.6)',
                    ],
                    borderColor: 'white',
                    borderWidth: 1
                }]
            },
            options: {
                responsive: false,
                animation: false,
                plugins: {
                    title: {
                        display: true,
                        text: 'Plan Distribution',
                        color: '#000000',
                        font: {
                            size: 18
                        }
                    },
                    legend: {
                        position: 'right',
                        labels: {
                            color: '#000000',
                            font: {
                                size: 14
                            },
                            padding: 20
                        }
                    }
                }
            }
        });

        // Need a slight delay to ensure chart renders
        setTimeout(() => {
            window.print();
            // Clean up
            document.body.removeChild(printContent);
        }, 500);
    });

    const planCount = {
        labels:[],
        data: []
    };

    <c:forEach var="entry" items="${revenueForFourMonths}">
    planCount.labels.push("${entry.key}");
    planCount.data.push(${entry.value});
    </c:forEach>

    const ctx4 = document.getElementById('revenueChart').getContext('2d');
    new Chart(ctx4, {
        type: 'line', // You can change to 'pie', 'line', etc.
        data: {
            labels: planCount.labels,
            datasets: [{
                label: 'Revenue By Plan Type',
                data: planCount.data,
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
