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
    <jsp:include page="../client/clientVerticalNavbar.jsp" />
    <div class="container">
        <div class="flex mb-4">
            <a href="workoutOptionss?page=clientWorkoutDetails&workoutId=${workout.workoutId}"
                class="btn btn-secondary">
            <i class="fas fa-arrow-left"></i> Back to Workouts
            </a>
        </div>

        <div class="card">
            <div class="card-header flex justify-between items-center">
                <h2 class="mb-0">Full Body Strength</h2>
                <span class="category-badge">
                    <i class="fas fa-tag"></i> Strength Training
                </span>
            </div>

            <div class="card-body">
                <!-- Hardcoded Exercise Cards -->
                <div class="grid grid-auto-fit gap-lg">
                    <!-- Exercise 1 -->
                    <div class="exercise-card">
                        <div class="flex items-center mb-3">
                            <div class="exercise-icon-container">
                                <i class="fas fa-dumbbell exercise-icon"></i>
                            </div>
                            <span class="exercise-title">Bench Press</span>
                        </div>

                        <div class="stats-container">
                            <div class="grid grid-2 gap-md">
                                <div>
                                    <div class="stats-label">Sets</div>
                                    <div class="stats-value">4</div>
                                </div>
                                <div>
                                    <div class="stats-label">Reps</div>
                                    <div class="stats-value">10</div>
                                </div>
                            </div>
                        </div>

                        <div class="note-container">
                            <div class="note-text">
                                <i class="fas fa-sticky-note"></i>
                                <span>Focus on form and maintain a steady tempo.</span>
                            </div>
                        </div>
                    </div>

                    <!-- Exercise 2 -->
                    <div class="exercise-card">
                        <div class="flex items-center mb-3">
                            <div class="exercise-icon-container">
                                <i class="fas fa-running exercise-icon"></i>
                            </div>
                            <span class="exercise-title">Squats</span>
                        </div>

                        <div class="stats-container">
                            <div class="grid grid-2 gap-md">
                                <div>
                                    <div class="stats-label">Sets</div>
                                    <div class="stats-value">3</div>
                                </div>
                                <div>
                                    <div class="stats-label">Reps</div>
                                    <div class="stats-value">12</div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Exercise 3 -->
                    <div class="exercise-card">
                        <div class="flex items-center mb-3">
                            <div class="exercise-icon-container">
                                <i class="fas fa-bicycle exercise-icon"></i>
                            </div>
                            <span class="exercise-title">Cycling</span>
                        </div>

                        <div class="stats-container">
                            <div class="grid grid-2 gap-md">
                                <div>
                                    <div class="stats-label">Duration</div>
                                    <div class="stats-value">20 min</div>
                                </div>
                                <div>
                                    <div class="stats-label">Intensity</div>
                                    <div class="stats-value">Moderate</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- End of Hardcoded Exercise Cards -->
            </div>
            <div class="flex mb-4">
                <a href="/workoutOptionss?page=workoutLogs" class="btn btn-success">
                     START
                </a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
