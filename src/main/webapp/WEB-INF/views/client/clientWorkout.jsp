<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Client Workouts | Lifetime Fitness</title>
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
    </style>
</head>
<body>

<div class="main-content">
    <jsp:include page="../client/clientVerticalNavbar.jsp" />
    <div class="container">
        <div class="card mb-4">
            <div class="card-header">
                <h2 class="text-center">Client Information</h2>
            </div>
            <div class="card-body">
                <div class="grid grid-3">
                    <div class="flex items-center gap-md">
                        <i class="fas fa-user"></i>
                        <span>${client.name}</span> <!-- Updated -->
                    </div>
                    <div class="flex items-center gap-md">
                        <i class="fas fa-phone"></i>
                        <span>${client.phoneNumber}</span> <!-- Updated -->
                    </div>
                    <div class="flex items-center gap-md">
                        <i class="fas fa-envelope"></i>
                        <span>${client.email}</span> <!-- Updated -->
                    </div>
                </div>
            </div>
        </div>

        <div class="workout-grid">
            <c:forEach var="workout" items="${clientWorkouts}">
                <div class="card">
                    <a href="workoutOptions?page=clientWorkoutDetails&workoutId=${workout.workoutId}" class="card-body">
                        <div class="flex items-center mb-3">
                            <div class="exercise-icon-container">
                                <i class="fas ${workout.category.icon} exercise-icon"></i>
                            </div>
                            <span class="exercise-title">${workout.workoutName}</span>
                        </div>
                        <div class="flex items-center gap-sm mb-2">
                            <i class="fas fa-tag"></i>
                            <span>${workout.category.categoryName}</span>
                        </div>
                        <div class="flex items-center gap-sm text-muted">
                            <i class="far fa-calendar-alt"></i>
                            <fmt:formatDate value="${workout.createdAtDate}" pattern="MMM dd, yyyy"/> <!-- Fixed -->
                        </div>
                    </a>
                </div>
            </c:forEach>
        </div>
    </div>
</div>

</body>
</html>
