<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <jsp:include page="clientVerticalNavbar.jsp" />
    <div class="container">
        <h1>My Workouts</h1>

        <!-- Workout Cards with Links and Delete Button -->
        <div class="container">
            <h1>Workouts for ${client.name}</h1>

            <div class="workout-cards">
                <c:forEach items="${workoutsByCategory}" var="category">
                    <h2>${category.key}</h2>
                    <c:forEach items="${category.value}" var="workout">
                        <div class="workout-card-wrapper">
                            <a href="${pageContext.request.contextPath}/workoutOptions?page=workout&workoutId=${workout.workoutId}" class="workout-link">
                                <div class="workout-card">
                                    <div class="workout-image ${workout.category.categoryName.toLowerCase()}"></div>
                                    <div class="workout-info">
                                        <h2>${workout.workoutName}</h2>
                                        <p>Created: ${workout.createdAt}</p>
                                    </div>
                                </div>
                            </a>
                        </div>
                    </c:forEach>
                </c:forEach>
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
