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

<div class="main-content">
    <div class="leaderboard-container">
        <h1 class="leaderboard-title" data-aos="fade-down">
            <div class="title-icon">
                <i class="fa-solid fa-trophy" style="color: #0052cc;"></i>
            </div>
            Leaderboard
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
                            <p>${entry.weightLoss}kg</p>
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


        <!-- Streak Category -->
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


        <!-- Exercise Category -->
        <div class="leaderboard-category" id="exercise-selection">
            <div class="exercise-selection-buttons" style="text-align:center; margin-bottom: 20px;">
                <form action="${pageContext.request.contextPath}/leaderBoardExercise" method="POST" style="display:inline;">
                    <button type="submit" name="exercise" value="Push-ups" class="tab-btn">Push-ups</button>
                </form>
                <form action="${pageContext.request.contextPath}/leaderBoardExercise" method="POST" style="display:inline;">
                    <button type="submit" name="exercise" value="Bench-Press" class="tab-btn">Bench-press</button>
                </form>
                <form action="${pageContext.request.contextPath}/leaderBoardExercise" method="POST" style="display:inline;">
                    <button type="submit" name="exercise" value="Squats" class="tab-btn">Squats</button>
                </form>
                <form action="${pageContext.request.contextPath}/leaderBoardExercise" method="POST" style="display:inline;">
                    <button type="submit" name="exercise" value="Deadlifts" class="tab-btn">Dead-lifts</button>
                </form>
                <form action="${pageContext.request.contextPath}/leaderBoardExercise" method="POST" style="display:inline;">
                    <button type="submit" name="exercise" value="Tricep-Dips" class="tab-btn">Tricep-Dips</button>
                </form>
            </div>

            <div class="top-performers" data-aos="fade-up">
                <c:forEach var="entry" items="${leaderboardCategory}" varStatus="status">
                    <c:if test="${status.index < 3}">
                        <div class="top-performer
                <c:choose>
                    <c:when test='${status.index == 0}'> first-place</c:when>
                    <c:when test='${status.index == 1}'> second-place</c:when>
                    <c:when test='${status.index == 2}'> third-place</c:when>
                </c:choose>">
                            <div class="medal
                <c:choose>
                    <c:when test='${status.index == 0}'> gold</c:when>
                    <c:when test='${status.index == 1}'> silver</c:when>
                    <c:when test='${status.index == 2}'> bronze</c:when>
                </c:choose>">${status.index + 1}</div>

                            <div class="performer-avatar">
                                <img src="/images/default-user.png" alt="Top Performer">
                            </div>
                            <h3>${entry.name}</h3>
                            <p>${entry.streak} days</p> <!-- Change this to suit your data (e.g., reps, weight) -->
                        </div>
                    </c:if>
                </c:forEach>
            </div>

            <div class="other-ranks" data-aos="fade-up">
                <c:forEach var="entry" items="${leaderboardCategory}" varStatus="status">
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

        <!-- Motivational Quote Section -->
        <div class="motivation-section" data-aos="fade-up">
            <blockquote>
                "Success isn't always about greatness. It's about consistency. Consistent hard work leads to success. Greatness will come."
                <footer>- Dwayne Johnson</footer>
            </blockquote>
        </div>
    </div>
</div>

<script>
    document.querySelectorAll('.category-tabs .tab-btn').forEach(button => {
        button.addEventListener('click', () => {
            const category = button.dataset.category;
            if (!category) return; // ignore exercise buttons

            // Hide all category content
            document.querySelectorAll('.leaderboard-category').forEach(tab => {
                tab.classList.remove('active');
            });

            // Show the selected category
            document.getElementById(category).classList.add('active');

            // Update active button
            document.querySelectorAll('.category-tabs .tab-btn').forEach(btn => {
                btn.classList.remove('active');
            });
            button.classList.add('active');
        });
    });
</script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/aos/2.3.4/aos.js"></script>
<script src="${pageContext.request.contextPath}/js/leaderboard.js"></script>
</body>
</html>