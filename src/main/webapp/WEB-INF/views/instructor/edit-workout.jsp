<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Workout | Lifetime Fitness</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
    <style>
        .category-badge {
            background-color: var(--primary-color);
            color: white;
            padding: 0.5rem 1rem;
            border-radius: var(--border-radius);
            font-weight: 500;
        }

        .exercise-title {
            font-size: 1.5rem;
            color: white;
            font-weight: 600;
        }
    </style>
</head>
<body>
<div class="main-content">
    <jsp:include page="../common/verticalNavBar.jsp" />
    <div class="container">
        <div class="flex mb-4">
            <a href="javascript:history.back()" class="btn btn-secondary">
                <i class="fas fa-arrow-left"></i> Back to Workouts
            </a>
        </div>

        <form action="${pageContext.request.contextPath}/instructor/editWorkout" method="POST" id="editWorkoutForm" class="card">
            <input type="hidden" name="workoutId" value="${workout.workoutId}">

            <div class="card-header flex justify-between items-center">
                <h2 class="mb-0">${workout.workoutName}</h2>
                <span class="category-badge">
                    <i class="fas fa-tag"></i> ${workout.category.categoryName}
                </span>
            </div>

            <div class="card-body">
                <div class="grid grid-auto-fit gap-lg">
                    <c:forEach var="exercise" items="${workout.exercises}" varStatus="status">
                        <div class="card">
                            <input type="hidden" name="exerciseIds" value="${exercise.exerciseId}">

                            <div class="flex justify-between items-center mb-3">
                                 <span class="exercise-title">
                                    <i class="fas fa-running"></i>
                                    ${exercise.exercise.exerciseName}
                                </span>
                                <button type="button" class="btn btn-danger" onclick="removeExercise(this)">
                                    <i class="fas fa-times"></i>
                                </button>
                            </div>

                            <div class="grid grid-2 gap-md mb-3">
                                <div class="form-group mb-2">
                                    <label class="form-label">Set</label>
                                    <input type="number" class="form-control" name="setNumbers"
                                           value="${exercise.setNumber}" min="1" required>
                                </div>
                                <div class="form-group mb-2">
                                    <label class="form-label">Reps</label>
                                    <input type="number" class="form-control" name="reps"
                                           value="${exercise.reps}" min="1" required>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="form-label">Notes</label>
                                <textarea class="form-control" name="notes"
                                          placeholder="Add notes...">${exercise.notes}</textarea>
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <div class="card mt-4">
                    <h3 class="mb-3">Add New Exercise</h3>
                    <div class="flex gap-md">
                        <select class="form-control" id="newExercise">
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
                </div>
            </div>

            <div class="flex justify-end">
                <button type="submit" class="btn btn-primary">
                    <i class="fas fa-save"></i> Save Changes
                </button>
            </div>
        </form>
    </div>
</div>

<script>
    function removeExercise(element) {
        const exerciseCard = element.closest('.card');
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

        const template =
            '<div class="card">' +
            '<input type="hidden" name="exerciseIds" value="' + exerciseId + '">' +
            '<div class="flex justify-between items-center mb-3">' +
            '<span class="exercise-title">' +
            '<i class="fas fa-running"></i> ' +
            exerciseName +
            '</span>' +
            '<button type="button" class="btn btn-danger" onclick="removeExercise(this)">' +
            '<i class="fas fa-times"></i>' +
            '</button>' +
            '</div>' +
            '<div class="grid grid-2 gap-md mb-3">' +
            '<div class="form-group mb-2">' +
            '<label class="form-label">Set</label>' +
            '<input type="number" class="form-control" name="setNumbers" value="1" min="1" required>' +
            '</div>' +
            '<div class="form-group mb-2">' +
            '<label class="form-label">Reps</label>' +
            '<input type="number" class="form-control" name="reps" value="10" min="1" required>' +
            '</div>' +
            '</div>' +
            '<div class="form-group">' +
            '<label class="form-label">Notes</label>' +
            '<textarea class="form-control" name="notes" placeholder="Add notes..."></textarea>' +
            '</div>' +
            '</div>';

        document.querySelector('.grid-auto-fit').insertAdjacentHTML('beforeend', template);
        select.value = '';
    }
</script>
</body>
</html>