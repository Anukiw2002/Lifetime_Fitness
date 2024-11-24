document.getElementById('loginForm').addEventListener('submit', function(event) {
    console.log('Form is being submitted.');
    const email = document.querySelector('input[name="email"]').value;
    console.log('Email:', email);

    const password = document.querySelector('input[name="password"]').value;
    console.log('Password:', password ? 'Provided' : 'Not provided');

    console.log('Form action:', this.action);
    console.log('Form method:', this.method);
});
