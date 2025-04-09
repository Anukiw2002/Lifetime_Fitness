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

            // Populate ranks for the selected category
            const ranksContainer = selectedCategory.querySelector('.other-ranks');
            switch(categoryId) {
                case 'weight-loss':
                    populateRanks(weightLossData, ranksContainer);
                    break;
                case 'strength':
                    populateRanks(strengthData, ranksContainer);
                    break;
                case 'dedication':
                    populateRanks(dedicationData, ranksContainer);
                    break;
            }
        }
    }

    tabButtons.forEach(button => {
        button.addEventListener('click', (e) => {
            const categoryId = e.target.getAttribute('data-category');
            showCategory(categoryId);
        });
    });

    // Populate other ranks with dummy data for each category
    const weightLossData = [
        { name: "Michael Scott", score: "-10kg in 2 months", avatar: "/images/coach4.png" },
        { name: "Lisa Taylor", score: "-9kg in 2 months", avatar: "/images/coach4.png" },
        { name: "David Kim", score: "-8kg in 1.5 months", avatar: "/images/coach4.png" },
        { name: "Jessica Brown", score: "-7kg in 1.5 months", avatar: "/images/coach4.png" },
        { name: "Robert Chen", score: "-6kg in 1 month", avatar: "/images/coach4.png" }
    ];

    const strengthData = [
        { name: "James Wilson", score: "Deadlift: 170kg", avatar: "/images/member10.jpg" },
        { name: "Steve Rogers", score: "Deadlift: 165kg", avatar: "/images/member11.jpg" },
        { name: "Bruce Wayne", score: "Deadlift: 160kg", avatar: "/images/member12.jpg" },
        { name: "Thor Odinson", score: "Deadlift: 155kg", avatar: "/images/member13.jpg" },
        { name: "Peter Parker", score: "Deadlift: 150kg", avatar: "/images/member14.jpg" }
    ];

    const dedicationData = [
        { name: "Natasha Chen", score: "16 sessions/month", avatar: "/images/member15.jpg" },
        { name: "Wanda Maximoff", score: "15 sessions/month", avatar: "/images/member16.jpg" },
        { name: "Carol Danvers", score: "14 sessions/month", avatar: "/images/member17.jpg" },
        { name: "Scott Lang", score: "13 sessions/month", avatar: "/images/member18.jpg" },
        { name: "Tony Stark", score: "12 sessions/month", avatar: "/images/member19.jpg" }
    ];

    function populateRanks(data, container) {
        if (!container) return;

        container.innerHTML = ''; // Clear existing content

        data.forEach((person, index) => {
            const rankItem = document.createElement('div');
            rankItem.className = 'rank-item';
            rankItem.innerHTML = `
                <span class="rank-number">${index + 4}</span>
                <div class="rank-avatar">
                    <img src="${person.avatar}" alt="${person.name}">
                </div>
                <div class="rank-info">
                    <div class="rank-name">${person.name}</div>
                    <div class="rank-score">${person.score}</div>
                </div>
            `;
            container.appendChild(rankItem);
        });
    }

    // Show initial category (weight-loss)
    showCategory('weight-loss');

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
});