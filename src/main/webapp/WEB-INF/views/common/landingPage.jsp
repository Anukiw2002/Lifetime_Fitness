<%@ include file="navbar.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/css/generalStyles.css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/aos/2.3.4/aos.css" rel="stylesheet">
    <title>Lifetime Fitness</title>
    <style>
        /* Core Variables */
        :root {
            --overlay-color: rgba(0, 0, 0, 0.5);
            --card-hover-bg: rgba(255, 255, 255, 0.1);
            --section-padding: 6rem 0;
            --transition-slow: 0.5s ease-in-out;
            --transition-medium: 0.3s ease;
            --glow-shadow: 0 0 20px rgba(var(--primary-color-rgb), 0.3);
        }

        /* Hero Section */
        .hero-container {
            height: 100vh;
            position: relative;
            overflow: hidden;
            display: flex;
            align-items: center;
            justify-content: center;
            text-align: center;
            color: var(--text-color);
        }

        .hero-container::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background: linear-gradient(
                    to bottom,
                    rgba(0, 0, 0, 0.7),
                    rgba(0, 0, 0, 0.4)
            );
            z-index: 1;
        }

        .hero-image {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            object-fit: cover;
            transform: scale(1.1);
            transition: transform 8s ease, opacity 1s ease-in-out;
        }

        .hero-content {
            position: relative;
            z-index: 2;
            max-width: 800px;
            padding: 0 2rem;
        }

        .hero-content h1 {
            font-size: clamp(2.5rem, 5vw, 4.5rem);
            font-weight: 800;
            margin-bottom: 2rem;
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
            opacity: 0;
            transform: translateY(30px);
            animation: fadeInUp 1s ease-out forwards;
        }

        /* About Section */
        .about-section {
            padding: var(--section-padding);
            background: linear-gradient(135deg, var(--bg-dark) 0%, var(--bg-darker) 100%);
            position: relative;
            overflow: hidden;
        }

        .about-section::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: linear-gradient(45deg, rgba(var(--accent-rgb), 0.05) 0%, transparent 100%);
            pointer-events: none;
        }

        .about-section .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 2rem;
        }

        .about-section h1 {
            text-align: center;
            margin-bottom: 3rem;
            color: var(--text-light);
            font-size: 2.5rem;
            font-weight: 700;
            text-transform: uppercase;
            letter-spacing: 2px;
        }

        .about-section h1 u {
            text-decoration: none;
            border-bottom: 4px solid var(--accent-color);
            padding-bottom: 0.5rem;
        }

        .about-content {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 4rem;
            align-items: center;
        }

        .about-text {
            opacity: 0;
            transform: translateX(-30px);
            transition: all var(--transition-medium);
            color: var(--text-light);
            line-height: 1.8;
        }

        .about-text.visible {
            opacity: 1;
            transform: translateX(0);
        }

        .about-text p {
            margin-bottom: 1.5rem;
            font-size: 1.1rem;
        }

        .details-text, .community-text, .footer-text {
            padding-left: 1.5rem;
            border-left: 3px solid var(--accent-color);
            margin: 2rem 0;
        }

        .about-image {
            position: relative;
            transform: translateY(20px);
            opacity: 0;
            transition: all var(--transition-medium);
        }

        .about-image.visible {
            transform: translateY(0);
            opacity: 1;
        }

        .about-image img {
            width: 100%;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
            transition: transform 0.3s ease;
        }

        .about-image img:hover {
            transform: scale(1.02);
        }

        /* Carousel Section */
        .carousel-container {
            padding: var(--section-padding);
            background: var(--bg-dark);
            overflow: hidden;
        }

        .carousel-title {
            text-align: center;
            margin-bottom: 3rem;
        }

        .carousel {
            position: relative;
            padding: 0 60px;
        }

        .carousel-track-container {
            overflow: hidden;
            margin: 0 -15px;
        }

        .carousel-track {
            display: flex;
            transition: transform var(--transition-slow);
            gap: 30px;
            padding: 1rem 0;
        }

        .carousel-slide {
            flex: 0 0 calc(33.333% - 20px);
            min-width: calc(33.333% - 20px);
            transform: scale(0.95);
            transition: transform var(--transition-medium);
            opacity: 0.8;
        }

        .carousel-slide.active {
            transform: scale(1);
            opacity: 1;
        }

        .carousel-btn {
            position: absolute;
            top: 50%;
            transform: translateY(-50%);
            width: 50px;
            height: 50px;
            border-radius: 50%;
            background: var(--primary-color);
            color: white;
            border: none;
            cursor: pointer;
            transition: all var(--transition-medium);
            z-index: 2;
        }

        .carousel-btn:hover {
            background: var(--primary-hover);
            transform: translateY(-50%) scale(1.1);
        }

        .prev-btn { left: 10px; }
        .next-btn { right: 10px; }

        /* Services Section */
        .services-section {
            padding: var(--section-padding);
            background: linear-gradient(135deg, var(--bg-darker) 0%, var(--bg-dark) 100%);
        }

        .services-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 2rem;
            padding: 2rem;
        }

        .service-card {
            background: rgba(255, 255, 255, 0.05);
            border-radius: var(--border-radius);
            padding: 2rem;
            transition: transform var(--transition-medium);
            cursor: pointer;
        }

        .service-card:hover {
            transform: translateY(-10px);
            background: var(--card-hover-bg);
        }

        .service-icon {
            width: 80px;
            height: 80px;
            margin-bottom: 1.5rem;
            transition: transform var(--transition-medium);
        }

        .service-card:hover .service-icon {
            transform: scale(1.1);
        }

        /* Pricing Section */
        .pricing-section {
            padding: var(--section-padding);
            background: linear-gradient(135deg, var(--bg-dark) 0%, #1a1a1a 100%);
            position: relative;
        }

        .pricing-section::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            height: 1px;
            background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.3), transparent);
        }

        .pricing-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(320px, 1fr));
            gap: 2.5rem;
            padding: 3rem 2rem;
            position: relative;
        }

        /* Pricing Cards */
        .pricing-card {
            background: rgba(255, 255, 255, 0.03);
            border-radius: 15px;
            overflow: hidden;
            transition: all var(--transition-medium);
            position: relative;
            backdrop-filter: blur(10px);
            border: 1px solid rgba(255, 255, 255, 0.1);
        }

        /* Card Specific Styles */
        .pricing-card.platinum {
            background: linear-gradient(135deg, rgba(236, 236, 236, 0.05) 0%, rgba(255, 255, 255, 0.07) 100%);
            border: 1px solid rgba(255, 255, 255, 0.2);
        }

        .pricing-card.gold {
            background: linear-gradient(135deg, rgba(255, 215, 0, 0.05) 0%, rgba(218, 165, 32, 0.07) 100%);
            border: 1px solid rgba(255, 215, 0, 0.2);
        }

        .pricing-card.silver {
            background: linear-gradient(135deg, rgba(192, 192, 192, 0.05) 0%, rgba(169, 169, 169, 0.07) 100%);
            border: 1px solid rgba(192, 192, 192, 0.2);
        }

        /* Hover Effects */
        .pricing-card:hover {
            transform: translateY(-10px);
            box-shadow: 0 20px 40px rgba(0, 0, 0, 0.4);
        }

        .pricing-card.platinum:hover {
            box-shadow: 0 20px 40px rgba(255, 255, 255, 0.1);
        }

        .pricing-card.gold:hover {
            box-shadow: 0 20px 40px rgba(255, 215, 0, 0.1);
        }

        .pricing-card.silver:hover {
            box-shadow: 0 20px 40px rgba(192, 192, 192, 0.1);
        }

        /* Card Headers */
        .pricing-header {
            padding: 2.5rem 2rem;
            text-align: center;
            position: relative;
            overflow: hidden;
        }

        .pricing-card.platinum .pricing-header {
            background: linear-gradient(135deg, #2c2c2c 0%, #1a1a1a 100%);
        }

        .pricing-card.gold .pricing-header {
            background: linear-gradient(135deg, #b8860b 0%, #966600 100%);
        }

        .pricing-card.silver .pricing-header {
            background: linear-gradient(135deg, #808080 0%, #666666 100%);
        }

        .pricing-header h2 {
            font-size: 2rem;
            margin: 0;
            font-weight: 700;
            letter-spacing: 2px;
            position: relative;
        }

        .pricing-header p {
            font-size: 1rem;
            opacity: 0.8;
            margin: 0.5rem 0 0;
            letter-spacing: 1px;
        }

        /* Card Content */
        .pricing-content {
            padding: 2.5rem 2rem;
        }

        .pricing-content p {
            margin: 1rem 0;
            font-size: 1.1rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .price {
            font-size: 1.5rem;
            font-weight: bold;
            color: var(--text-color);
            text-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
        }

        .time-duration {
            margin-top: 2rem;
            padding-top: 1rem;
            border-top: 1px solid rgba(255, 255, 255, 0.1);
            font-size: 0.9rem;
            color: rgba(255, 255, 255, 0.7);
            text-align: center;
        }

        /* Popular Badge */
        .pricing-card.platinum::before {
            content: 'MOST POPULAR';
            position: absolute;
            top: 20px;
            right: -35px;
            background: var(--primary-color);
            color: white;
            padding: 0.5rem 3rem;
            transform: rotate(45deg);
            font-size: 0.8rem;
            font-weight: bold;
            z-index: 1;
        }

        /* Day Pass Style */
        .day-pass {
            margin-top: 3rem;
            padding: 1.5rem;
            background: rgba(255, 255, 255, 0.05);
            border-radius: 10px;
            text-align: center;
            border: 1px solid rgba(255, 255, 255, 0.1);
            max-width: 300px;
            margin-left: auto;
            margin-right: auto;
            transition: var(--transition-medium);
        }

        .day-pass:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
        }

        /* Animation Keyframes */
        @keyframes fadeInUp {
            from {
                opacity: 0;
                transform: translateY(30px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        @keyframes float {
            0% { transform: translateY(0); }
            50% { transform: translateY(-10px); }
            100% { transform: translateY(0); }
        }

        @keyframes pulse {
            0% { transform: scale(1); }
            50% { transform: scale(1.05); }
            100% { transform: scale(1); }
        }

        /* CTA Button */
        .cta-button {
            display: inline-block;
            padding: 1.2rem 3rem;
            font-size: 1.2rem;
            font-weight: 600;
            color: white;
            background: var(--primary-color);
            border-radius: var(--border-radius);
            text-decoration: none;
            transition: all var(--transition-medium);
            animation: pulse 2s infinite;
            opacity: 0;
            transform: translateY(20px);
            animation: fadeInUp 1s ease-out 0.5s forwards;
        }

        .cta-button:hover {
            background: var(--primary-hover);
            transform: scale(1.05);
            box-shadow: var(--glow-shadow);
        }

        /* Responsive Design */
        @media (max-width: 768px) {
            .hero-content h1 {
                font-size: 2.5rem;
            }

            .carousel-slide {
                flex: 0 0 calc(100% - 30px);
                min-width: calc(100% - 30px);
            }

            .service-card {
                text-align: center;
            }

            .pricing-grid {
                grid-template-columns: 1fr;
                padding: 1rem;
                gap: 2rem;
            }

            .pricing-header {
                padding: 2rem 1.5rem;
            }

            .pricing-content {
                padding: 2rem 1.5rem;
            }

            .carousel-btn {
                width: 40px;
                height: 40px;
                font-size: 1rem;
            }

            .about-section .container {
                padding: 1rem;
            }

            .about-section h1 {
                font-size: 2rem;
                margin-bottom: 2rem;
            }

            .about-content {
                gap: 2rem;
            }

            .about-text p {
                font-size: 1rem;
            }
        }

        @media (min-width: 769px) and (max-width: 1024px) {
            .carousel-slide {
                flex: 0 0 calc(50% - 30px);
                min-width: calc(50% - 30px);
            }
        }
    </style>
</head>
<body>
<!-- Hero Section -->
<div class="hero-container" id="home">
    <img src="/images/image1.1.jpg" alt="Gym" class="hero-image">
    <div class="hero-content">
        <h1 data-aos="fade-up">FOCUS GAIN ATTAIN</h1>
        <a href="testView?page=page1" class="cta-button" data-aos="fade-up" data-aos-delay="200">JOIN NOW</a>
    </div>
</div>

<!-- Why Choose Us Section -->
<div class="carousel-container" id="carousel1" data-aos="fade-up">
    <h2 class="carousel-title"><u>WHY CHOOSE US?</u></h2>
    <div class="carousel">
        <button class="carousel-btn prev-btn">&#10094;</button>
        <div class="carousel-track-container">
            <ul class="carousel-track">
                <li class="carousel-slide">
                    <img src="/images/gymimg1.jpg" alt="Gym Equipment" style="width: 100%; height: 300px; object-fit: cover; border-radius: var(--border-radius);">
                </li>
                <li class="carousel-slide">
                    <img src="/images/gymimg2.jpg" alt="Training Area" style="width: 100%; height: 300px; object-fit: cover; border-radius: var(--border-radius);">
                </li>
                <li class="carousel-slide">
                    <img src="/images/gymimg3.jpg" alt="Fitness Class" style="width: 100%; height: 300px; object-fit: cover; border-radius: var(--border-radius);">
                </li>
                <!-- Add more slides as needed -->
            </ul>
        </div>
        <button class="carousel-btn next-btn">&#10095;</button>
    </div>
    <div class="carousel-indicators"></div>
</div>

<!-- About Section -->
<section class="about-section" data-aos="fade-up">
    <div class="container">
        <h1>ABOUT OUR GYM</h1>

        <div class="about-content">
            <div class="about-text">
                <!-- Introduction -->
                <p>
                    Lifetime Fitness is a well-maintained gym located in Thalawathugoda. With our state-of-the-art equipment,
                    experienced trainers, and a variety of fitness programs, we cater to all your fitness needs.
                </p>

                <p>
                    Our facility is designed to provide a comfortable and motivating environment, whether you are just
                    starting your fitness journey or are a seasoned athlete.
                </p>

                <!-- Details Section -->
                <div class="details-text">
                    <p>
                        At Lifetime Fitness, we offer a wide range of classes including yoga, Pilates,
                        high-intensity interval training (HIIT), and strength training. Our personalized training
                        sessions ensure that you receive the attention and guidance necessary to achieve your
                        specific fitness goals.
                    </p>
                </div>

                <!-- Community Section -->
                <div class="community-text">
                    <p>
                        We pride ourselves on creating a supportive community where members can achieve their
                        health and wellness goals together. Our gym features modern amenities, including clean
                        locker rooms, a juice bar, and ample parking space for your convenience.
                    </p>
                </div>

                <!-- Footer Section -->
                <div class="footer-text">
                    <p>
                        We provide high quality and high-end equipment to our members. Our gym is equipped
                        with the latest fitness technology to ensure the best workout experience.
                    </p>
                </div>
            </div>

            <div class="about-image" data-aos="fade-left">
                <img src="${pageContext.request.contextPath}/images/circle1.png"
                     alt="Lifetime Fitness Gym Interior"
                     loading="lazy">
            </div>
        </div>
    </div>
</section>

<!-- Services Section -->
<div class="services-section">
    <div class="container">
        <h1 class="text-center" data-aos="fade-up"><u>Services</u></h1>
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
<section class="pricing-section" data-aos="fade-up">
    <div class="container">
        <h1 class="text-center"><u>MEMBER PRICING</u></h1>
        <div class="pricing-grid">
            <div class="pricing-card platinum" data-aos="fade-up" data-aos-delay="100">
                <div class="pricing-header">
                    <h2>PLATINUM</h2>
                    <p>MEMBERSHIP</p>
                </div>
                <div class="pricing-content">
                    <p>Gents – Annual <span class="price">Rs. 65,000</span></p>
                    <p>Ladies – Annual <span class="price">Rs. 55,000</span></p>
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
                    <p>3 months <span class="price">Rs. 35,000</span></p>
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
<div class="carousel-container" id="carousel2" data-aos="fade-up">
    <h2 class="carousel-title"><u>OUR COACHES</u></h2>
    <div class="carousel">
        <button class="carousel-btn prev-btn">&#10094;</button>
        <div class="carousel-track-container">
            <ul class="carousel-track">
                <li class="carousel-slide">
                    <div class="card">
                        <img src="/images/coach1.jpg" alt="Coach 1" style="width: 100%; height: 100%; object-fit: cover; border-radius: var(--border-radius);">
                    </div>
                </li>
                <li class="carousel-slide">
                    <div class="card">
                        <img src="/images/coach2.jpg" alt="Coach 2" style="width: 100%; height: 100%; object-fit: cover; border-radius: var(--border-radius);">
                    </div>
                </li>
                <li class="carousel-slide">
                    <div class="card">
                        <img src="/images/coach3.jpg" alt="Coach 3" style="width: 100%; height: 100%; object-fit: cover; border-radius: var(--border-radius);">
                    </div>
                </li>
                <!-- Add more coaches as needed -->
            </ul>
        </div>
        <button class="carousel-btn next-btn">&#10095;</button>
    </div>
    <div class="carousel-indicators"></div>
</div>

<!-- Scripts -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/aos/2.3.4/aos.js"></script>
<script>
    // Initialize AOS
    AOS.init({
        duration: 1000,
        once: true,
        offset: 100
    });

    // Hero Image Rotation
    document.addEventListener("DOMContentLoaded", () => {
        const heroImages = [
            "/images/image1.1.jpg",
            "/images/image1.2.jpeg",
            "/images/image1.3.jpg",
            "/images/image1.4.jpg"
        ];
        const heroImageElement = document.querySelector(".hero-image");
        let currentIndex = 0;

        const aboutText = document.querySelector('.about-text');
        const aboutImage = document.querySelector('.about-image');

        function changeHeroImage() {
            const nextIndex = (currentIndex + 1) % heroImages.length;
            const nextImage = new Image();
            nextImage.src = heroImages[nextIndex];

            const observer = new IntersectionObserver((entries) => {
                entries.forEach(entry => {
                    if (entry.isIntersecting) {
                        entry.target.classList.add('visible');
                    }
                });
            }, { threshold: 0.2 });

            observer.observe(aboutText);
            observer.observe(aboutImage);

            nextImage.onload = () => {
                heroImageElement.style.opacity = '0';
                setTimeout(() => {
                    heroImageElement.src = heroImages[nextIndex];
                    heroImageElement.style.opacity = '1';
                    currentIndex = nextIndex;
                }, 500);
            };
        }

        setInterval(changeHeroImage, 5000);
    });

    // Enhanced Carousel Functionality
    document.querySelectorAll('.carousel-container').forEach(carousel => {
        const track = carousel.querySelector('.carousel-track');
        const slides = Array.from(track.children);
        const nextButton = carousel.querySelector('.next-btn');
        const prevButton = carousel.querySelector('.prev-btn');
        const indicators = carousel.querySelector('.carousel-indicators');
        let currentIndex = 0;

        // Create indicators
        slides.forEach((_, index) => {
            const dot = document.createElement('span');
            dot.classList.add('dot');
            if (index === 0) dot.classList.add('active');
            indicators.appendChild(dot);
        });

        const dots = Array.from(indicators.children);

        function updateSlidePosition() {
            // Calculate the slide width including gap
            const slideWidth = slides[0].offsetWidth;
            const slideMargin = 30; // Gap between slides
            const moveAmount = slideWidth + slideMargin;

            track.style.transform = `translateX(-${currentIndex * moveAmount}px)`;

            // Update active states
            dots.forEach((dot, index) => {
                dot.classList.toggle('active', index === currentIndex);
            });

            slides.forEach((slide, index) => {
                slide.classList.toggle('active', index === currentIndex);
            });
        }

        // Add click handlers for navigation
        nextButton.addEventListener('click', () => {
            if (currentIndex < slides.length - 1) {
                currentIndex++;
            } else {
                currentIndex = 0;
            }
            updateSlidePosition();
        });

        prevButton.addEventListener('click', () => {
            if (currentIndex > 0) {
                currentIndex--;
            } else {
                currentIndex = slides.length - 1;
            }
            updateSlidePosition();
        });

        // Add click handlers for indicators
        dots.forEach((dot, index) => {
            dot.addEventListener('click', () => {
                currentIndex = index;
                updateSlidePosition();
            });
        });

        // Initialize position
        updateSlidePosition();

        // Add touch support for mobile
        let touchStartX = 0;
        let touchEndX = 0;

        track.addEventListener('touchstart', e => {
            touchStartX = e.touches[0].clientX;
        });

        track.addEventListener('touchend', e => {
            touchEndX = e.changedTouches[0].clientX;
            handleSwipe();
        });

        function handleSwipe() {
            const swipeThreshold = 50;
            const difference = touchStartX - touchEndX;

            if (Math.abs(difference) > swipeThreshold) {
                if (difference > 0 && currentIndex < slides.length - 1) {
                    currentIndex++;
                } else if (difference < 0 && currentIndex > 0) {
                    currentIndex--;
                }
                updateSlidePosition();
            }
        }
    });
</script>
</body>
</html>
<%@ include file="map.jsp" %>
<%@ include file="footer.jsp" %>