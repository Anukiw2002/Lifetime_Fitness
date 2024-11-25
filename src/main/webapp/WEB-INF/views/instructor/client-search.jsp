<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Search Client | Lifetime Fitness</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
</head>
<body>
<div class="main-content">
    <jsp:include page="../common/verticalNavBar.jsp" />

    <div class="container">
        <div class="card">
            <h2 class="text-center">Search Client</h2>

            <div class="card-body">
                <form action="searchClient" method="GET" class="form-group">
                    <div class="form-group">
                        <input type="tel"
                               name="phoneNumber"
                               class="form-control"
                               placeholder="Enter client's phone number"
                               pattern="[0-9]{10}"
                               title="Please enter a valid 10-digit phone number"
                               required>
                    </div>

                    <div class="flex justify-center">
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-search"></i> Search
                        </button>
                    </div>
                </form>

                <c:if test="${not empty errorMessage}">
                    <div class="mt-3">
                        <p class="text-danger">${errorMessage}</p>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</div>
</body>
</html>