<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
<jsp:include page="../instructor/clientVerticalNavbar.jsp" />
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
            <button class="tab-btn" data-category="strength">Strength</button>
            <button class="tab-btn" data-category="dedication">Dedication</button>
        </div>

        <!-- Weight Loss Category -->
        <div class="leaderboard-category active" id="weight-loss">
            <div class="top-performers" data-aos="fade-up">
                <!-- Second Place -->
                <div class="top-performer second-place">
                    <div class="medal silver">2</div>
                    <div class="performer-avatar">
                        <img src="/images/after1.png" alt="Second Place">
                    </div>
                    <h3>Sarah Williams</h3>
                    <p>-15kg in 3 months</p>
                </div>

                <!-- First Place -->
                <div class="top-performer first-place">
                    <div class="medal gold">1</div>
                    <div class="performer-avatar">
                        <img src="/images/Head Coach.png" alt="First Place">
                    </div>
                    <h3>John Anderson</h3>
                    <p>-20kg in 4 months</p>
                </div>

                <!-- Third Place -->
                <div class="top-performer third-place">
                    <div class="medal bronze">3</div>
                    <div class="performer-avatar">
                        <img src="/images/coach4.png" alt="Third Place">
                    </div>
                    <h3>Emily Chen</h3>
                    <p>-12kg in 2 months</p>
                </div>
            </div>

            <div class="other-ranks" data-aos="fade-up">
                <!-- Other ranks will be populated by JavaScript -->
            </div>
        </div>

        <!-- Strength Category -->
        <div class="leaderboard-category" id="strength">
            <div class="top-performers" data-aos="fade-up">
                <!-- Second Place -->
                <div class="top-performer second-place">
                    <div class="medal silver">2</div>
                    <div class="performer-avatar">
                        <img src="/images/member5.jpg" alt="Second Place">
                    </div>
                    <h3>Mike Johnson</h3>
                    <p>Deadlift: 200kg</p>
                </div>

                <!-- First Place -->
                <div class="top-performer first-place">
                    <div class="medal gold">1</div>
                    <div class="performer-avatar">
                        <img src="/images/member4.jpg" alt="First Place">
                    </div>
                    <h3>Chris Evans</h3>
                    <p>Deadlift: 220kg</p>
                </div>

                <!-- Third Place -->
                <div class="top-performer third-place">
                    <div class="medal bronze">3</div>
                    <div class="performer-avatar">
                        <img src="/images/member6.jpg" alt="Third Place">
                    </div>
                    <h3>Tom Hardy</h3>
                    <p>Deadlift: 180kg</p>
                </div>
            </div>

            <div class="other-ranks" data-aos="fade-up">
                <!-- Other ranks will be populated by JavaScript -->
            </div>
        </div>

        <!-- Dedication Category -->
        <div class="leaderboard-category" id="dedication">
            <div class="top-performers" data-aos="fade-up">
                <!-- Second Place -->
                <div class="top-performer second-place">
                    <div class="medal silver">2</div>
                    <div class="performer-avatar">
                        <img src="/images/member8.jpg" alt="Second Place">
                    </div>
                    <h3>Emma Watson</h3>
                    <p>20 sessions/month</p>
                </div>

                <!-- First Place -->
                <div class="top-performer first-place">
                    <div class="medal gold">1</div>
                    <div class="performer-avatar">
                        <img src="/images/member7.jpg" alt="First Place">
                    </div>
                    <h3>David Lee</h3>
                    <p>25 sessions/month</p>
                </div>

                <!-- Third Place -->
                <div class="top-performer third-place">
                    <div class="medal bronze">3</div>
                    <div class="performer-avatar">
                        <img src="/images/member9.jpg" alt="Third Place">
                    </div>
                    <h3>Sophie Turner</h3>
                    <p>18 sessions/month</p>
                </div>
            </div>

            <div class="other-ranks dedication-ranks" data-aos="fade-up">
                <!-- Other ranks will be populated by JavaScript -->
            </div>
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

<script src="https://cdnjs.cloudflare.com/ajax/libs/aos/2.3.4/aos.js"></script>
<script src="${pageContext.request.contextPath}/js/leaderboard.js"></script>
</body>
</html>