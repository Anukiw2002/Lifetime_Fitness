
    const weightDates = [
    <c:forEach var="d" items="${dates}" varStatus="loop">
        "${d}"<c:if test="${!loop.last}">,</c:if>
    </c:forEach>
    ];

    const weightValues = [
    <c:forEach var="w" items="${weights}" varStatus="loop">
        ${w}<c:if test="${!loop.last}">,</c:if>
    </c:forEach>
    ];

    const ctx = document.getElementById('weightTrendChart').getContext('2d');
    new Chart(ctx, {
    type: 'line',
    data: {
    labels: weightDates,
    datasets: [{
    label: 'Weight (kg)',
    data: weightValues,
    fill: false,
    borderColor: '#4CAF50',
    tension: 0.4
}]
},
    options: {
    responsive: true,
    plugins: {
    legend: {
    display: true
},
    tooltip: {
    mode: 'index',
    intersect: false
}
},
    scales: {
    x: {
    title: {
    display: true,
    text: 'Date'
}
},
    y: {
    title: {
    display: true,
    text: 'Weight (kg)'
},
    beginAtZero: false
}
}
}
});

