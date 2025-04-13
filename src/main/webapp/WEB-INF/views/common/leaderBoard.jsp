<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
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
<jsp:include page="../client/clientVerticalNavbar.jsp" />
<div class="main-content">
    <div class="leaderboard-container">
        <h1 class="leaderboard-title" data-aos="fade-down">
            <div class="title-icon">
                <i class="fa-solid fa-trophy" style="color: #0052cc;"></i>
            </div>
            Leaderbaord
            <div class="title-icon">
                <i class="fa-solid fa-trophy" style="color: #0052cc;"></i>
            </div>
        </h1>

        <div class="category-tabs" data-aos="fade-up">
            <button class="tab-btn active" data-category="weight-loss">Weight Loss</button>
            <button class="tab-btn" data-category="streak">Streak</button>

            <button class="tab-btn" data-category="exercise-selection">Exercises</button>

        </div>

        <!-- Weight Loss Category -->
        <div class="leaderboard-category active" id="weight-loss">
            <div class="top-performers" data-aos="fade-up">
                <c:forEach var="entry" items="${leaderboard}" varStatus="status">
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
                            <p>-${entry.weightLoss}kg</p>
                        </div>
                    </c:if>
                </c:forEach>
            </div>

            <div class="other-ranks" data-aos="fade-up">
                <c:forEach var="entry" items="${leaderboard}" varStatus="status">
                    <c:if test="${status.index >= 3}">
                        <div class="rank-entry">
                            <span class="rank-number">${status.index + 1}</span>
                            <span class="rank-name">${entry.name}</span>
                            <span class="rank-score">-${entry.weightLoss}kg</span>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </div>


        <!-- Strength Category -->
        <div class="leaderboard-category" id="streak">
            <div class="top-performers" data-aos="fade-up">
                <c:forEach var="entry" items="${streakboard}" varStatus="status">
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
                                <img src="/images/default-user.png" alt="Top Performer">
                            </div>
                            <h3>${entry.name}</h3>
                            <p>${entry.streak} days</p>
                        </div>
                    </c:if>
                </c:forEach>
            </div>

            <div class="other-ranks" data-aos="fade-up">
                <c:forEach var="entry" items="${streakboard}" varStatus="status">
                    <c:if test="${status.index >= 3}">
                        <div class="rank-entry">
                            <span class="rank-number">${status.index + 1}</span>
                            <span class="rank-name">${entry.name}</span>
                            <span class="rank-score">${entry.streak} days</span>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </div>


        <!-- Dedication Category -->
        <div class="exercise-selection" id="exercise-selection" style="text-align:center; margin-bottom: 20px;">
            <button class="tab-btn" data-exercise="pushups">Push-ups</button>
            <button class="tab-btn" data-exercise="benchpress">Bench Press</button>
            <button class="tab-btn" data-exercise="squats">Squats</button>
            <button class="tab-btn" data-exercise="deadlifts">Deadlifts</button>
            <button class="tab-btn" data-exercise="tricepdips">Tricep Dips</button>
        </div>


        <!-- Motivational Quote Section -->
    <div class="motivation-section" data-aos="fade-up">
        <blockquote>
            "Success isn't always about greatness. It's about consistency. Consistent hard work leads to success. Greatness will come."
            <footer>- Dwayne Johnson</footer>
        </blockquote>
    </div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/aos/2.3.4/aos.js"></script>
<script src="${pageContext.request.contextPath}/js/leaderboard.js"></script>
</body>
</html>