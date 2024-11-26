<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Delete Blog</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/viewBlogs.css" />
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400&display=swap" rel="stylesheet">
    <style>
        .modal {
            display: none;
            position: fixed;
            z-index: 1000;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
        }

        .modal-content {
            background-color: white;
            margin: 15% auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 8px;
            width: 80%;
            max-width: 400px;
            text-align: center;
        }

        .modal-content h3 {
            color: red;
        }

        .modal-buttons {
            display: flex;
            justify-content: center;
            gap: 10px;
        }

        .modal-buttons button, .modal-buttons a {
            padding: 10px 20px;
            border: none;
            cursor: pointer;
            border-radius: 5px;
            text-decoration: none;
        }

        .confirm-button {
            background-color: red;
            color: white;
        }

        .cancel-button {
            background-color: gray;
            color: white;
        }
    </style>
</head>
<body>
<jsp:include page="../common/verticalNavBar.jsp" />

<div class="delete-container">
    <h2>Delete Blog</h2>
    <p>Are you sure you want to delete the blog titled <strong>${blogName}</strong>?</p>
    <button class="confirm-button" onclick="showDeleteModal()">Delete Blog</button>
    <a href="${pageContext.request.contextPath}/viewBlogs" class="cancel-button">Cancel</a>
</div>

<!-- Delete Confirmation Modal -->
<div id="deleteModal" class="modal">
    <div class="modal-content">
        <h3>Confirm Deletion</h3>
        <p>Are you sure you want to delete the blog titled "<strong>${blogName}</strong>"? This action cannot be undone.</p>
        <div class="modal-buttons">
            <form id="deleteBlogForm" action="deleteBlog" method="post">
                <input type="hidden" name="id" value="${blogId}">
                <button type="submit" class="confirm-button">Yes, Delete</button>
            </form>
            <button class="cancel-button" onclick="hideDeleteModal()">Cancel</button>
        </div>
    </div>
</div>

<script>
    const deleteModal = document.getElementById('deleteModal');

    function showDeleteModal() {
        deleteModal.style.display = 'block';
    }

    function hideDeleteModal() {
        deleteModal.style.display = 'none';
    }

    window.onload = function () {
        // Automatically show the modal if blogName exists
        <% if (request.getAttribute("blogName") != null) { %>
        showDeleteModal();
        <% } %>
    };
</script>
</body>
</html>
