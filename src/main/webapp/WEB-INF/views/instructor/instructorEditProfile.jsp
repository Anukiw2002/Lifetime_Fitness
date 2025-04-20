<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Edit Profile | Lifetime Fitness</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/editInstructor.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
<jsp:include page="instructorVerticalNavbar.jsp" />
<div class="main-content">
  <div class="container">
    <div class="card">
      <div class="profile-header">
        <h2>Edit Profile</h2>
      </div>

      <form action="${pageContext.request.contextPath}/instructorEditProfile" method="post" enctype="multipart/form-data">
        <div class="profile-image-section">
          <div class="profile-image">
            <c:choose>
              <c:when test="${empty instructor.profilePictureBase64}">
                <img id="profilePreview" src="${pageContext.request.contextPath}/images/profilePicAvatar.jpg" alt="Default Profile Picture">
              </c:when>
              <c:otherwise>
                <img id="profilePreview" src="data:image/jpeg;base64,${instructor.profilePictureBase64}" alt="${instructor.firstName} ${instructor.surname}">
              </c:otherwise>
            </c:choose>
            <div class="image-upload">
              <label for="profilePicture">Change Photo</label>
              <input type="file" id="profilePicture" name="profilePicture" accept="image/*">
            </div>
          </div>
        </div>

        <div class="grid grid-3 gap-lg">
          <div class="form-group">
            <label for="firstName" class="form-label">First Name</label>
            <input type="text" id="firstName" name="firstName" class="form-control" value="${instructor.firstName}" required>
          </div>

          <div class="form-group">
            <label for="surname" class="form-label">Surname</label>
            <input type="text" id="surname" name="surname" class="form-control" value="${instructor.surname}" required>
          </div>

          <div class="form-group">
            <label for="email" class="form-label">Email Address</label>
            <input type="email" id="email" name="email" class="form-control" value="${instructor.email}" required>
          </div>
        </div>

        <div class="grid grid-3 gap-lg">
          <div class="form-group">
            <label for="dateOfBirth" class="form-label">Date of Birth</label>
            <input type="date" id="dateOfBirth" name="dateOfBirth" class="form-control" value="${instructor.dateOfBirth}" required>
          </div>
          <div class="form-group">
            <label for="phoneNumber" class="form-label">Phone Number</label>
            <input type="tel" id="phoneNumber" name="phoneNumber" class="form-control" value="${instructor.phoneNumber}" required>
          </div>
          <div class="form-group">
            <label for="nic" class="form-label">NIC number</label>
            <input type="text" id="nic" name="nic" class="form-control" value="${instructor.nic}" required>
          </div>
        </div>

        <h2 class="mb-3">Address</h2>
        <div class="grid grid-3 gap-lg">
          <div class="form-group">
            <label for="houseNumber" class="form-label">House Number</label>
            <input type="text" id="houseNumber" name="houseNumber" class="form-control" value="${instructor.houseNumber}" required>
          </div>
          <div class="form-group">
            <label for="streetName" class="form-label">Street Name</label>
            <input type="text" id="streetName" name="streetName" class="form-control" value="${instructor.streetName}" required>
          </div>
          <div class="form-group">
            <label for="city" class="form-label">City</label>
            <input type="text" id="city" name="city" class="form-control" value="${instructor.city}" required>
          </div>
        </div>

        <h2 class="mb-3">Emergency Contact</h2>
        <div class="grid grid-3 gap-lg">
          <div class="form-group">
            <label for="emergencyContactName" class="form-label">Contact Name</label>
            <input type="text" id="emergencyContactName" name="emergencyContactName" class="form-control" value="${instructor.emergencyContactName}" required>
          </div>
          <div class="form-group">
            <label for="emergencyContactRelationship" class="form-label">Relationship</label>
            <input type="text" id="emergencyContactRelationship" name="emergencyContactRelationship" class="form-control" value="${instructor.emergencyContactRelationship}" required>
          </div>
          <div class="form-group">
            <label for="emergencyContactNumber" class="form-label">Contact Phone Number</label>
            <input type="tel" id="emergencyContactNumber" name="emergencyContactNumber" class="form-control" value="${instructor.emergencyContactNumber}" required>
          </div>
        </div>

        <h2 class="mb-3">Certifications</h2>
        <div id="certificationsContainer">
          <c:forEach var="cert" items="${instructor.certificates}" varStatus="status">
            <div class="certification-entry grid grid-3 gap-lg mb-3">
              <div class="form-group">
                <label class="form-label">Certification Name</label>
                <input type="text" name="certificationName" class="form-control" value="${cert.certificationName}" required>
              </div>
              <div class="form-group">
                <label class="form-label">Issuing Organization</label>
                <input type="text" name="certificationProvider" class="form-control" value="${cert.certificationProvider}" required>
              </div>
              <div class="cert-remove-container">
                <button type="button" class="remove-cert btn btn-danger">
                  <i class="fas fa-trash"></i> Remove
                </button>
              </div>
            </div>
          </c:forEach>
        </div>

        <div class="mb-3">
          <button type="button" id="addCertification" class="btn btn-secondary">
            <i class="fas fa-plus"></i> Add Certification
          </button>
        </div>

        <div class="form-actions flex justify-between">

          <div class="flex gap-md">
            <button type="button" class="btn btn-secondary">Cancel</button>
            <button type="submit" class="btn btn-primary">Save Changes</button>
          </div>
        </div>
      </form>
    </div>
  </div>
</div>

<script>
  // Preview profile picture before upload
  document.getElementById('profilePicture').addEventListener('change', function(event) {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = function(e) {
        document.getElementById('profilePreview').src = e.target.result;
      };
      reader.readAsDataURL(file);
    }
  });

  // Add new certification fields
  document.getElementById('addCertification').addEventListener('click', function() {
    const container = document.getElementById('certificationsContainer');
    const newEntry = document.createElement('div');
    newEntry.className = 'certification-entry grid grid-3 gap-lg mb-3';
    newEntry.innerHTML = `
            <div class="form-group">
                <label class="form-label">Certification Name</label>
                <input type="text" name="certificationName" class="form-control" required>
            </div>
            <div class="form-group">
                <label class="form-label">Issuing Organization</label>
                <input type="text" name="certificationProvider" class="form-control" required>
            </div>
            <div class="cert-remove-container">
                <button type="button" class="remove-cert btn btn-danger">
                    <i class="fas fa-trash"></i> Remove
                </button>
            </div>
        `;
    container.appendChild(newEntry);

    // Add remove functionality to the new button
    newEntry.querySelector('.remove-cert').addEventListener('click', function() {
      container.removeChild(newEntry);
    });
  });

  // Add remove functionality to existing certification buttons
  document.querySelectorAll('.remove-cert').forEach(button => {
    button.addEventListener('click', function() {
      const entry = this.closest('.certification-entry');
      entry.parentNode.removeChild(entry);
    });
  });
</script>
</body>
</html>