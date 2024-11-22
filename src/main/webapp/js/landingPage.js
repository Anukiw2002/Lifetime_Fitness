document.querySelectorAll('.carousel-container').forEach(carousel => {
    const track = carousel.querySelector('.carousel-track');
    const slides = Array.from(track.children);
    const nextButton = carousel.querySelector('.next-btn');
    const prevButton = carousel.querySelector('.prev-btn');
    const indicators = carousel.querySelector('.carousel-indicators');
    let currentIndex = 0;

    // Create dots based on the number of slides
    slides.forEach((_, index) => {
        const dot = document.createElement('span');
        dot.classList.add('dot');
        if (index === 0) dot.classList.add('active');
        indicators.appendChild(dot);
    });

    const dots = Array.from(indicators.children);

    function updateSlidePosition() {
        track.style.transform = `translateX(-${currentIndex * (slides[0].offsetWidth + 30)}px)`;
        dots.forEach(dot => dot.classList.remove('active'));
        dots[currentIndex].classList.add('active');
    }

    nextButton.addEventListener('click', () => {
        if (currentIndex < slides.length - 1) {
            currentIndex++;
        } else {
            currentIndex = 0; // Loop back to the first image
        }
        updateSlidePosition();
    });

    prevButton.addEventListener('click', () => {
        if (currentIndex > 0) {
            currentIndex--;
        } else {
            currentIndex = slides.length - 1; // Loop to the last image
        }
        updateSlidePosition();
    });

    dots.forEach((dot, index) => {
        dot.addEventListener('click', () => {
            currentIndex = index;
            updateSlidePosition();
        });
    });

});

document.addEventListener("DOMContentLoaded", () => {
    const heroImages = [
        "/images/image1.1.jpg",
        "/images/image1.2.jpeg",
        "/images/image1.3.jpg",
        "/images/image1.4.jpg"
    ]; // List of images to rotate
    const heroImageElement = document.querySelector(".container img");
    let currentIndex = 0;

    function changeHeroImage() {
        currentIndex = (currentIndex + 1) % heroImages.length; // Cycle through images
        heroImageElement.src = heroImages[currentIndex]; // Update the image source
    }

    // Change image every 5 seconds
    setInterval(changeHeroImage, 5000);
});

