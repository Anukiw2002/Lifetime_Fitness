<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Workout Log UI</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/workoutLogs.css">
</head>
<body>
<div class="layout-container">
    <!-- Navbar Container -->
    <div class="navbar-container">
        <jsp:include page="../common/verticalNavBar.jsp" />
    </div>

    <!-- Workout Logs Container -->
    <div class="workout-logs-container">
        <div class="columns">
            <div class="column">
                <h2>Weight</h2>
                <input type="text" name="weight1" placeholder="Weight 1">
                <input type="text" name="weight2" placeholder="Weight 2">
                <input type="text" name="weight3" placeholder="Weight 3">
            </div>
            <div class="column">
                <h2>Reps</h2>
                <input type="text" name="reps1" placeholder="Reps 1">
                <input type="text" name="reps2" placeholder="Reps 2">
                <input type="text" name="reps3" placeholder="Reps 3">
            </div>
        </div>
        <div class="buttons">
            <button type="button" class="prev">Prev</button>
            <button type="button" class="next">Next</button>
        </div>
    </div>
</div>
</body>
</html>
