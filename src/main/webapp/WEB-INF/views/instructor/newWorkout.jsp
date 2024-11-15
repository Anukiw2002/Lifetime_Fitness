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
        <!-- Exercise rows -->
        <div class="exercise-row">
            <input type="text" name="exercise" placeholder="Squat">
            <button type="submit" class="add-button">ADD</button>
        </div>
        <div class="exercise-row">
            <input type="text" name="exercise" placeholder="Bench press">
            <button type="submit" class="add-button">ADD</button>
        </div>
        <div class="exercise-row">
            <input type="text" name="exercise" placeholder="Deadlift">
            <button type="submit" class="add-button">ADD</button>
        </div>
        <div class="exercise-row">
            <input type="text" name="exercise" placeholder="Enter exercise">
            <button type="submit" class="add-button">ADD</button>
        </div>
        <div class="exercise-row">
            <input type="text" name="exercise" placeholder="Enter exercise">
            <button type="submit" class="add-button">ADD</button>
        </div>

        <!-- Create button at the bottom right -->
        <div class="create-button-container">
            <button type="submit" class="create-button">CREATE</button>
        </div>
    </form>
</div>
</body>
</html>
