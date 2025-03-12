fetch("/dashboard", {
    method: 'GET',
    headers: {
        'X-Requested-With': 'XMLHttpRequest' // This is crucial for the server to recognize the request as AJAX
    }
})
    .then(response => response.json())
    .then(data => {
        const membershipCtx = document.getElementById("membershipChart").getContext("2d");
        const revenueCtx = document.getElementById("revenueChart").getContext("2d");

        // Membership Growth Chart
        new Chart(membershipCtx, {
            type: 'line',
            data: {
                labels: data.months, // Use months from the JSON response
                datasets: [{
                    label: 'Membership Growth',
                    data: data.userCounts, // Use userCounts from the JSON response
                    borderColor: 'blue',
                    backgroundColor: 'rgba(0, 0, 255, 0.2)',
                    borderWidth: 1
                }]
            }
        });

        // You can create the second chart for revenue similarly
    });
