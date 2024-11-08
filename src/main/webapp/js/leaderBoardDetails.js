document.getElementById('leaderboardForm').addEventListener('submit', function(event) {
    event.preventDefault();
    alert('Submit');
    this.reset();
});

function updateIcon(radio) {
    const radios = document.querySelectorAll('input[type="radio"]');
    radios.forEach(r => {
        if (r !== radio) {
            r.checked = false;
        }
    });
}
