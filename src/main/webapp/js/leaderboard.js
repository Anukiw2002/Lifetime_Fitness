// Initialize AOS
AOS.init({
    duration: 1000,
    once: true,
    offset: 100
});

document.addEventListener('DOMContentLoaded', function() {
    // Tab switching functionality
    const tabButtons = document.querySelectorAll('.tab-btn');
    const categories = document.querySelectorAll('.leaderboard-category');

    function showCategory(categoryId) {
        // First, remove active class from all categories and buttons
        categories.forEach(category => {
            category.classList.remove('active');
        });

        tabButtons.forEach(button => {
            button.classList.remove('active');
        });

        // Then, add active class to selected category and button
        const selectedCategory = document.getElementById(categoryId);
        const selectedButton = document.querySelector(`[data-category="${categoryId}"]`);

        if (selectedCategory && selectedButton) {
            selectedCategory.classList.add('active');
            selectedButton.classList.add('active');
        }
    }

    tabButtons.forEach(button => {
        button.addEventListener('click', (e) => {
            const categoryId = e.target.getAttribute('data-category');
            if (categoryId) {
                showCategory(categoryId);
            }
        });
    });

    // Show initial category if there's a default one, or the first one
    const defaultCategory = document.querySelector('.leaderboard-category');
    if (defaultCategory) {
        const defaultCategoryId = defaultCategory.id;
        if (defaultCategoryId) {
            showCategory(defaultCategoryId);
        } else {
            defaultCategory.classList.add('active');
        }
    }

    // Add hover effect to top performers
    const topPerformers = document.querySelectorAll('.top-performer');
    topPerformers.forEach(performer => {
        performer.addEventListener('mouseenter', () => {
            performer.style.transform = 'translateY(-10px)';
        });

        performer.addEventListener('mouseleave', () => {
            performer.style.transform = 'translateY(0)';
        });
    });

    // Add animation to medals
    const medals = document.querySelectorAll('.medal');
    medals.forEach(medal => {
        medal.addEventListener('mouseenter', () => {
            medal.style.transform = 'translateX(-50%) rotate(360deg) scale(1.2)';
        });

        medal.addEventListener('mouseleave', () => {
            medal.style.transform = 'translateX(-50%) rotate(0) scale(1)';
        });
    });

    // Exercise selection buttons
    const exerciseButtons = document.querySelectorAll('[data-exercise]');
    exerciseButtons.forEach(button => {
        button.addEventListener('click', function() {
            const exerciseType = this.dataset.exercise;
            if (exerciseType) {
                sendExerciseTypeToBackend(exerciseType);
            }
        });
    });

    function sendExerciseTypeToBackend(exerciseType) {
        fetch('/leaderBoardExercise', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: 'exercise=' + encodeURIComponent(exerciseType)
        })
            .then(response => {
                if (response.ok) {
                    // Success - you could reload the page to show new data
                    window.location.reload();
                    // Alternatively, you could update the DOM directly with the new data
                    // if the server returns the data in the response
                } else {
                    console.error('Error sending exercise type to server');
                }
            })
            .catch(error => console.error('Error:', error));
    }
});