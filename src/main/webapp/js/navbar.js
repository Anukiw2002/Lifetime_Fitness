const hamburger = document.querySelector('.hamburger');
const navbar = document.querySelector('.navbar');
const dropdownMenu = document.querySelector('.dropdown-menu');

hamburger.addEventListener('click', () => {
    navbar.classList.toggle('active');
    dropdownMenu.classList.toggle('active');
});

document.addEventListener('click', (event) => {
    if (!notificationIcon.contains(event.target) && !notificationDropdown.contains(event.target)) {
        notificationDropdown.classList.remove('active');
    }
});