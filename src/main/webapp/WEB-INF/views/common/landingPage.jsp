<%@ include file="navbar.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Reference to the CSS file -->
    <link rel="stylesheet" href="/css/landingPage.css">
    <link rel="stylesheet" href="/css/map.css">


    <title>Landing Page</title>
</head>
<body>
    <div class="container" id="home">
        <img src="/images/image1.1.jpg" alt="Logo" class="logo">
        <h1 >FOCUS GAIN ATTAIN</h1>
        <!-- Added Button -->
        <a href="testView?page=page1" class="cta-button">JOIN NOW</a>
    </div>
<div class="carousel-container" id="carousel1">
    <h2><u>WHY CHOOSE US ?</u></h2>
    </br>

    <div class="carousel">
        <button class="carousel-btn prev-btn">&#10094;</button>
        <div class="carousel-track-container">
            <ul class="carousel-track">
                <li class="carousel-slide"><div class="card"><img src="/images/gymimg1.jpg" alt="Card Image 1" style="width: 100%; height: 100%; object-fit: cover; border-radius: 10px;"></div></li>
                <li class="carousel-slide"><div class="card"><img src="/images/gymimg2.jpg" alt="Card Image 1" style="width: 100%; height: 100%; object-fit: cover; border-radius: 10px;"></div></li>
                <li class="carousel-slide"><div class="card"><img src="/images/gymimg3.jpg" alt="Card Image 1" style="width: 100%; height: 100%; object-fit: cover; border-radius: 10px;"></div></li>
                <li class="carousel-slide"><div class="card"><img src="/images/gymimg3.jpg" alt="Card Image 1" style="width: 100%; height: 100%; object-fit: cover; border-radius: 10px;"></div></li>
                <li class="carousel-slide"><div class="card"><img src="/images/gymimg3.jpg" alt="Card Image 1" style="width: 100%; height: 100%; object-fit: cover; border-radius: 10px;"></div></li>
                <li class="carousel-slide"><div class="card"><img src="/images/gymimg3.jpg" alt="Card Image 1" style="width: 100%; height: 100%; object-fit: cover; border-radius: 10px;"></div></li>
            </ul>
        </div>
        <button class="carousel-btn next-btn">&#10095;</button>
    </div>
    <div  class="carousel-indicators" id="about">

    </div>
</div>

<div class="about-section">
    <h1 ><u>ABOUT OUR GYM</u></h1>

    <p class="intro-text">
        Lifetime Fitness is a well-maintained gym located in Thalawathugoda. With our state-of-the-art equipment,
        experienced trainers, and a variety of fitness programs, we cater to all your fitness needs. Our facility
        is designed to provide a comfortable and motivating environment, whether you are just starting your fitness
        journey or are a seasoned athlete.
    </p><br/>
    <p class="details-text">
        At Lifetime Fitness, we offer a wide range of classes including yoga, Pilates, high-intensity interval
        training (HIIT), and strength training. Our personalized training sessions ensure that you receive the
        attention and guidance necessary to achieve your specific fitness goals.
    </p><br/>
    <p class="community-text">
        We pride ourselves on creating a supportive community where members can achieve their health and wellness
        goals together. Our gym features modern amenities, including clean locker rooms, a juice bar, and ample
        parking space for your convenience.
    </p>
    <div class="footer">
        <div class="circle">
            <img src="/images/circle1.png" alt="Circle Image 1" style="width: 100%; height: 100%; border-radius: 70%;">
        </div>
        <div class="circle">
            <img src="/images/circle1.png" alt="Circle Image 1" style="width: 100%; height: 100%; border-radius: 50%;">
        </div>
        <p class="footer-text">
            We provide high quality and high end equipment to our members. Our gym is equipped with the latest.
        </p>
    </div>
</div>

<div class="services-section">
    <h1 class="service-h1"><u>Services</u></h1>
    <div class="service">
        <img src="/images/one-to-one.png" alt="One to One Training" class="service-icon">
        <div class="service-details">
            <h2><u>One to One Training</u></h2>
            <p>All our personal trainers are certified and ready to help with whatever fitness goal you may have!</p>
        </div>
    </div>
    <div class="service">
        <img src="/images/fitness.png" alt="Fitness Check-Up" class="service-icon">
        <div class="service-details">
            <h2><u>Fitness Check-Up</u></h2>
            <p>Get your BMI and fat percentage checked every month to keep track of your body composition.</p>
        </div>
    </div>
    <div class="service">
        <img src="/images/Soda.png" alt="Juice & Supplement Bar" class="service-icon">
        <div class="service-details">
            <h2><u>Juice & Supplement Bar</u></h2>
            <p>We have a supplement bar at the gym with pre-workout, whey protein, BCAA, Greek yogurt, and more!</p>
        </div>
    </div>
</div>

<div class="carousel-container" id="carousel2">
    <h2><u>OUR COACHERS</u></h2>
    </br>
    <div class="carousel">
        <button class="carousel-btn prev-btn">&#10094;</button>
        <div class="carousel-track-container">
            <ul class="carousel-track">
                <li class="carousel-slide"><div class="card"><div class="card"><img src="/images/coach1.jpg" alt="Card Image 1" style="width: 100%; height: 100%; object-fit: cover; border-radius: 10px;"></div></li>
                <li class="carousel-slide"><div class="card"><div class="card"><img src="/images/coach2.jpg" alt="Card Image 1" style="width: 100%; height: 100%; object-fit: cover; border-radius: 10px;"></div></li>
                <li class="carousel-slide"><div class="card"><div class="card"><img src="/images/coach3.jpg" alt="Card Image 1" style="width: 100%; height: 100%; object-fit: cover; border-radius: 10px;"></div></li>
                <li class="carousel-slide"><div class="card"><div class="card"><img src="/images/coach3.jpg" alt="Card Image 1" style="width: 100%; height: 100%; object-fit: cover; border-radius: 10px;"></div></li>
                <li class="carousel-slide"><div class="card"><div class="card"><img src="/images/coach1.jpg" alt="Card Image 1" style="width: 100%; height: 100%; object-fit: cover; border-radius: 10px;"></div></li>
                <li class="carousel-slide"><div class="card"><div class="card"><img src="/images/coach1.jpg" alt="Card Image 1" style="width: 100%; height: 100%; object-fit: cover; border-radius: 10px;"></div></li>

            </ul>
        </div>
        <button class="carousel-btn next-btn">&#10095;</button>
    </div>
    <div class="carousel-indicators" id="pricing">

    </div>
</div>
<section class="pricing-section">
    <h1><u>MEMBER PRICING</u></h1>
    <div class="pricing-container">
        <div class="pricing-card platinum">
            <div class="card-header">
                <h2>PLATINUM</h2>
                <p>MEMBERSHIP</p>
            </div>
            <div class="card-body">
                <p>Gents – Annual: <span>Rs. 85,000</span></p>
                <p>Ladies – Annual: <span>Rs. 85,000</span></p>
                <p>Couple – Annual: <span>Rs. 85,000</span></p>
                <p class="time-duration">Time Duration: 4:00 am to 12:00 Midnight</p>
            </div>
        </div>
        <div class="pricing-card gold">
            <div class="card-header">
                <h2>GOLD</h2>
                <p>MEMBERSHIP</p>
            </div>
            <div class="card-body">
                <p>Gents – Annual: <span>Rs. 48,000</span></p>
                <p>Ladies – Annual: <span>Rs. 48,000</span></p>
                <p class="time-duration">Time Duration: 4:00 am to 4:00 pm</p>
            </div>
        </div>
        <div class="pricing-card silver">
            <div class="card-header">
                <h2>SILVER</h2>
                <p>MEMBERSHIP</p>
            </div>
            <div class="card-body">
                <p>Individual – 6 months: <span>Rs. 45,000</span></p>
                <p>Individual – 3 months: <span>Rs. 25,000</span></p>
                <p>Individual – 1 month: <span>Rs. 15,000</span></p>
                <p class="time-duration">Time Duration: 4:00 am to 12:00 Midnight</p>
            </div>
        </div>
    </div>
    <br
    <p id="contact us">Day Pass: Rs. 1,500</p>
</section>


<script src="<%= request.getContextPath() %>/js/landingPage.js"></script>
</body>
</html>
<%@ include file="map.jsp" %>

<%@ include file="footer.jsp" %>

