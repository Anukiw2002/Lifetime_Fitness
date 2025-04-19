<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Instructor Management - Lifetime Fitness</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/instructorManagement.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
<jsp:include page="../common/verticalNavBar.jsp" />
<div class="main-content">
    <div class="container">
        <div class="flex justify-between items-center mb-4">
            <h1>Instructor Management</h1>
            <button class="btn btn-primary" onclick="location.href='/addInstructor'">
                <i class="fas fa-plus"></i> Add Instructor
            </button>
        </div>

        <div class="card mb-4">
            <div class="flex justify-between items-center gap-lg">
                <div class="search-box">
                    <input type="text" class="form-control" placeholder="Search instructors..." id="searchInput">
                    <i class="fas fa-search"></i>
                </div>

                <div class="flex gap-md">
                    <select id="statusFilter" class="form-control">
                        <option value="">All Status</option>
                        <option value="active">Active</option>
                        <option value="inactive">Inactive</option>
                    </select>
                </div>
            </div>
        </div>

        <div class="grid grid-3" id="instructorGrid">
            <c:forEach var="instructor" items="${instructors}">
                <div class="instructor-card" onclick="viewInstructorDetails(${instructor.userId})">
                    <div class="instructor-status ${instructor.isActive ? 'active' : 'inactive'}">${instructor.isActive ? 'Active' : 'Inactive'}</div>
                    <div class="instructor-image-container">
                        <c:choose>
                            <c:when test="${empty instructor.profilePictureBase64}">
                                <img src="${pageContext.request.contextPath}/images/profilePicAvatar.jpg" alt="Default Profile Picture">
                            </c:when>
                            <c:otherwise>
                                <img src="data:image/jpeg;base64,${instructor.profilePictureBase64}" class="instructor-image" alt="${instructor.firstName} ${instructor.surname}">
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <h3>${instructor.firstName} ${instructor.surname}</h3>
                </div>
            </c:forEach>
        </div>
    </div>
</div>

<script>
    function viewInstructorDetails(instructorId) {
        window.location.href = "viewInstructor?id=" + instructorId;
    }

    // Search functionality
    document.getElementById('searchInput').addEventListener('input', function() {
        const searchTerm = this.value.toLowerCase();
        const instructorCards = document.querySelectorAll('.instructor-card');

        instructorCards.forEach(card => {
            const instructorName = card.querySelector('h3').textContent.toLowerCase();
            if (instructorName.includes(searchTerm)) {
                card.style.display = 'block';
            } else {
                card.style.display = 'none';
            }
        });

        // Check if there are any visible cards
        checkEmptyResults();
    });

    // Status filter functionality
    document.getElementById('statusFilter').addEventListener('change', function() {
        const selectedStatus = this.value;
        const instructorCards = document.querySelectorAll('.instructor-card');

        instructorCards.forEach(card => {
            const statusDiv = card.querySelector('.instructor-status');
            const isActive = statusDiv.classList.contains('active');

            if (selectedStatus === '') {
                card.style.display = 'block';
            } else if (selectedStatus === 'active' && isActive) {
                card.style.display = 'block';
            } else if (selectedStatus === 'inactive' && !isActive) {
                card.style.display = 'block';
            } else {
                card.style.display = 'none';
            }
        });

        // Check if there are any visible cards
        checkEmptyResults();
    });

    // Check if there are any visible results
    function checkEmptyResults() {
        const instructorGrid = document.getElementById('instructorGrid');
        const visibleCards = instructorGrid.querySelectorAll('.instructor-card[style="display: block;"]');

        if (visibleCards.length === 0 && instructorGrid.querySelectorAll('.instructor-card').length > 0) {
            const allHidden = true;
            Array.from(instructorGrid.querySelectorAll('.instructor-card')).forEach(card => {
                if (card.style.display !== 'none') {
                    allHidden = false;
                }
            });

            if (allHidden) {
                // No results message
                if (!document.getElementById('no-results')) {
                    const noResults = document.createElement('div');
                    noResults.id = 'no-results';
                    noResults.className = 'card text-center';
                    noResults.style.gridColumn = '1 / -1';
                    noResults.style.padding = 'var(--spacing-xl)';
                    noResults.innerHTML = '<i class="fas fa-search" style="font-size: 3rem; margin-bottom: var(--spacing-lg); color: var(--text-muted);"></i><h3>No instructors found</h3><p>Try adjusting your search or filter criteria</p>';
                    instructorGrid.appendChild(noResults);
                }
            }
        } else {
            // Remove no results message if exists
            const noResults = document.getElementById('no-results');
            if (noResults) {
                noResults.remove();
            }
        }
    }
</script>
</body>
</html>