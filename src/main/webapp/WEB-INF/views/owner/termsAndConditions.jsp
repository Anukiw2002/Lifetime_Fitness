<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Terms and Conditions</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/termsAndConditions.css">
</head>
<body>
<!-- Main layout structure -->
<div class="flex">
  <!-- Navbar container -->
  <div>
    <jsp:include page="../common/verticalNavBar.jsp" />
  </div>

  <!-- Main content -->
  <div class="main-content">
    <div class="container">
      <div class="card terms-container">
        <div class="card-header">
          <h2 class="text-center mb-0">Terms and Conditions</h2>
        </div>

        <div class="card-body">
          <ul class="terms-list">
            <li>Memberships are non-transferable and non-refundable.</li>
            <li>All members must check in at the front desk upon arrival.</li>
            <li>Proper gym attire, including closed-toe shoes, is required at all times.</li>
            <li>Gym equipment must be wiped down after use using the provided cleaning supplies.</li>
            <li>Members must adhere to all safety guidelines and instructions from staff.</li>
            <li>The gym is not responsible for lost or stolen items.</li>
            <li>Memberships may be revoked due to violation of rules or inappropriate behavior.</li>
            <li>Any damage to gym property caused by a member will result in charges.</li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>