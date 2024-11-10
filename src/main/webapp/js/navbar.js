// script.js
const hamburger = document.querySelector('.hamburger');
const navbar = document.querySelector('.navbar');
const dropdownMenu = document.querySelector('.dropdown-menu');

hamburger.addEventListener('click', () => {
    navbar.classList.toggle('active');
    dropdownMenu.classList.toggle('active');
});
