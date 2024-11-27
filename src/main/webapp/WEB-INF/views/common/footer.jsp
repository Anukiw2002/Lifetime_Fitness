<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Footer</title>
    <link rel="stylesheet" href="/css/generalStyles.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        footer {
            background: linear-gradient(to top, var(--bg-darker), var(--bg-dark));
            color: var(--text-color);
            padding: 4rem 0 2rem;
            position: relative;
        }

        footer::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            height: 1px;
            background: linear-gradient(
                    to right,
                    transparent,
                    var(--border-color),
                    transparent
            );
        }

        .footer-container {
            max-width: var(--container-width);
            margin: 0 auto;
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 4rem;
            padding: 0 2rem;
        }

        .social-media {
            display: flex;
            flex-direction: column;
            align-items: flex-start;
        }

        .social-links {
            display: flex;
            gap: 1.5rem;
            margin-bottom: 2rem;
        }

        .social-links a {
            display: flex;
            align-items: center;
            justify-content: center;
            width: 40px;
            height: 40px;
            border-radius: 50%;
            background: rgba(255, 255, 255, 0.1);
            color: var(--text-color);
            text-decoration: none;
            transition: all var(--transition-medium);
        }

        .social-links a:hover {
            background: var(--primary-color);
            transform: translateY(-3px);
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
        }

        .social-media p {
            font-size: 0.9rem;
            opacity: 0.8;
        }

        .footer-center {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
        }

        .footer-logo {
            font-size: 1.5rem;
            font-weight: 800;
            margin-bottom: 1rem;
            background: linear-gradient(45deg, var(--primary-color), var(--accent-color));
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            animation: glow 3s ease-in-out infinite;
        }

        .footer-right {
            text-align: right;
        }

        .footer-right h2 {
            margin-bottom: 1.5rem;
        }

        .footer-right h2 a {
            color: var(--text-color);
            text-decoration: none;
            font-size: 1.5rem;
            transition: color var(--transition-medium);
        }

        .footer-right h2 a:hover {
            color: var(--primary-color);
        }

        .footer-links {
            display: flex;
            flex-direction: column;
            gap: 0.5rem;
        }

        .footer-links a {
            color: var(--text-color);
            text-decoration: none;
            opacity: 0.8;
            transition: all var(--transition-medium);
            font-size: 0.9rem;
        }

        .footer-links a:hover {
            opacity: 1;
            color: var(--primary-color);
            transform: translateX(-3px);
        }

        @keyframes glow {
            0%, 100% { filter: brightness(100%); }
            50% { filter: brightness(150%); }
        }

        @media (max-width: 768px) {
            .footer-container {
                grid-template-columns: 1fr;
                gap: 2rem;
                text-align: center;
            }

            .social-media {
                align-items: center;
            }

            .footer-right {
                text-align: center;
            }

            .footer-links {
                align-items: center;
            }
        }
    </style>
</head>
<body>
<footer>
    <div class="footer-container">
        <div class="social-media">
            <div class="social-links">
                <a href="https://www.facebook.com" target="_blank" aria-label="Facebook">
                    <i class="fab fa-facebook-f"></i>
                </a>
                <a href="https://www.instagram.com" target="_blank" aria-label="Instagram">
                    <i class="fab fa-instagram"></i>
                </a>
                <a href="https://www.twitter.com" target="_blank" aria-label="Twitter">
                    <i class="fab fa-twitter"></i>
                </a>
                <a href="https://www.youtube.com" target="_blank" aria-label="YouTube">
                    <i class="fab fa-youtube"></i>
                </a>
            </div>
            <p>© LIFETIME FITNESS<br>ALL RIGHTS RESERVED</p>
        </div>

        <div class="footer-center">
            <div class="footer-logo">LIFETIME FITNESS</div>
            <p>Transform Your Life</p>
        </div>

        <div class="footer-right">
            <h2><a href="#"><b>JOIN NOW</b></a></h2>
            <div class="footer-links">
                <a href="#">Contact</a>
                <a href="#">Terms of Use</a>
                <a href="#">Privacy Policy</a>
                <a href="#">Cookies</a>
                <a href="#">Settings</a>
            </div>
        </div>
    </div>
</footer>
</body>
</html>