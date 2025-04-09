<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Search User UI</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/selectUser.css">
</head>
<body>
<div class="navbar-container">
  <jsp:include page="../common/verticalNavBar.jsp" />
</div>

<div class="content-container">
  <div class="search-bar">
    <form action="${pageContext.request.contextPath}/workoutOptions/search" method="post">
      <label for="searchInput" class="label">Search Client by Phone:</label>
      <input type="text" id="searchInput" name="clientPhone"
             class="search-input"
             placeholder="Enter phone number (e.g., 1234567890)"
             pattern="[0-9]+"
             title="Please enter numbers only"
             required>
      <button type="submit" class="search-button">Search</button>
    </form>

    <c:if test="${not empty error}">
      <div class="error-message" style="color: red; margin-top: 10px;">
          ${error}
      </div>
    </c:if>
  </div>
</div>
</body>
</html>