<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Search Client | Lifetime Fitness</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/workoutStylesheet.css">
</head>
<body>
<div class="navbar-container">
    <jsp:include page="../common/verticalNavBar.jsp" />
</div>
<div class="container upcoming-session">
    <div class="search-box">
        <h2>Search Client</h2>
        <form action="searchClient" method="GET" class="search-form">
            <input type="tel" name="phoneNumber" class="search-input"
                   placeholder="Enter client's phone number"
                   pattern="[0-9]{10}"
                   title="Please enter a valid 10-digit phone number"
                   required>
            <button type="submit" class="search-btn">
                <i class="fas fa-search"></i> Search
            </button>
        </form>
        <c:if test="${not empty errorMessage}">
            <p class="error-message">${errorMessage}</p>
        </c:if>
    </div>
</div>
</body>
</html>
