<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

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

        .modal {
            display: none;
            position: fixed;
            z-index: 1000;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0,0,0,0.5);
        }

        .card-modal {
            background-color: var(--bg-dark);
            margin: 15% auto;
            padding: var(--spacing-xl);
            border-radius: var(--border-radius);
            width: 80%;
            max-width: 500px;
            position: relative;
            color: var(--text-color);
            border: 1px solid var(--border-color);
        }

        .card-modal-header {
            margin-bottom: var(--spacing-md);
        }

        .card-modal-header h3 {
            margin: 0;
            color: var(--danger-color);
        }

        .card-modal-body {
            margin-bottom: var(--spacing-lg);
        }

        .delete-icon {
            position: absolute;
            top: var(--spacing-md);
            right: var(--spacing-md);
            background: none;
            border: none;
            color: var(--danger-color);
            cursor: pointer;
            padding: var(--spacing-xs);
            font-size: var(--font-size-lg);
            border-radius: 50%;
            width: 36px;
            height: 36px;
            display: flex;
            align-items: center;
            justify-content: center;
            transition: var(--transition);
        }

        .delete-icon:hover {
            background-color: rgba(220, 53, 69, 0.1);
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
    <jsp:include page="clientVerticalNavbar.jsp" />
    <div class="container">
        <div class="card mb-4">
            <div class="card-header">
                <h2 class="text-center">Client Information</h2>
            </div>
            <div class="card-body">
                <div class="grid grid-3">
                    <div class="flex items-center gap-md">
                        <i class="fas fa-user"></i>
                        <span>${client.name}</span>
                    </div>
                    <div class="flex items-center gap-md">
                        <i class="fas fa-phone"></i>
                        <span>${client.phoneNumber}</span>
                    </div>
                    <div class="flex items-center gap-md">
                        <i class="fas fa-envelope"></i>
                        <span>${client.email}</span>
                    </div>
                </div>
            </div>
        </div>

        <div class="flex justify-end mb-4">
            <a href="createWorkout?clientPhone=${client.phoneNumber}" class="btn btn-primary">
                <i class="fas fa-plus"></i> Create New Workout
            </a>
        </div>

        <div class="workout-grid">
            <c:forEach var="workout" items="${workouts}">
                <div class="card">
                    <button class="delete-icon" onclick="showDeleteModal('${fn:escapeXml(workout.workoutId)}', '${fn:escapeXml(workout.workoutName)}')">
                        <i class="fas fa-trash-alt"></i>
                    </button>

                    <a href="editWorkout?workoutId=${workout.workoutId}" class="card-body">
                        <div class="flex items-center mb-3">
                            <div class="exercise-icon-container">
                                <i class="fas fa-running exercise-icon"></i>
                            </div>
                            <span class="exercise-title">${workout.workoutName}</span>
                        </div>
                        <div class="flex items-center gap-sm mb-2">
                            <i class="fas fa-tag"></i>
                            <span>${workout.category.categoryName}</span>
                        </div>
                        <div class="flex items-center gap-sm text-muted">
                            <i class="far fa-calendar-alt"></i>
                            <fmt:formatDate value="${workout.createdAtDate}" pattern="MMM dd, yyyy"/>
                        </div>
                    </a>
                </div>
            </c:forEach>
        </div>
    </div>
</div>

<!-- Delete Confirmation Modal -->
<div id="deleteModal" class="modal">
    <div class="card-modal">
        <div class="card-modal-header">
            <h3><i class="fas fa-exclamation-triangle"></i> Confirm Delete</h3>
        </div>
        <div class="card-modal-body">
            <p>Are you sure you want to delete the workout "<span id="workoutName"></span>"?</p>
            <p class="text-muted">This action cannot be undone.</p>
        </div>
        <div class="flex justify-end gap-md">
            <button class="btn btn-secondary" onclick="hideDeleteModal()">Cancel</button>
            <form id="deleteForm" action="deleteWorkout" method="POST" style="display: inline;">
                <input type="hidden" id="workoutId" name="workoutId" value="">
                <input type="hidden" name="clientPhone" value="${client.phoneNumber}">
                <button type="submit" class="btn btn-danger">Delete</button>
            </form>
        </div>
    </div>
</div>

<script>
    // Get the modal
    const modal = document.getElementById('deleteModal');

    // Show the delete confirmation modal
    function showDeleteModal(workoutId, workoutName) {
        document.getElementById('workoutId').value = workoutId;
        document.getElementById('workoutName').textContent = workoutName;
        modal.style.display = 'block';
    }

    // Hide the delete confirmation modal
    function hideDeleteModal() {
        modal.style.display = 'none';
    }

    // Close modal when clicking outside
    window.onclick = function(event) {
        if (event.target == modal) {
            hideDeleteModal();
        }
    }

    // Prevent click events on delete button from propagating to the workout card link
    document.querySelectorAll('.delete-btn').forEach(button => {
        button.addEventListener('click', (e) => {
            e.preventDefault();
            e.stopPropagation();
        });
    });
</script>
</div>
</body>
</html>