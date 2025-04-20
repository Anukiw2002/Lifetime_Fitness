<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>My Workouts | Lifetime Fitness</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
    <style>
        body {
            color: var(--text-color) !important;
        }
        .card, .card-body, .card a {
            color: var(--text-color) !important;
            text-decoration: none;
        }
        .card:hover {
            transform: translateY(-2px);
            transition: var(--transition);
        }
        .text-muted {
            color: rgba(255, 255, 255, 0.7) !important;
        }
        .exercise-icon-container {
            background-color: var(--primary-color);
            border-radius: 50%;
            width: 60px;
            height: 60px;
            display: flex;
            align-items: center;
            justify-content: center;
            margin-bottom: 1rem;
        }
        .exercise-icon {
            color: white;
            font-size: 2rem;
        }
        .exercise-title {
            color: white;
            font-size: 1.25rem;
            font-weight: 500;
            margin-left: 1rem;
        }
        .workout-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: var(--spacing-lg);
        }
        .alert {
            padding: 1rem;
            border-radius: var(--border-radius);
            margin: 1rem 0;
        }
        .alert-info {
            background-color: rgba(0, 123, 255, 0.2);
            border-left: 4px solid;
        }
    </style>
</head>
<body>

<div class="main-content">
    <jsp:include page="clientVerticalNavbar.jsp" />
    <div class="container">
        <!-- Client Information Section -->
        <div class="card mb-4">
            <div class="card-header">
                <h2 class="text-center">My Information</h2>
            </div>
            <div class="card-body">
                <div class="grid grid-3">
                    <div class="flex items-center gap-md">
                        <i class="fas fa-user"></i>
                        <span>${client.name}</span>
                    </div>
                    <div class="flex items-center gap-md">
                        <i class="fas fa-envelope"></i>
                        <span>${client.email}</span>
                    </div>
                </div>
            </div>
        </div>

        <!-- Workouts Section -->
        <h3 class="mb-3">My Workouts</h3>

        <c:choose>
            <c:when test="${not empty workouts}">
                <div class="workout-grid">
                    <c:forEach var="workout" items="${workouts}">
                        <div class="card">
                            <div class="card-body">
                                <div class="flex items-center mb-3">
                                    <div class="exercise-icon-container">
                                        <c:choose>
                                            <c:when test="${workout.categoryId == 1}">
                                                <i class="fas fa-running exercise-icon"></i>
                                            </c:when>
                                            <c:when test="${workout.categoryId == 2}">
                                                <i class="fas fa-dumbbell exercise-icon"></i>
                                            </c:when>
                                            <c:when test="${workout.categoryId == 3}">
                                                <i class="fas fa-spa exercise-icon"></i>
                                            </c:when>
                                            <c:otherwise>
                                                <i class="fas fa-heartbeat exercise-icon"></i>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <span class="exercise-title">${workout.workoutName}</span>
                                </div>
                                <div class="flex items-center gap-sm mb-2">
                                    <i class="fas fa-tag"></i>
                                    <span>
                                        <c:choose>
                                            <c:when test="${workout.category != null}">
                                                ${workout.category.categoryName}
                                            </c:when>
                                            <c:when test="${workout.categoryId == 1}">Cardio</c:when>
                                            <c:when test="${workout.categoryId == 2}">Strength</c:when>
                                            <c:when test="${workout.categoryId == 3}">Yoga</c:when>
                                            <c:otherwise>Other</c:otherwise>
                                        </c:choose>
                                    </span>
                                </div>
                                <div class="flex items-center gap-sm text-muted">
                                    <i class="far fa-calendar-alt"></i>
                                    <fmt:formatDate value="${workout.createdAtDate}" pattern="MMM dd, yyyy"/>
                                </div>
                                <div class="mt-3">
                                    <a href="${pageContext.request.contextPath}/client/ViewExercises?page=clientWorkoutDetails&workoutId=${workout.workoutId}"
                                       class="btn btn-primary btn-sm">
                                        View Details
                                    </a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <div class="alert alert-info">
                    <i class="fas fa-info-circle"></i> No workouts found. Contact an instructor to get started with your fitness journey!
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

</body>
</html>