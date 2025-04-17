<%--
  Created by IntelliJ IDEA.
  User: Ishan Maduranga
  Date: 4/13/2025
  Time: 3:50 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lifetime Fitness - Leaderboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/leaderBoard.css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/aos/2.3.4/aos.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body>
<c:choose>
    <c:when test="${sessionScope.role == 'client'}">
        <jsp:include page="../client/clientVerticalNavbar.jsp" />
    </c:when>
    <c:when test="${sessionScope.role == 'instructor'}">
        <jsp:include page="../instructor/instructorVerticalNavbar.jsp" />
    </c:when>
    <c:when test="${sessionScope.role == 'owner'}">
        <jsp:include page="../common/verticalNavBar.jsp" />
    </c:when>
</c:choose>

<div class="leaderboard-category active" >
    <div class="top-performers" data-aos="fade-up">
        <c:forEach var="entry" items="${exerciseList}" varStatus="status">
            <c:if test="${status.index < 3}">
                <div class="top-performer
                    <c:choose>
                        <c:when test='${status.index == 0}'>first-place</c:when>
                        <c:when test='${status.index == 1}'>second-place</c:when>
                        <c:when test='${status.index == 2}'>third-place</c:when>
                    </c:choose>">
                    <div class="medal
                        <c:choose>
                            <c:when test='${status.index == 0}'>gold</c:when>
                            <c:when test='${status.index == 1}'>silver</c:when>
                            <c:when test='${status.index == 2}'>bronze</c:when>
                        </c:choose>">${status.index + 1}</div>

                    <div class="performer-avatar">
                        <!-- You can customize avatar logic here based on user or keep a default -->
                        <img src="/images/default-user.png" alt="Top Performer">
                    </div>
                    <h3>${entry.name}</h3>
                    <p>-${entry.amount}kg</p>
                </div>
            </c:if>
        </c:forEach>
    </div>

    <div class="other-ranks" data-aos="fade-up">
        <c:forEach var="entry" items="${exerciseList}" varStatus="status">
            <c:if test="${status.index >= 3}">
                <div class="rank-entry">
                    <span class="rank-number">${status.index + 1}</span>
                    <span class="rank-name">${entry.name}</span>
                    <span class="rank-score">-${entry.amount}kg</span>
                </div>
            </c:if>
        </c:forEach>
    </div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/aos/2.3.4/aos.js"></script>
<script src="${pageContext.request.contextPath}/js/leaderboardExercise.js"></script>
</body>
</html>
