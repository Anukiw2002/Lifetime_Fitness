<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Workouts</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/createAndUpdateWorkout.css">
</head>
<body>
<div class="container">
    <h1>My Workouts</h1>

    <!-- Workout Cards with Links -->
    <a href="${pageContext.request.contextPath}/workoutOptions?page=workout" class="workout-link">
        <div class="workout-card">
            <div class="workout-image abs"></div>
            <div class="workout-info">
                <h2>ABS ADVANCED</h2>
                <p>36 mins · 21 exercises</p>
            </div>
        </div>
    </a>

    <a href="${pageContext.request.contextPath}/workoutOptions?page=workout" class="workout-link">
        <div class="workout-card">
            <div class="workout-image chest"></div>
            <div class="workout-info">
                <h2>CHEST ADVANCED</h2>
                <p>19 mins · 16 exercises</p>
            </div>
        </div>
    </a>

    <a href="${pageContext.request.contextPath}/workoutOptions?page=workout" class="workout-link">
        <div class="workout-card">
            <div class="workout-image arm"></div>
            <div class="workout-info">
                <h2>ARM ADVANCED</h2>
                <p>32 mins · 28 exercises</p>
            </div>
        </div>
    </a>

    <!-- Create New Workout Link -->
    <div class="create-new">
        <a href="${pageContext.request.contextPath}/workoutOptions?page=newWorkout">Create New Workout</a>
    </div>
</div>
</body>
</html>
