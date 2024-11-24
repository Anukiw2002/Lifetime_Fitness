<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Workout | Lifetime Fitness</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/workoutStylesheet.css">
    <style>
        .exercise-actions {
            display: flex;
            gap: 10px;
            align-items: center;
        }

        .remove-exercise {
            color: #dc3545;
            cursor: pointer;
            padding: 5px;
            border-radius: 50%;
            transition: background-color 0.2s;
        }

        .remove-exercise:hover {
            background-color: rgba(220, 53, 69, 0.1);
        }

        .exercise-input {
            width: 60px;
            padding: 5px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        .notes-input {
            width: 100%;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
            margin-top: 8px;
        }

        .add-exercise-section {
            margin-top: 20px;
            padding: 20px;
            background-color: rgba(255, 255, 255, 0.05);
            border-radius: 8px;
        }

        .exercise-select {
            width: 100%;
            padding: 8px;
            margin-bottom: 10px;
            border-radius: 4px;
        }

        .save-button {
            background-color: #28a745;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
            margin-top: 20px;
        }

        .save-button:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>
<div class="container">
    <a href="javascript:history.back()" class="back-button">
        <i class="fas fa-arrow-left"></i> Back to Workouts
    </a>

    <form action="${pageContext.request.contextPath}/instructor/editWorkout" method="POST" id="editWorkoutForm">
        <input type="hidden" name="workoutId" value="${workout.workoutId}">

        <div class="workout-header">
            <div class="workout-title">
                <h2>${workout.workoutName}</h2>
                <span class="category-badge">
                        <i class="fas fa-tag"></i> ${workout.category.categoryName}
                    </span>
            </div>
        </div>

        <div class="exercise-list">
            <c:forEach var="exercise" items="${workout.exercises}" varStatus="status">
                <div class="exercise-card">
                    <input type="hidden" name="exerciseIds" value="${exercise.exerciseId}">

                    <div class="exercise-header">
                            <span class="exercise-name">
                                <i class="fas fa-running"></i>
                                ${exercise.exercise.exerciseName}
                            </span>
                        <span class="remove-exercise" onclick="removeExercise(this)">
                                <i class="fas fa-times"></i>
                            </span>
                    </div>

                    <div class="exercise-details">
                        <div class="detail-box">
                            <div class="detail-label">Set</div>
                            <input type="number" class="exercise-input" name="setNumbers"
                                   value="${exercise.setNumber}" min="1" required>
                        </div>
                        <div class="detail-box">
                            <div class="detail-label">Reps</div>
                            <input type="number" class="exercise-input" name="reps"
                                   value="${exercise.reps}" min="1" required>
                        </div>
                    </div>

                    <div class="exercise-notes">
                            <textarea class="notes-input" name="notes"
                                      placeholder="Add notes...">${exercise.notes}</textarea>
                    </div>
                </div>
            </c:forEach>
        </div>

        <div class="add-exercise-section">
            <h3>Add New Exercise</h3>
            <select class="exercise-select" id="newExercise">
                <option value="">Select an exercise...</option>
                <c:forEach var="availableExercise" items="${availableExercises}">
                    <option value="${availableExercise.exerciseId}">
                            ${availableExercise.exerciseName}
                    </option>
                </c:forEach>
            </select>
            <button type="button" class="btn btn-primary" onclick="addNewExercise()">
                <i class="fas fa-plus"></i> Add Exercise
            </button>
        </div>

        <button type="submit" class="save-button">
            <i class="fas fa-save"></i> Save Changes
        </button>
    </form>
</div>

<script>
    function removeExercise(element) {
        const exerciseCard = element.closest('.exercise-card');
        exerciseCard.remove();
    }

    function addNewExercise() {
        const select = document.getElementById('newExercise');
        const exerciseId = select.value;
        const exerciseName = select.options[select.selectedIndex].text;

        if (!exerciseId) {
            alert('Please select an exercise');
            return;
        }

        const template = `
                <div class="exercise-card">
                    <input type="hidden" name="exerciseIds" value="${exerciseId}">
                    <div class="exercise-header">
                        <span class="exercise-name">
                            <i class="fas fa-running"></i>
                            ${exerciseName}
                        </span>
                        <span class="remove-exercise" onclick="removeExercise(this)">
                            <i class="fas fa-times"></i>
                        </span>
                    </div>
                    <div class="exercise-details">
                        <div class="detail-box">
                            <div class="detail-label">Set</div>
                            <input type="number" class="exercise-input" name="setNumbers"
                                   value="1" min="1" required>
                        </div>
                        <div class="detail-box">
                            <div class="detail-label">Reps</div>
                            <input type="number" class="exercise-input" name="reps"
                                   value="10" min="1" required>
                        </div>
                    </div>
                    <div class="exercise-notes">
                        <textarea class="notes-input" name="notes"
                                  placeholder="Add notes..."></textarea>
                    </div>
                </div>
            `;

        document.querySelector('.exercise-list').insertAdjacentHTML('beforeend', template);
        select.value = '';
    }
</script>
</body>
</html>