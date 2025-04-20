<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lifetime Fitness - Exercise Leaderboard</title>
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

<!-- Add the main container structure -->
<div class="main-content">
    <div class="leaderboard-container">
        <!-- Add a title for this page -->
        <h1 class="leaderboard-title" data-aos="fade-down">
            <div class="title-icon">
                <i class="fa-solid fa-trophy" style="color: #0052cc;"></i>
            </div>
            Exercise Leaderboard
            <div class="title-icon">
                <i class="fa-solid fa-trophy" style="color: #0052cc;"></i>
            </div>
        </h1>

        <!-- Your leaderboard category -->
        <div class="leaderboard-category active" id="exercise-leaderboard">
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
                                <c:choose>
                                    <c:when test="${empty entry.profilePictureBase64}">
                                        <img src="${pageContext.request.contextPath}/images/profilePicAvatar.jpg" alt="Top Performer">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="data:image/jpeg;base64,${entry.profilePictureBase64}" alt="Top Performer">
                                    </c:otherwise>
                                </c:choose>
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

        <!-- Add the motivational quote section that's in your first JSP -->
        <div class="motivation-section" data-aos="fade-up">
            <blockquote>
                "Success isn't always about greatness. It's about consistency. Consistent hard work leads to success. Greatness will come."
                <footer>- Dwayne Johnson</footer>
            </blockquote>
        </div>
    </div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/aos/2.3.4/aos.js"></script>
<script>
    // Initialize AOS
    AOS.init({
        duration: 1000,
        once: true,
        offset: 100
    });

    document.addEventListener('DOMContentLoaded', function() {
        // Add hover effect to top performers
        const topPerformers = document.querySelectorAll('.top-performer');
        topPerformers.forEach(performer => {
            performer.addEventListener('mouseenter', () => {
                performer.style.transform = 'translateY(-10px)';
            });

            performer.addEventListener('mouseleave', () => {
                performer.style.transform = 'translateY(0)';
            });
        });

        // Add animation to medals
        const medals = document.querySelectorAll('.medal');
        medals.forEach(medal => {
            medal.addEventListener('mouseenter', () => {
                medal.style.transform = 'translateX(-50%) rotate(360deg) scale(1.2)';
            });

            medal.addEventListener('mouseleave', () => {
                medal.style.transform = 'translateX(-50%) rotate(0) scale(1)';
            });
        });
    });
</script>
</body>
</html>