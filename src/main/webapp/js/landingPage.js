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

    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add('visible');
            }
        });
    });

    document.querySelectorAll('.about-text, .about-image').forEach((el) => observer.observe(el));

    function changeHeroImage() {
        const nextIndex = (currentIndex + 1) % heroImages.length;
        const nextImage = new Image();
        nextImage.src = heroImages[nextIndex];

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