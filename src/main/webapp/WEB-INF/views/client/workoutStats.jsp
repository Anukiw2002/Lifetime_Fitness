<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Workout Stats UI</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/workoutStats.css">
</head>
<body>
<div class="layout-container">
    <!-- Navbar Container -->
    <div class="navbar-container">
        <jsp:include page="../common/verticalNavBar.jsp" />
    </div>

    <!-- Workout Stats Container -->
    <div class="workout-stats-container">
        <div class="row">
            <div class="input-group">
                <label for="duration">Duration</label>
                <input type="text" id="duration" name="duration">
            </div>
            <div class="input-group">
                <label for="total-reps">Total Reps</label>
                <input type="text" id="total-reps" name="total-reps">
            </div>
        </div>
        <div class="row">
            <div class="input-group">
                <label for="total-weight">Total Weight</label>
                <input type="text" id="total-weight" name="total-weight">
            </div>
            <div class="input-group">
                <label for="new-pb">New PB</label>
                <input type="text" id="new-pb" name="new-pb">
            </div>
        </div>
    </div>
</div>
</body>
</html>
