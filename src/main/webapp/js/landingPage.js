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
    let isTransitioning = false;

    // Create indicators
    slides.forEach((_, index) => {
        const dot = document.createElement('span');
        dot.classList.add('dot');
        if (index === 0) dot.classList.add('active');
        dot.style.cssText = `
            width: 8px;
            height: 8px;
            border-radius: 50%;
            background-color: rgba(255, 255, 255, 0.5);
            margin: 0 4px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        `;
        indicators.appendChild(dot);
    });

    const dots = Array.from(indicators.children);

    function updateSlidePosition() {
        if (isTransitioning) return;
        isTransitioning = true;

        const slideWidth = slides[0].offsetWidth;
        const slideMargin = 30;
        const moveAmount = slideWidth + slideMargin;

        // Apply smooth transition
        track.style.transform = `translateX(-${currentIndex * moveAmount}px)`;

        // Update active states
        dots.forEach((dot, index) => {
            dot.style.backgroundColor = index === currentIndex
                ? 'rgba(255, 255, 255, 1)'
                : 'rgba(255, 255, 255, 0.5)';
            dot.classList.toggle('active', index === currentIndex);
        });

        slides.forEach((slide, index) => {
            slide.classList.toggle('active', index === currentIndex);
        });

        // Reset transition flag
        setTimeout(() => {
            isTransitioning = false;
        }, 500);
    }

    // Navigation event handlers
    nextButton.addEventListener('click', () => {
        if (!isTransitioning) {
            currentIndex = (currentIndex + 1) % slides.length;
            updateSlidePosition();
        }
    });

    prevButton.addEventListener('click', () => {
        if (!isTransitioning) {
            currentIndex = (currentIndex - 1 + slides.length) % slides.length;
            updateSlidePosition();
        }
    });

    // Indicator click handlers
    dots.forEach((dot, index) => {
        dot.addEventListener('click', () => {
            if (!isTransitioning && currentIndex !== index) {
                currentIndex = index;
                updateSlidePosition();
            }
        });
    });

    // Initialize position
    updateSlidePosition();

    // Add touch support
    let touchStartX = 0;
    let touchEndX = 0;

    track.addEventListener('touchstart', e => {
        if (!isTransitioning) {
            touchStartX = e.touches[0].clientX;
        }
    });

    track.addEventListener('touchend', e => {
        if (!isTransitioning) {
            touchEndX = e.changedTouches[0].clientX;
            handleSwipe();
        }
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