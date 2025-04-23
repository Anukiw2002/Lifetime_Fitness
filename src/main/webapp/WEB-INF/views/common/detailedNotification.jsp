<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Notification Details</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/detailedNotification.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
</head>
<body>
<jsp:include page="../client/clientVerticalNavbar.jsp" />

<div class="main-content">
  <div class="container">
    <h1 class="mb-4">Notification Details</h1>

    <c:forEach var="notification" items="${notifications}">
      <div class="notification-card card">
        <div class="notification-title">${notification.title}</div>
        <div class="notification-description">
            ${notification.description}
        </div>
        <div class="notification-time">${notification.timeAge}</div>
      </div>
    </c:forEach>

  </div>
</div>
</body>
</html>
