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
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
            transition: var(--transition);
        }

        .category-badge:hover {
            background-color: var(--primary-hover);
            transform: translateY(-1px);
            box-shadow: 0 3px 5px rgba(0, 0, 0, 0.3);
        }

        .workout-header {
            position: relative;
            padding-bottom: var(--spacing-md);
            margin-bottom: var(--spacing-lg);
            border-bottom: 1px solid var(--border-color);
        }

        .workout-header h2 {
            margin-bottom: var(--spacing-sm);
            font-size: var(--font-size-2xl);
            font-weight: 700;
            letter-spacing: 0.5px;
        }

        .exercise-icon-container {
            background: linear-gradient(145deg, var(--primary-color), var(--primary-hover));
            border-radius: 50%;
            width: 60px;
            height: 60px;
            display: flex;
            align-items: center;
            justify-content: center;
            margin-right: var(--spacing-md);
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            transition: var(--transition);
        }

        .exercise-icon-container:hover {
            transform: rotate(15deg);
        }

        .exercise-icon {
            color: white;
            font-size: 1.8rem;
        }

        .exercise-title {
            color: white;
            font-size: 1.4rem;
            font-weight: 600;
            letter-spacing: 0.3px;
        }

        .stats-container {
            background: linear-gradient(to right, rgba(255, 255, 255, 0.08), rgba(255, 255, 255, 0.12));
            border-radius: var(--border-radius);
            padding: 1.25rem;
            margin: 1.25rem 0;
            border: 1px solid var(--border-color);
            transition: var(--transition);
        }

        .stats-container:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
            border-color: var(--primary-color);
        }

        .stats-row {
            display: grid;
            grid-template-columns: 2fr 1fr 1fr;
            gap: 1rem;
            align-items: center;
        }

        .stats-label {
            color: var(--text-muted);
            font-weight: 500;
            margin-bottom: 0.25rem;
            text-transform: uppercase;
            font-size: 0.8rem;
            letter-spacing: 1px;
        }

        .stats-value {
            color: white;
            font-size: 1.5rem;
            font-weight: 700;
        }

        .exercise-name {
            font-weight: 600;
            font-size: 1.1rem;
        }

        .note-container {
            background-color: rgba(var(--primary-color-rgb), 0.1);
            border-radius: var(--border-radius);
            padding: 1rem;
            margin-top: 1rem;
            border-left: 3px solid var(--primary-color);
        }

        .note-text {
            color: white;
            font-size: 0.95rem;
            display: flex;
            align-items: flex-start;
            gap: 0.75rem;
        }

        .note-text i {
            color: var(--primary-color);
            margin-top: 0.25rem;
        }

        .exercise-card {
            background: linear-gradient(to bottom right, var(--bg-dark), var(--bg-darker));
            border: 1px solid var(--border-color);
            border-radius: var(--border-radius);
            padding: 1.75rem;
            box-shadow: var(--shadow);
        }

        .action-btn-container {
            margin: var(--spacing-xl) 0;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .btn-start {
            padding: 0.75rem 2.5rem;
            font-size: 1.1rem;
            letter-spacing: 1px;
            position: relative;
            overflow: hidden;
        }

        .btn-start:after {
            content: "";
            position: absolute;
            top: -50%;
            right: -50%;
            bottom: -50%;
            left: -50%;
            background: linear-gradient(to bottom, rgba(255, 255, 255, 0), rgba(255, 255, 255, 0.2), rgba(255, 255, 255, 0));
            transform: rotate(30deg) translate(-100%, 0);
            animation: shine 3s infinite;
        }

        @keyframes shine {
            0% {
                transform: rotate(30deg) translate(-100%, 0);
            }
            20% {
                transform: rotate(30deg) translate(100%, 0);
            }
            100% {
                transform: rotate(30deg) translate(100%, 0);
            }
        }

        :root {
            --primary-color-rgb: 0, 82, 204;
        }
    </style>
</head>
<body>
<div class="main-content">
    <jsp:include page="../client/clientVerticalNavbar.jsp" />
    <div class="container">
        <div class="flex mb-4">
            <a href="javascript:history.back()"
               class="btn btn-secondary">
                <i class="fas fa-arrow-left"></i> Back to Workouts
            </a>
        </div>

        <div class="card">
            <div class="workout-header flex justify-between items-center">
                <h2>${workout.workoutName}</h2>
                <span class="category-badge">
                    <i class="fas fa-tag"></i> ${workout.category.categoryName}
                </span>
            </div>

            <div class="card-body">
                <div class="exercise-card">
                    <div class="flex items-center mb-4">
                        <div class="exercise-icon-container">
                            <i class="fas fa-dumbbell exercise-icon"></i>
                        </div>
                        <span class="exercise-title">${workout.workoutName}</span>
                    </div>

                    <!-- Loop through the exercises of the single workout -->
                    <c:forEach var="exercise" items="${workout.exercises}" varStatus="status">
                        <div class="stats-container">
                            <div class="stats-row">
                                <div class="exercise-name">
                                        ${exercise.exercise.exerciseName}
                                </div>
                                <div>
                                    <div class="stats-label">Sets</div>
                                    <div class="stats-value">${exercise.setNumber}</div>
                                </div>
                                <div>
                                    <div class="stats-label">Reps</div>
                                    <div class="stats-value">${exercise.reps}</div>
                                </div>
                            </div>

                            <c:if test="${not empty exercise.notes}">
                                <div class="note-container mt-3">
                                    <div class="note-text">
                                        <i class="fas fa-sticky-note"></i>
                                        <span>${exercise.notes}</span>
                                    </div>
                                </div>
                            </c:if>
                        </div>
                    </c:forEach>
                </div>
            </div>

            <div class="action-btn-container">
                <a href="${pageContext.request.contextPath}/StartExercises?workoutId=${workout.workoutId}" class="btn btn-success btn-start">
                    <i class="fas fa-play-circle"></i> START WORKOUT
                </a>
            </div>
        </div>
    </div>
</div>
</body>
</html>