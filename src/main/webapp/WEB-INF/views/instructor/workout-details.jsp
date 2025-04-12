<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Workout Details | Lifetime Fitness</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
    <style>
        .category-badge {
            background-color: var(--primary-color);
            color: white;
            padding: 0.5rem 1rem;
            border-radius: var(--border-radius);
            font-weight: 500;
            display: inline-flex;
            align-items: center;
            gap: 0.5rem;
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

        .stats-container {
            background-color: rgba(255, 255, 255, 0.1);
            border-radius: var(--border-radius);
            padding: 1rem;
            margin: 1rem 0;
        }

        .stats-label {
            color: white;
            font-weight: 500;
            margin-bottom: 0.5rem;
        }

        .stats-value {
            color: white;
            font-size: 1.25rem;
            font-weight: 600;
        }

        .note-container {
            background-color: rgba(255, 255, 255, 0.1);
            border-radius: var(--border-radius);
            padding: 1rem;
            margin-top: 1rem;
        }

        .note-text {
            color: white;
            font-size: 0.95rem;
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }

        .exercise-card {
            background-color: var(--bg-dark);
            border: 1px solid var(--border-color);
            border-radius: var(--border-radius);
            padding: 1.5rem;
        }
    </style>
</head>
<body>
<div class="main-content">
    <jsp:include page="instructorVerticalNavbar.jsp" />
    <div class="container">
        <div class="flex mb-4">
            <a href="/instructor/clientWorkouts?phoneNumber=${workout.clientPhone}" class="btn btn-secondary">
                <i class="fas fa-arrow-left"></i> Back to Workouts
            </a>
        </div>

        <div class="card">
            <div class="card-header flex justify-between items-center">
                <h2 class="mb-0">${workout.workoutName}</h2>
                <span class="category-badge">
                    <i class="fas fa-tag"></i> ${workout.category.categoryName}
                </span>
            </div>

            <div class="card-body">
                <c:choose>
                    <c:when test="${empty workout.exercises}">
                        <div class="text-center text-muted">
                            <p>No exercises found for this workout.</p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="grid grid-auto-fit gap-lg">
                            <c:forEach var="exercise" items="${workout.exercises}">
                                <div class="exercise-card">
                                    <div class="flex items-center mb-3">
                                        <div class="exercise-icon-container">
                                            <i class="fas fa-running exercise-icon"></i>
                                        </div>
                                        <span class="exercise-title">
                                                ${exercise.exercise.exerciseName}
                                        </span>
                                    </div>

                                    <div class="stats-container">
                                        <div class="grid grid-2 gap-md">
                                            <div>
                                                <div class="stats-label">Sets</div>
                                                <div class="stats-value">${exercise.setNumber}</div>
                                            </div>
                                            <div>
                                                <div class="stats-label">Reps</div>
                                                <div class="stats-value">${exercise.reps}</div>
                                            </div>
                                        </div>
                                    </div>

                                    <c:if test="${not empty exercise.notes}">
                                        <div class="note-container">
                                            <div class="note-text">
                                                <i class="fas fa-sticky-note"></i>
                                                <span>${exercise.notes}</span>
                                            </div>
                                        </div>
                                    </c:if>
                                </div>
                            </c:forEach>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>
</body>
</html>