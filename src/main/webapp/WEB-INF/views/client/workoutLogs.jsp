<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Workout Log UI</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/workoutLogs.css">
</head>
<body>
<div class="container">
    <div class="columns">
        <div class="column">
            <h2>Weight</h2>
            <input type="text" name="weight1" placeholder="">
            <input type="text" name="weight2" placeholder="">
            <input type="text" name="weight3" placeholder="">
        </div>
        <div class="column">
            <h2>Reps</h2>
            <input type="text" name="reps1" placeholder="">
            <input type="text" name="reps2" placeholder="">
            <input type="text" name="reps3" placeholder="">
        </div>
    </div>
    <div class="buttons">
        <button type="button" class="prev">Prev</button>
        <button type="button" class="next">Next</button>
    </div>
</div>
</body>
</html>
