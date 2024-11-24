<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Create Workout | Lifetime Fitness</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/workoutStylesheet.css">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f4f4f4;
            line-height: 1.6;
        }

        .navbar {
            background: #333;
            color: white;
            padding: 1rem;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }

        .navbar h1 {
            font-size: 1.5rem;
        }

        .container {
            max-width: 800px;
            margin: 2rem auto;
            padding: 0 1rem;
        }

        .back-button {
            display: inline-flex;
            align-items: center;
            gap: 0.5rem;
            padding: 0.8rem 1.5rem;
            background: #6c757d;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            margin-bottom: 1rem;
            transition: background 0.3s;
        }

        .back-button:hover {
            background: #5a6268;
        }

        .workout-form {
            background: white;
            padding: 2rem;
            border-radius: 10px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }

        .form-section {
            margin-bottom: 2rem;
        }

        .form-section h2 {
            color: #333;
            margin-bottom: 1rem;
            padding-bottom: 0.5rem;
            border-bottom: 2px solid #f0f0f0;
        }

        .form-group {
            margin-bottom: 1rem;
        }

        label {
            display: block;
            margin-bottom: 0.5rem;
            color: #555;
            font-weight: 500;
        }

        input[type="text"],
        select,
        textarea {
            width: 100%;
            padding: 0.8rem;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 1rem;
        }

        .exercise-list {
            margin-top: 1rem;
        }

        .exercise-item {
            background: #f8f9fa;
            padding: 1rem;
            border-radius: 5px;
            margin-bottom: 1rem;
            position: relative;
        }

        .exercise-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 1rem;
        }

        .remove-exercise {
            position: absolute;
            top: 0.5rem;
            right: 0.5rem;
            background: none;
            border: none;
            color: #dc3545;
            cursor: pointer;
            padding: 0.5rem;
            font-size: 1.1rem;
        }

        .add-exercise-btn {
            display: inline-flex;
            align-items: center;
            gap: 0.5rem;
            padding: 0.8rem 1.5rem;
            background: #17a2b8;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 1rem;
            margin-top: 1rem;
        }

        .add-exercise-btn:hover {
            background: #138496;
        }

        .submit-btn {
            display: inline-flex;
            align-items: center;
            gap: 0.5rem;
            padding: 1rem 2rem;
            background: #28a745;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 1rem;
            margin-top: 2rem;
        }

        .submit-btn:hover {
            background: #218838;
        }

        .form-note {
            font-size: 0.9rem;
            color: #666;
            margin-top: 0.5rem;
        }
    </style>
</head>
<body>

<div class="container">
    <a href="javascript:history.back()" class="back-button">
        <i class="fas fa-arrow-left"></i> Back to Workouts
    </a>

    <form action="createWorkout" method="POST" class="workout-form" id="workoutForm">
        <input type="hidden" name="clientPhone" value="${param.clientPhone}">

        <div class="form-section">
            <h2>Workout Information</h2>
            <div class="form-group">
                <label for="workoutName">Workout Name</label>
                <input type="text" id="workoutName" name="workoutName" required>
            </div>

            <div class="form-group">
                <label for="categoryId">Workout Category</label>
                <select id="categoryId" name="categoryId" required>
                    <c:forEach var="category" items="${categories}">
                        <option value="${category.categoryId}">${category.categoryName}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="form-section">
            <h2>Exercises</h2>
            <div id="exerciseList" class="exercise-list">
                <!-- Exercise items will be added here -->
            </div>

            <button type="button" class="add-exercise-btn" onclick="addExercise()">
                <i class="fas fa-plus"></i> Add Exercise
            </button>
        </div>

        <button type="submit" class="submit-btn">
            <i class="fas fa-save"></i> Create Workout
        </button>
    </form>
</div>

<script>
    let exerciseCount = 0;

    function addExercise() {
        const exerciseList = document.getElementById('exerciseList');
        const exerciseItem = document.createElement('div');
        exerciseItem.className = 'exercise-item';
        exerciseItem.innerHTML = `
                <button type="button" class="remove-exercise" onclick="removeExercise(this)">
                    <i class="fas fa-times"></i>
                </button>
                <div class="exercise-grid">
                    <div class="form-group">
                        <label for="exercise_${exerciseCount}">Exercise</label>
                        <select id="exercise_${exerciseCount}" name="exercises[${exerciseCount}].exerciseId" required>
                            <c:forEach var="exercise" items="${exercises}">
                                <option value="${exercise.exerciseId}">${exercise.exerciseName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="sets_${exerciseCount}">Sets</label>
                        <input type="number" id="sets_${exerciseCount}" name="exercises[${exerciseCount}].setNumber"
                               min="1" required>
                    </div>
                    <div class="form-group">
                        <label for="reps_${exerciseCount}">Reps</label>
                        <input type="number" id="reps_${exerciseCount}" name="exercises[${exerciseCount}].reps"
                               min="1" required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="notes_${exerciseCount}">Notes</label>
                    <textarea id="notes_${exerciseCount}" name="exercises[${exerciseCount}].notes" rows="2"></textarea>
                </div>
            `;
        exerciseList.appendChild(exerciseItem);
        exerciseCount++;
    }

    function removeExercise(button) {
        button.parentElement.remove();
    }

    // Add first exercise by default
    document.addEventListener('DOMContentLoaded', function() {
        addExercise();
    });
</script>
</body>
</html>