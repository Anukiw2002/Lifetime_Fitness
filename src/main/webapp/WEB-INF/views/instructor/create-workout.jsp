<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Create Workout | Lifetime Fitness</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
</head>
<body>
<div class="main-content">
    <jsp:include page="instructorVerticalNavbar.jsp" />
    <div class="container">
        <button onclick="javascript:history.back()" class="btn btn-secondary mb-4">
            <i class="fas fa-arrow-left"></i> Back to Workouts
        </button>

        <c:if test="${not empty client}">
            <div class="flex items-center gap-md">
                <i class="fas fa-user"></i>
                <span>${client.name}</span>
            </div>
        </c:if>

        <form action="createWorkout" method="POST" class="card" id="workoutForm" onsubmit="return validateForm()">
            <input type="hidden" name="clientPhone" value="${param.clientPhone}">

            <c:if test="${not empty error}">
                <div class="error-message">
                    <i class="fas fa-exclamation-circle"></i>
                        ${error}
                </div>
            </c:if>

            <div class="card-body">
                <h2 class="text-center mb-4">Workout Information</h2>
                <div class="form-group">
                    <label class="form-label" for="workoutName">Workout Name</label>
                    <input type="text" id="workoutName" name="workoutName" class="form-control" required>
                </div>

                <div class="form-group">
                    <label class="form-label" for="categoryId">Workout Category</label>
                    <select id="categoryId" name="categoryId" class="form-control" required>
                        <option value="">Select Category</option>
                        <c:forEach var="category" items="${categories}">
                            <option value="${category.categoryId}">${category.categoryName}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="mt-4">
                    <h3 class="mb-3">Exercises</h3>
                    <div id="exerciseList" class="grid gap-lg"></div>
                    <button type="button" class="btn btn-secondary mt-3" onclick="addExercise()">
                        <i class="fas fa-plus"></i> Add Exercise
                    </button>
                </div>
            </div>

            <div class="flex justify-end">
                <button type="submit" class="btn btn-primary">
                    <i class="fas fa-save"></i> Create Workout
                </button>
            </div>
        </form>
    </div>
</div>

<script>
    let exerciseCount = 0;

    function addExercise() {
        const exerciseList = document.getElementById('exerciseList');
        const exerciseItem = document.createElement('div');
        exerciseItem.className = 'card';
        exerciseItem.dataset.index = exerciseCount;
        exerciseItem.innerHTML = `
            <div class="card-body">
                <button type="button" class="btn btn-danger" style="position: absolute; top: 10px; right: 10px;" onclick="removeExercise(this.parentElement.parentElement)">
                    <i class="fas fa-times"></i>
                </button>
                <div class="grid grid-3 gap-md">
                    <div class="form-group">
                        <label class="form-label" for="exercise_${exerciseCount}">Exercise</label>
                        <select id="exercise_${exerciseCount}" name="exerciseId" class="form-control" required>
                            <option value="">Select Exercise</option>
                            <c:forEach var="exercise" items="${exercises}">
                                <option value="${exercise.exerciseId}">${exercise.exerciseName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-label" for="sets_${exerciseCount}">Sets</label>
                        <input type="number" id="sets_${exerciseCount}" name="setNumber" class="form-control" min="1" required value="1">
                    </div>
                    <div class="form-group">
                        <label class="form-label" for="reps_${exerciseCount}">Reps</label>
                        <input type="number" id="reps_${exerciseCount}" name="reps" class="form-control" min="1" required value="1">
                    </div>
                </div>
                <div class="form-group mt-3">
                    <label class="form-label" for="notes_${exerciseCount}">Notes</label>
                    <textarea id="notes_${exerciseCount}" name="notes" rows="2" class="form-control"></textarea>
                </div>
            </div>
        `;
        exerciseList.appendChild(exerciseItem);
        exerciseCount++;
    }

    function removeExercise(element) {
        element.remove();
    }

    function validateWorkoutName() {
        const workoutName = document.getElementById('workoutName').value.trim();
        if (workoutName === '') {
            alert('Please enter a workout name.');
            return false;
        }
        return true;
    }

    function validateForm() {
        if (!validateWorkoutName()) return false;
        const exerciseList = document.getElementById('exerciseList');
        if (exerciseList.children.length === 0) {
            alert('Please add at least one exercise to the workout.');
            return false;
        }
        return true;
    }

    document.addEventListener('DOMContentLoaded', function() {
        addExercise();
    });
</script>
</body>
</html>
