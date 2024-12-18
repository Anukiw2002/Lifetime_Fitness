<%@ include file="navbar.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/landingPage.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/aos/2.3.4/aos.css" rel="stylesheet">
    <title>Lifetime Fitness</title>

</head>
<body>
<!-- Hero Section -->
<div class="hero-container" id="home">
    <img src="/images/image1.1.jpg" alt="Gym" class="hero-image">
    <div class="hero-content">
        <h1 data-aos="fade-up">FOCUS. GAIN. ATTAIN.</h1>
        <a href="testView?page=page1" class="cta-button" data-aos="fade-up" data-aos-delay="200">JOIN NOW</a>
    </div>
</div>

<!-- Why Choose Us Section HTML -->
<div class="carousel-wrapper">
    <div class="carousel-container why-choose-section" id="carousel1" data-aos="fade-up">
        <h1 class="carousel-title"><u>WHY CHOOSE US?</u></h1>

        <div class="features-grid">
            <div class="feature-card" data-aos="fade-up" data-aos-delay="100">
                <div class="feature-icon">
                    <img src="/images/solidarity.png" alt="Caring Environment Icon" class="icon">
                </div>
                <h3>Caring Environment</h3>
                <p>There's no sense in doing something you're not going to enjoy. It's so important that you really enjoy the environment you're in because that's what will ultimately keep you consistent. Lifetime Fitness is like a loving second home!</p>
            </div>

            <div class="feature-card" data-aos="fade-up" data-aos-delay="200">
                <div class="feature-icon">
                    <img src="/images/crowd-of-users.png" alt="Gym Family Icon" class="icon">
                </div>
                <h3>Join our Gym Family</h3>
                <p>Lifetime Fitness is more than just a gym. We are a family that's there to help you reach your goals. The best way to get where you want to go is to have fun along the way - and it's always more fun doing it alongside people that care!</p>
            </div>

            <div class="feature-card" data-aos="fade-up" data-aos-delay="300">
                <div class="feature-icon">
                    <img src="/images/dumbbell.png" alt="Training Icon" class="icon">
                </div>
                <h3>Personalized Training</h3>
                <p>Every member at Lifetime Fitness gets a free personalized diet and training program that suits their individual lifestyle, food preferences and fitness goals. We believe in helping each individual based on their goals and lifestyle.</p>
            </div>
        </div>

        <!-- Inside the carousel-wrapper div, after the features-grid -->
        <div class="enhanced-carousel" data-aos="fade-up">
            <div class="carousel-slides">
                <div class="slide active">
                    <img src="/images/img1.jpg" alt="Gym Equipment">
                </div>
                <div class="slide">
                    <img src="/images/img2.jpg" alt="Training Area">
                </div>
                <div class="slide">
                    <img src="/images/img3.jpg" alt="Fitness Class">
                </div>
                <div class="slide">
                    <img src="/images/img4.jpg" alt="Personal Training">
                </div>
                <div class="slide">
                    <img src="/images/img5.jpg" alt="Recovery Area">
                </div>
            </div>
            <div class="carousel-controls">
                <button class="prev-slide">❮</button>
                <div class="slide-indicators"></div>
                <button class="next-slide">❯</button>
            </div>
        </div>
</div>

<!-- About Section -->
<section class="about-section" id="about" data-aos="fade-up" >
    <div class="container">
        <h1><u>About Our Gym</u></h1>

        <div class="about-content">
            <!-- Left Column - Text Content -->
            <div class="about-text">
                <!-- Introduction -->
                <div class="intro-text">
                    <p>Lifetime Fitness is a well-maintained gym located in Thalawathugoda. With our state-of-the-art equipment, experienced trainers, and a variety of fitness programs, we cater to all your fitness needs.</p>

                    <p>Our facility is designed to provide a comfortable and motivating environment, whether you are just starting your fitness journey or are a seasoned athlete.</p>
                </div>
                    <p>We pride ourselves on creating a supportive community where members can achieve their health and wellness goals together. Our gym features modern amenities, including:</p>
                    <ul style="padding-left: 20px;">
                        <li>Clean, modern locker rooms</li>
                        <li>Fully-equipped juice bar</li>
                        <li>Ample parking space</li>
                        <li>Professional cleaning staff</li>
                    </ul>
                <br>
                    <p>We provide high quality and high-end equipment to our members. Our gym is equipped with the latest fitness technology to ensure the best workout experience.</p>
            </div>

            <!-- Right Column - Image -->
            <div class="about-image" data-aos="fade-left">
                <img src="/images/ourGym.jpg" alt="Lifetime Fitness Gym Interior"
                     loading="lazy"
                     width="600"
                     height="400">
            </div>
        </div>
    </div>
</section>

<!-- Services Section -->
    <div class="services-section">
        <div class="container">
            <h1 class="text-center"><u>SERVICES</u></h1>
            <div class="services-grid">
                <div class="service-card" data-aos="fade-up" data-aos-delay="100">
                    <img src="/images/one-to-one.png" alt="Personal Training" class="service-icon">
                    <h3>One to One Training</h3>
                    <p>All our personal trainers are certified and ready to help with whatever fitness goal you may have!</p>
                </div>
                <div class="service-card" data-aos="fade-up" data-aos-delay="200">
                    <img src="/images/fitness.png" alt="Fitness Check-Up" class="service-icon">
                    <h3>Fitness Check-Up</h3>
                    <p>Get your BMI and fat percentage checked every month to keep track of your body composition.</p>
                </div>
                <div class="service-card" data-aos="fade-up" data-aos-delay="300">
                    <img src="/images/Soda.png" alt="Supplements" class="service-icon">
                    <h3>Juice & Supplement Bar</h3>
                    <p>We have a supplement bar with pre-workout, whey protein, BCAA, Greek yogurt, and more!</p>
                </div>
            </div>
        </div>
    </div>


<!-- Pricing Section -->
<section class="pricing-section" id="pricing" data-aos="fade-up">
    <div class="container">
        <h1 class="text-center"><u>MEMBER PRICING</u></h1>
        <div class="pricing-grid">
            <div class="pricing-card platinum" data-aos="fade-up" data-aos-delay="100">
                <div class="pricing-header">
                    <h2>PLATINUM</h2>
                    <p>MEMBERSHIP</p>
                </div>
                <div class="pricing-content">
                    <p>Gents – Annual <span class="price">Rs. 85,000</span></p>
                    <p>Ladies – Annual <span class="price">Rs. 85,000</span></p>
                    <p>Couple – Annual <span class="price">Rs. 85,000</span></p>
                    <p class="time-duration">Access: 4:00 am to 12:00 Midnight</p>
                </div>
            </div>

            <div class="pricing-card gold" data-aos="fade-up" data-aos-delay="200">
                <div class="pricing-header">
                    <h2>GOLD</h2>
                    <p>MEMBERSHIP</p>
                </div>
                <div class="pricing-content">
                    <p>Gents – Annual <span class="price">Rs. 48,000</span></p>
                    <p>Ladies – Annual <span class="price">Rs. 48,000</span></p>
                    <p class="time-duration">Access: 4:00 am to 4:00 pm</p>
                </div>
            </div>

            <div class="pricing-card silver" data-aos="fade-up" data-aos-delay="300">
                <div class="pricing-header">
                    <h2>SILVER</h2>
                    <p>MEMBERSHIP</p>
                </div>
                <div class="pricing-content">
                    <p>6 months <span class="price">Rs. 45,000</span></p>
                    <p>3 months <span class="price">Rs. 25,000</span></p>
                    <p>1 month <span class="price">Rs. 15,000</span></p>
                    <p class="time-duration">Access: 4:00 am to 12:00 Midnight</p>
                </div>
            </div>
        </div>
        <div class="day-pass" data-aos="fade-up">
            <p>Day Pass: <span class="price">Rs. 1,500</span></p>
        </div>
    </div>
</section>

<!-- Our Coaches Section -->
    <!-- Our Coaches Section -->
    <section class="coaches-section" data-aos="fade-up">
        <div class="container">
            <h1 class="carousel-title"><u>OUR COACHES</u></h1>
            <div class="coaches-grid">
                <div class="coach-card" data-aos="fade-up" data-aos-delay="100">
                    <div class="coach-image">
                        <img src="/images/Head Coach.png" alt="Head Coach">
                    </div>
                    <div class="coach-info">
                        <h3>
                            Maduranga Perera</h3>
                        <p>Founder, Head Coach</p>
                    </div>
                </div>

                <div class="coach-card" data-aos="fade-up" data-aos-delay="200">
                    <div class="coach-image">
                        <img src="/images/coach4.png" alt="Fitness Trainer">
                    </div>
                    <div class="coach-info">
                        <h3>Avishka Senevirathana</h3>
                        <p>Instructor</p>
                    </div>
                </div>

                <div class="coach-card" data-aos="fade-up" data-aos-delay="300">
                    <div class="coach-image">
                        <img src="/images/kavindu.png" alt="Strength Coach">
                    </div>
                    <div class="coach-info">
                        <h3>Kavindu Pathiratne</h3>
                        <p>Instructor</p>
                    </div>
                </div>
            </div>
        </div>
    </section>

<section class="reviews-container" id="reviews" data-aos="fade-up">
    <div class="container">
        <h1 class="carousel-title"><u>REVIEWS</u></h1>

        <div class="grid grid-3">
            <!-- Review Card 1 -->
            <div class="review-card">
                <div class="review-header">
                    <div class="reviewer-avatar">JD</div>
                    <div class="reviewer-info">
                        <div class="reviewer-name">John Doe</div>
                        <div class="review-date">Member since 2023</div>
                    </div>
                </div>
                <div class="review-rating">★★★★★</div>
                <div class="review-content">
                    "The trainers here are exceptional! They've helped me achieve my fitness goals and pushed me beyond what I thought was possible. The facilities are always clean and well-maintained."
                </div>
            </div>

            <!-- Review Card 2 -->
            <div class="review-card">
                <div class="review-header">
                    <div class="reviewer-avatar">SK</div>
                    <div class="reviewer-info">
                        <div class="reviewer-name">Sarah Kim</div>
                        <div class="review-date">Member since 2024</div>
                    </div>
                </div>
                <div class="review-rating">★★★★★</div>
                <div class="review-content">
                    "I love the variety of equipment and classes available. The atmosphere is welcoming and motivating. It's not just a gym, it's a community that supports each other."
                </div>
            </div>

            <!-- Review Card 3 -->
            <div class="review-card">
                <div class="review-header">
                    <div class="reviewer-avatar">MP</div>
                    <div class="reviewer-info">
                        <div class="reviewer-name">Mike Peterson</div>
                        <div class="review-date">Member since 2022</div>
                    </div>
                </div>
                <div class="review-rating">★★★★★</div>
                <div class="review-content">
                    "The 24/7 access and flexible membership plans make it perfect for my busy schedule. The staff is always friendly and professional. Best gym experience I've had!"
                </div>
            </div>
        </div>
    </div>
</section>

    <!-- Transformations Section -->
    <section class="transformations-section" data-aos="fade-up">
        <div class="container">
            <h1 class="carousel-title"><u>SUCCESS STORIES</u></h1>

            <div class="transformation-grid">
                <!-- First Transformation -->
                <div class="transformation-card" data-aos="fade-up" data-aos-delay="100">
                    <div class="image-comparison">
                        <div class="before-image">
                            <img src="/images/before1.png" alt="Before Transformation">
                            <span class="label">BEFORE</span>
                        </div>
                        <div class="after-image">
                            <img src="/images/after1.png" alt="After Transformation">
                            <span class="label">AFTER</span>
                        </div>
                    </div>
                    <div class="transformation-info">
                        <h3>-15kg in 6 Months</h3>
                        <p>"The trainers at Lifetime Fitness helped me achieve what I thought was impossible!"</p>
                        <span class="time-period">6 Months Journey</span>
                    </div>
                </div>

                <!-- Add more transformation cards similarly -->
                <div class="transformation-card" data-aos="fade-up" data-aos-delay="200">
                    <div class="image-comparison">
                        <div class="before-image">
                            <img src="/images/before2.png" alt="Before Transformation">
                            <span class="label">BEFORE</span>
                        </div>
                        <div class="after-image">
                            <img src="/images/after2.png" alt="After Transformation">
                            <span class="label">AFTER</span>
                        </div>
                    </div>
                    <div class="transformation-info">
                        <h3>-15kg in 6 Months</h3>
                        <p>"The trainers at Lifetime Fitness helped me achieve what I thought was impossible!"</p>
                        <span class="time-period">6 Months Journey</span>
                    </div>
                </div>


                <div class="transformation-card" data-aos="fade-up" data-aos-delay="300">
                    <div class="image-comparison">
                        <div class="before-image">
                            <img src="/images/before3.png" alt="Before Transformation">
                            <span class="label">BEFORE</span>
                        </div>
                        <div class="after-image">
                            <img src="/images/after3.png" alt="After Transformation">
                            <span class="label">AFTER</span>
                        </div>
                    </div>
                    <div class="transformation-info">
                        <h3>-15kg in 6 Months</h3>
                        <p>"The trainers at Lifetime Fitness helped me achieve what I thought was impossible!"</p>
                        <span class="time-period">6 Months Journey</span>
                    </div>
                </div>
            </div>
        </div>
        <div class="stats-container">
            <div class="stat-item" data-aos="fade-up" data-aos-delay="100">
                <div class="stat-number" data-target="500">0</div>
                <div class="stat-label">Success Stories</div>
            </div>
            <div class="stat-item" data-aos="fade-up" data-aos-delay="200">
                <div class="stat-number" data-target="10">0</div>
                <div class="stat-label">Years Experience</div>
            </div>
            <div class="stat-item" data-aos="fade-up" data-aos-delay="300">
                <div class="stat-number" data-target="98%">0</div>
                <div class="stat-label">Goal Achievement</div>
            </div>
        </div>
    </section>


<!-- Scripts -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/aos/2.3.4/aos.js"></script>
<script src="${pageContext.request.contextPath}/js/landingPage.js"></script>
</body>
</html>
<%@ include file="map.jsp" %>
<%@ include file="footer.jsp" %>