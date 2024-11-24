<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Client Workouts | Lifetime Fitness</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/workoutStylesheet.css">

    <style>
        /* Previous styles remain the same */

        /* Modal styles */
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

        .modal-content {
            background-color: #fefefe;
            margin: 15% auto;
            padding: 20px;
            border-radius: 10px;
            width: 80%;
            max-width: 500px;
            position: relative;
        }

        .modal-header {
            margin-bottom: 1rem;
        }

        .modal-header h3 {
            margin: 0;
            color: #dc3545;
        }

        .modal-body {
            margin-bottom: 1.5rem;
        }

        .modal-footer {
            display: flex;
            justify-content: flex-end;
            gap: 1rem;
        }

        .btn {
            padding: 0.5rem 1rem;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 1rem;
        }

        .btn-cancel {
            background-color: #6c757d;
            color: white;
        }

        .btn-delete {
            background-color: #dc3545;
            color: white;
        }

        .btn:hover {
            opacity: 0.9;
        }

        .delete-btn {
            position: absolute;
            top: 1rem;
            right: 1rem;
            background: none;
            border: none;
            color: #dc3545;
            cursor: pointer;
            padding: 0.5rem;
            font-size: 1.1rem;
            border-radius: 50%;
            width: 36px;
            height: 36px;
            display: flex;
            align-items: center;
            justify-content: center;
            transition: background-color 0.3s;
        }

        .delete-btn:hover {
            background-color: rgba(220, 53, 69, 0.1);
        }

        /* Make workout card position relative for delete button positioning */
        .workout-card {
            position: relative;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="client-info">
        <h2>Client Information</h2>
        <div class="client-details">
            <div class="detail-item">
                <i class="fas fa-user"></i> ${client.name}
            </div>
            <div class="detail-item">
                <i class="fas fa-phone"></i> ${client.phoneNumber}
            </div>
            <div class="detail-item">
                <i class="fas fa-envelope"></i> ${client.email}
            </div>
        </div>
    </div>

    <div class="container">
        <div class="client-info">
            <!-- Previous client info content -->
        </div>

        <div class="action-bar">
            <a href="createWorkout?clientPhone=${client.phoneNumber}" class="create-btn">
                <i class="fas fa-plus"></i> Create New Workout
            </a>
        </div>

        <div class="workouts-grid">
            <!-- Previous workout cards content -->
        </div>
    </div>

    <div class="workouts-grid">
        <c:forEach var="workout" items="${workouts}">
            <div class="workout-card">
                <button class="delete-btn" onclick="showDeleteModal('${workout.workoutId}', '${workout.workoutName}')">
                    <i class="fas fa-trash-alt"></i>
                </button>
                <a href="workoutDetails?workoutId=${workout.workoutId}">
                    <div class="workout-header">
                        <i class="fas fa-running workout-icon"></i>
                        <span class="workout-name">${workout.workoutName}</span>
                    </div>
                    <div class="workout-category">
                        <i class="fas fa-tag"></i> ${workout.category.categoryName}
                    </div>
                    <div class="workout-date">
                        <i class="far fa-calendar-alt"></i>
                        <fmt:formatDate value="${workout.createdAtDate}" pattern="MMM dd, yyyy"/>
                    </div>
                </a>
            </div>
        </c:forEach>
    </div>
<!-- Delete Confirmation Modal -->
<div id="deleteModal" class="modal">
    <div class="modal-content">
        <div class="modal-header">
            <h3><i class="fas fa-exclamation-triangle"></i> Confirm Delete</h3>
        </div>
        <div class="modal-body">
            <p>Are you sure you want to delete the workout "<span id="workoutName"></span>"?</p>
            <p>This action cannot be undone.</p>
        </div>
        <div class="modal-footer">
            <button class="btn btn-cancel" onclick="hideDeleteModal()">Cancel</button>
            <form id="deleteForm" action="deleteWorkout" method="POST" style="display: inline;">
                <input type="hidden" id="workoutId" name="workoutId" value="">
                <input type="hidden" name="clientPhone" value="${client.phoneNumber}">
                <button type="submit" class="btn btn-delete">Delete</button>
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