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

        <div class="grid grid-3">
            <c:forEach var="instructor" items="${instructors}">
                <div class="instructor-card">
                    <div class="instructor-status ${instructor.isActive ? 'active' : 'inactive'}">${instructor.isActive ? 'Active' : 'Inactive'}</div>
                    <div>
                        <c:choose>
                            <c:when test="${empty instructor.profilePictureBase64}">
                                <img src="${pageContext.request.contextPath}/images/profilePicAvatar.jpg" alt="Default Profile Picture">
                            </c:when>
                            <c:otherwise>
                                <img src="data:image/jpeg;base64,${instructor.profilePictureBase64}" class="instructor-image" alt="${instructor.firstName} ${instructor.surname}" >                            </c:otherwise>
                        </c:choose>
                    </div>

                    <h3 class="text-center">${instructor.firstName} ${instructor.surname}</h3>
                    <div class="flex gap-md">
                        <button class="btn-icon" title="Edit"><i class="fas fa-edit"></i></button>
                        <button class="btn-icon" title="Schedule"><i class="fas fa-calendar"></i></button>
                        <button class="btn-icon" title="More"><i class="fas fa-ellipsis-v"></i></button>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
</body>
</html>