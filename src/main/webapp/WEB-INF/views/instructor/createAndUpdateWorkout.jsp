<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title style="color: white" >My Workouts</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/createAndUpdateWorkout.css">
</head>
<body>
<div class="main-container">
    <jsp:include page="../common/verticalNavBar.jsp" />
    <div class="container">
        <h1>My Workouts</h1>

        <!-- Workout Cards with Links and Delete Button -->
        <div class="workout-cards">
            <div class="workout-card-wrapper">
                <a href="${pageContext.request.contextPath}/workoutOptions?page=workout" class="workout-link">
                    <div class="workout-card">
                        <div class="workout-image abs"></div>
                        <div class="workout-info">
                            <h2>ABS ADVANCED</h2>
                            <p>36 mins · 21 exercises</p>
                        </div>
                    </div>
                </a>
                <form action="${pageContext.request.contextPath}/deleteWorkout" method="post" class="delete-form">
                    <input type="hidden" name="workoutId" value="1"> <!-- Replace with dynamic workout ID -->
                    <button type="submit" class="delete-button">Delete</button>
                </form>
            </div>

            <div class="workout-card-wrapper">
                <a href="${pageContext.request.contextPath}/workoutOptions?page=workout" class="workout-link">
                    <div class="workout-card">
                        <div class="workout-image chest"></div>
                        <div class="workout-info">
                            <h2>CHEST ADVANCED</h2>
                            <p>19 mins · 16 exercises</p>
                        </div>
                    </div>
                </a>
                <form action="${pageContext.request.contextPath}/deleteWorkout" method="post" class="delete-form">
                    <input type="hidden" name="workoutId" value="2"> <!-- Replace with dynamic workout ID -->
                    <button type="submit" class="delete-button">Delete</button>
                </form>
            </div>

            <div class="workout-card-wrapper">
                <a href="${pageContext.request.contextPath}/workoutOptions?page=workout" class="workout-link">
                    <div class="workout-card">
                        <div class="workout-image arm"></div>
                        <div class="workout-info">
                            <h2>ARM ADVANCED</h2>
                            <p>32 mins · 28 exercises</p>
                        </div>
                    </div>
                </a>
                <form action="${pageContext.request.contextPath}/deleteWorkout" method="post" class="delete-form">
                    <input type="hidden" name="workoutId" value="3"> <!-- Replace with dynamic workout ID -->
                    <button type="submit" class="delete-button">Delete</button>
                </form>
            </div>
        </div>

        <!-- Create New Workout Link -->
        <div class="create-new">
            <a href="${pageContext.request.contextPath}/workoutOptions?page=newWorkout">Create New Workout</a>
        </div>
    </div>
</div>
</body>
</html>
