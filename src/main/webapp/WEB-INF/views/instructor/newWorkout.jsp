<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>New Workout</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/newWorkout.css">
</head>
<body>
<div class="container">
    <h1>New Workout</h1>
    <form action="addWorkout" method="post">
        <div class="exercise-row">
            <input type="text" name="exercise" placeholder="Squat">
            <button type="submit">ADD</button>
        </div>
        <div class="exercise-row">
            <input type="text" name="exercise" placeholder="Bench press">
            <button type="submit">ADD</button>
        </div>
        <div class="exercise-row">
            <input type="text" name="exercise" placeholder="Deadlift">
            <button type="submit">ADD</button>
        </div>
        <!-- Add more exercise rows as needed -->
        <div class="exercise-row">
            <input type="text" name="exercise" placeholder="Enter exercise">
            <button type="submit">ADD</button>
        </div>
        <div class="exercise-row">
            <input type="text" name="exercise" placeholder="Enter exercise">
            <button type="submit">ADD</button>
        </div>
    </form>
</div>
</body>
</html>
