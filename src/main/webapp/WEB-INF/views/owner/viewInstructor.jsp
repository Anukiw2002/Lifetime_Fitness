<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>View Instructor Profile | Lifetime Fitness</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/editInstructor.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
<jsp:include page="../common/verticalNavBar.jsp" />
<div class="main-content">
  <div class="container">
    <div class="card">
      <div class="profile-header">
        <h2>View Instructor Profile</h2>
        <div class="instructor-status ${instructor.isActive ? 'active' : 'inactive'}">
          ${instructor.isActive ? 'Active' : 'Inactive'}
        </div>
      </div>

      <div class="profile-image-section">
        <div class="profile-image">
          <c:choose>
            <c:when test="${empty instructor.profilePictureBase64}">
              <img src="${pageContext.request.contextPath}/images/profilePicAvatar.jpg" alt="Default Profile Picture">
            </c:when>
            <c:otherwise>
              <img src="data:image/jpeg;base64,${instructor.profilePictureBase64}" class="instructor-image" alt="${instructor.firstName} ${instructor.surname}" >
            </c:otherwise>
          </c:choose>
        </div>
      </div>

      <div class="grid grid-3 gap-lg">
        <div class="form-group">
          <div class="form-label">First Name</div>
          <div class="data-display">${instructor.firstName}</div>
        </div>

        <div class="form-group">
          <div class="form-label">Surname</div>
          <div class="data-display">${instructor.surname}</div>
        </div>

        <div class="form-group">
          <div class="form-label">Email Address</div>
          <div class="data-display">${instructor.email}</div>
        </div>
      </div>

      <div class="grid grid-3 gap-lg">
        <div class="form-group">
          <div class="form-label">Date of Birth</div>
          <div class="data-display">${instructor.dateOfBirth}</div>
        </div>
        <div class="form-group">
          <div class="form-label">Phone Number</div>
          <div class="data-display">${instructor.phoneNumber}</div>
        </div>
        <div class="form-group">
          <div class="form-label">NIC number</div>
          <div class="data-display">${instructor.nic}</div>
        </div>
      </div>

      <h2 class="mb-3">Address</h2>
      <div class="grid grid-3 gap-lg">
        <div class="form-group">
          <div class="form-label">House Number</div>
          <div class="data-display">${instructor.houseNumber}</div>
        </div>
        <div class="form-group">
          <div class="form-label">Street Name</div>
          <div class="data-display">${instructor.streetName}</div>
        </div>
        <div class="form-group">
          <div class="form-label">City</div>
          <div class="data-display">${instructor.city}</div>
        </div>
      </div>


        <h2 class="mb-3">Emergency Contact</h2>
        <div class="grid grid-3 gap-lg">
          <div class="form-group">
            <div class="form-label">Contact Name</div>
            <div class="data-display">${instructor.emergencyContactName}</div>
          </div>
          <div class="form-group">
            <div class="form-label">Relationship</div>
            <div class="data-display">${instructor.emergencyContactRelationship}</div>
          </div>
          <div class="form-group">
            <div class="form-label">Contact Phone Number</div>
            <div class="data-display">${instructor.emergencyContactNumber}</div>
          </div>
        </div>

      <h2 class="mb-3">Certifications</h2>
      <c:forEach var="cert" items="${instructor.certificates}">
      <div class="grid grid-2 gap-lg">
        <div class="form-group">
          <div class="form-label">Certification Name</div>
          <div class="data-display">${cert.certificationName}</div>
        </div>
        <div class="form-group">
          <div class="form-label">Issuing Organization</div>
          <div class="data-display">${cert.certificationProvider}</div>
        </div>
      </div>
      </c:forEach>
      <a href="/editInstructor" class="btn btn-primary"> Edit Instructor </a>
    </div>
  </div>
</div>
</body>
</html>