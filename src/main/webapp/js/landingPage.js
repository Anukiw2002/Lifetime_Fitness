// Initialize AOS
AOS.init({
    duration: 1000,
    once: true,
    offset: 100
});

// Add this after the AOS initialization
function initializeEnhancedCarousel() {
    const carousel = document.querySelector('.enhanced-carousel');
    if (!carousel) return;

    const slidesContainer = carousel.querySelector('.carousel-slides');
    const slides = carousel.querySelectorAll('.slide');
    const indicatorsContainer = carousel.querySelector('.slide-indicators');
    const prevButton = carousel.querySelector('.prev-slide');
    const nextButton = carousel.querySelector('.next-slide');

    let currentIndex = 0;
    let interval;

    // Create indicators
    slides.forEach((_, index) => {
        const indicator = document.createElement('div');
        indicator.classList.add('indicator');
        if (index === 0) indicator.classList.add('active');
        indicator.addEventListener('click', () => goToSlide(index));
        indicatorsContainer.appendChild(indicator);
    });

    function updateSlides() {
        slidesContainer.style.transform = `translateX(-${currentIndex * 100}%)`;

        // Update active states
        slides.forEach((slide, index) => {
            slide.classList.toggle('active', index === currentIndex);
        });

        const indicators = indicatorsContainer.querySelectorAll('.indicator');
        indicators.forEach((indicator, index) => {
            indicator.classList.toggle('active', index === currentIndex);
        });
    }

    function goToSlide(index) {
        currentIndex = index;
        updateSlides();
        resetInterval();
    }

    function nextSlide() {
        currentIndex = (currentIndex + 1) % slides.length;
        updateSlides();
        resetInterval();
    }

    function prevSlide() {
        currentIndex = (currentIndex - 1 + slides.length) % slides.length;
        updateSlides();
        resetInterval();
    }

    function resetInterval() {
        clearInterval(interval);
        interval = setInterval(nextSlide, 3000);
    }

    // Event listeners
    prevButton.addEventListener('click', prevSlide);
    nextButton.addEventListener('click', nextSlide);

    // Touch support
    let touchStartX = 0;
    let touchEndX = 0;

    carousel.addEventListener('touchstart', e => {
        touchStartX = e.touches[0].clientX;
    });

    carousel.addEventListener('touchend', e => {
        touchEndX = e.changedTouches[0].clientX;
        const difference = touchStartX - touchEndX;

        if (Math.abs(difference) > 50) {
            if (difference > 0) {
                nextSlide();
            } else {
                prevSlide();
            }
        }
    });

    // Start autoplay
    resetInterval();
}

// Call the function after DOM content is loaded
document.addEventListener('DOMContentLoaded', () => {
    initializeEnhancedCarousel();
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

    // Add intersection observer for about section
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

    // Coaches Carousel Functionality
    function initializeCoachesCarousel() {
        const coachesCarousel = document.querySelector('#carousel2');
        if (!coachesCarousel) return;

        const track = coachesCarousel.querySelector('.carousel-track');
        const slides = Array.from(track.children);
        const nextButton = coachesCarousel.querySelector('.next-btn');
        const prevButton = coachesCarousel.querySelector('.prev-btn');
        const dotsContainer = coachesCarousel.querySelector('.carousel-indicators');

        let currentIndex = 0;
        let isTransitioning = false;

        // Create exactly three dots for three coaches
        for (let i = 0; i < 3; i++) {
            const dot = document.createElement('span');
            dot.classList.add('dot');
            if (i === 0) dot.classList.add('active');
            dotsContainer.appendChild(dot);
        }

        const dots = Array.from(dotsContainer.children);

        function updateCoachesCarousel() {
            if (isTransitioning) return;
            isTransitioning = true;

            // Calculate moveAmount based on viewport width
            const slideWidth = slides[0].offsetWidth;
            const moveAmount = slideWidth + 40; // 40px is our new gap

            track.style.transform = `translateX(-${currentIndex * moveAmount}px)`;

            slides.forEach((slide, index) => {
                slide.classList.toggle('active', index === currentIndex);
            });

            dots.forEach((dot, index) => {
                dot.classList.toggle('active', index === currentIndex);
            });

            setTimeout(() => {
                isTransitioning = false;
            }, 500);
        }

        // Event Listeners for coaches carousel
        nextButton.addEventListener('click', () => {
            if (!isTransitioning && currentIndex < slides.length - 1) {
                currentIndex++;
                updateCoachesCarousel();
            }
        });

        prevButton.addEventListener('click', () => {
            if (!isTransitioning && currentIndex > 0) {
                currentIndex--;
                updateCoachesCarousel();
            }
        });

        dots.forEach((dot, index) => {
            dot.addEventListener('click', () => {
                if (!isTransitioning && currentIndex !== index) {
                    currentIndex = index;
                    updateCoachesCarousel();
                }
            });
        });

        // Touch support for coaches carousel
        let touchStartX = 0;
        let touchEndX = 0;

        track.addEventListener('touchstart', e => {
            touchStartX = e.touches[0].clientX;
        });

        track.addEventListener('touchend', e => {
            touchEndX = e.changedTouches[0].clientX;
            const difference = touchStartX - touchEndX;

            if (Math.abs(difference) > 50) { // 50px threshold
                if (difference > 0 && currentIndex < slides.length - 1) {
                    currentIndex++;
                    updateCoachesCarousel();
                } else if (difference < 0 && currentIndex > 0) {
                    currentIndex--;
                    updateCoachesCarousel();
                }
            }
        });

        // Initialize coaches carousel
        updateCoachesCarousel();

        // Handle window resize for coaches carousel
        window.addEventListener('resize', () => {
            updateCoachesCarousel();
        });

        // Preload coach images
        slides.forEach(slide => {
            const img = slide.querySelector('img');
            if (img) {
                const newImg = new Image();
                newImg.src = img.src;
                newImg.onload = () => {
                    img.style.opacity = '1';
                };
            }
        });
    }

    // Initialize coaches carousel
    initializeCoachesCarousel();
});

// Handle any other carousels in the landing page
document.querySelectorAll('.carousel-container').forEach(carousel => {
    // Skip the coaches carousel as it's handled separately
    if (carousel.id === 'carousel2') return;

    const track = carousel.querySelector('.carousel-track');
    if (!track) return; // Skip if no track found

    const slides = Array.from(track.children);
    const nextButton = carousel.querySelector('.next-btn');
    const prevButton = carousel.querySelector('.prev-btn');
    const indicators = carousel.querySelector('.carousel-indicators');
    let currentIndex = 0;
    let isTransitioning = false;

    // Create indicators for other carousels
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

// Add this function to handle the counting animation
function animateStats() {
    const stats = document.querySelectorAll('.stat-number');

    stats.forEach(stat => {
        const target = parseInt(stat.getAttribute('data-target'));
        const duration = 2000;
        const step = target / (duration / 16);
        let current = 0;

        const updateCount = () => {
            current += step;
            if (current < target) {
                // Check if this is the goal achievement stat (98%)
                if (stat.closest('.stat-item').querySelector('.stat-label').textContent.includes('Goal Achievement')) {
                    stat.textContent = Math.round(Math.min(current, target)) + '%';
                } else {
                    // For other stats, keep the '+' suffix
                    if (target <= 10) {
                        stat.textContent = Math.min(current, target).toFixed(1) + '+';
                    } else {
                        stat.textContent = Math.round(Math.min(current, target)) + '+';
                    }
                }
                requestAnimationFrame(updateCount);
            } else {
                // Final value
                if (stat.closest('.stat-item').querySelector('.stat-label').textContent.includes('Goal Achievement')) {
                    stat.textContent = target + '%';
                } else {
                    stat.textContent = target + '+';
                }
            }
        };

        const observer = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    updateCount();
                    observer.unobserve(entry.target);
                }
            });
        }, { threshold: 0.5 });

        observer.observe(stat);
    });
}

// Add this to your existing DOMContentLoaded event listener
document.addEventListener('DOMContentLoaded', () => {
    // ... existing code ...
    animateStats();
});

// Add smooth scrolling behavior
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
        e.preventDefault();

        const target = document.querySelector(this.getAttribute('href'));
        if (target) {
            target.scrollIntoView({
                behavior: 'smooth',
                block: 'start'
            });
        }
    });
});

window.onscroll = function() {
    const backToTop = document.querySelector('.back-to-top');
    if (window.scrollY > 300) {
        backToTop.classList.add('show');
    } else {
        backToTop.classList.remove('show');
    }
};