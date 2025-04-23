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
                backgroundColor: 'rgba(75, 192, 192, 0.6)',
                borderColor: 'rgba(75, 192, 192, 1)',
                borderWidth: 1
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
</script>
</body>
</html>
