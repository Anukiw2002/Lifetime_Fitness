<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>LIFETIME FITNESS - View Member Profile</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/generalStyles.css">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/editProfile.css">
</head>
<body>
<c:choose>
  <c:when test="${sessionScope.userRole == 'instructor'}">
    <jsp:include page="../instructor/instructorVerticalNavbar.jsp" />
  </c:when>
  <c:when test="${sessionScope.userRole == 'owner'}">
    <jsp:include page="../common/verticalNavBar.jsp" />
  </c:when>
</c:choose>
<div class="main-content">
  <div class="container">
    <div class="card">
      <h1 class="text-center mb-4">View Member Profile</h1>
      <div class="flex flex-col items-center mb-4">
        <div class="profile-picture">
          <c:choose>
            <c:when test="${empty client.profilePictureBase64}">
              <img src="${pageContext.request.contextPath}/images/profilePicAvatar.jpg" alt="Default Profile Picture">
            </c:when>
            <c:otherwise>
              <img src="data:image/jpeg;base64,${client.profilePictureBase64}" alt="Profile Picture">
            </c:otherwise>
          </c:choose>
        </div>
      </div>
      <div class="card mb-4">
        <h2 class="mb-3">Personal Information</h2>
        <div class="form-row">
          <div class="form-group">
            <label class="form-label" for="name">First Name</label>
            <input type="text" id="name" name="name" class="form-control" value="${client.firstName}" readonly>
          </div>
          <div class="form-group">
            <label class="form-label" for="username">Username</label>
            <input type="text" id="username" name="username" class="form-control" value="${client.username}" readonly>
          </div>
        </div>
        <div class="form-row">
          <div class="form-group">
            <label class="form-label" for="dateOfBirth">Date of Birth</label>
            <input type="date" id="dateOfBirth" name="dateOfBirth" class="form-control" value="${client.dateOfBirth}" readonly>
          </div>
          <div class="form-group">
            <label class="form-label" for="gender">Gender</label>
            <select id="gender" name="gender" class="form-control" disabled>
              <option value="Male" ${client.gender == 'Male' ? 'selected' : ''}>Male</option>
              <option value="Female" ${client.gender == 'Female' ? 'selected' : ''}>Female</option>
            </select>
          </div>
        </div>
      </div>

      <div class="card mb-4">
        <h2 class="mb-3">Contact Information</h2>
        <div class="form-row">
          <div class="form-group">
            <label class="form-label" for="emailAddress">Email Address</label>
            <input type="email" id="emailAddress" name="emailAddress" class="form-control" value="${client.email}" readonly>
          </div>
          <div class="form-group">
            <label class="form-label" for="phoneNumber">Phone Number</label>
            <input type="tel" id="phoneNumber" name="phoneNumber" class="form-control" value="${client.phoneNumber}" readonly>
          </div>
        </div>
        <h3 class="mt-3 mb-2">Address</h3>
        <div class="form-row">
          <div class="form-group">
            <label class="form-label" for="houseNo">House Number</label>
            <input type="text" id="houseNo" name="houseNo" class="form-control" value="${client.houseNo}" readonly>
          </div>
          <div class="form-group">
            <label class="form-label" for="streetName">Street Name</label>
            <input type="text" id="streetName" name="streetName" class="form-control" value="${client.streetName}" readonly>
          </div>
        </div>
        <div class="form-row">
          <div class="form-group">
            <label class="form-label" for="city">City</label>
            <input type="text" id="city" name="city" class="form-control" value="${client.city}" readonly>
          </div>
        </div>
      </div>

      <div class="card mb-4">
        <h2 class="mb-3">Emergency Contact</h2>
        <div class="form-row">
          <div class="form-group">
            <label class="form-label" for="emergencyContactName">Contact Name</label>
            <input type="text" id="emergencyContactName" name="emergencyContactName" class="form-control" value="${client.emergencyContactName}" readonly>
          </div>
          <div class="form-group">
            <label class="form-label" for="emergencyContactNumber">Contact Phone</label>
            <input type="tel" id="emergencyContactNumber" name="emergencyContactNumber" class="form-control" value="${client.emergencyContactNumber}" readonly>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>
