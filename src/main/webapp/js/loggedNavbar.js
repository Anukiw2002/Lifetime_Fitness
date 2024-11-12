// script.js
const hamburger = document.querySelector('.hamburger');
const navbar = document.querySelector('.navbar');
const dropdownMenu = document.querySelector('.dropdown-menu');

hamburger.addEventListener('click', () => {
    navbar.classList.toggle('active');
    dropdownMenu.classList.toggle('active');
});

// loggedNavbar.js

// Select the notification icon and dropdown elements
const notificationIcon = document.querySelector('.notification-icon');
const notificationDropdown = document.querySelector('.notification-dropdown');

// Toggle dropdown visibility on bell icon click
notificationIcon.addEventListener('click', (event) => {
    event.stopPropagation(); // Prevent the event from bubbling up
    notificationDropdown.classList.toggle('active');
});

// Close dropdown when clicking outside
document.addEventListener('click', (event) => {
    if (!notificationIcon.contains(event.target) && !notificationDropdown.contains(event.target)) {
        notificationDropdown.classList.remove('active');
    }
});
