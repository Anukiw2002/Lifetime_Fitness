<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lifetime Fitness - Choose Your Plan</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/signUp4.css">
</head>
<body>
<div class="container">
    <!-- Logo -->
    <div class="logo-container">
        <img src="${pageContext.request.contextPath}/images/LogoWhite.png" alt="Lifetime Fitness" class="logo">
    </div>

    <!-- Progress Tracker -->
    <div class="progress-steps">
        <div class="step completed">
            <div class="step-number">1</div>
            <span>General details</span>
        </div>
        <div class="step-line1"></div>
        <div class="step completed">
            <div class="step-number">2</div>
            <span>Medical History</span>
        </div>
        <div class="step-line1"></div>
        <div class="step active">
            <div class="step-number">3</div>
            <span>Membership plan</span>
        </div>
    </div>

    <!-- Main Content -->
    <h1 class="main-title" style="color: white;">Choose your plan</h1>

    <div class="membership-grid">
        <!-- Platinum Membership -->
        <div class="membership-card platinum">
            <div class="popular-badge">Most Popular</div>
            <div class="card-header">
                <h2 style="color: white;"><i class="fas fa-star"></i> Platinum Membership</h2>
                <div class="timing">
                    <i class="far fa-clock"></i>
                    4:00 am to 12:00 Midnight
                </div>
            </div>
            <div class="card-content">
                <div class="features">
                    <div class="feature"><i class="fas fa-check"></i> Access to all premium equipment</div>
                    <div class="feature"><i class="fas fa-check"></i> Personal trainer sessions</div>
                    <div class="feature"><i class="fas fa-check"></i> Spa & Sauna access</div>
                    <div class="feature"><i class="fas fa-check"></i> Group classes included</div>
                </div>
                <div class="price-options">
                    <div class="price-row">
                        <span class="label">Gents</span>
                        <div class="price-select">
                            <span class="price">Rs. 65,000</span>
                            <a href="/payment" class="select-btn">Select</a>
                        </div>
                    </div>
                    <div class="price-row">
                        <span class="label">Ladies</span>
                        <div class="price-select">
                            <span class="price">Rs. 65,000</span>
                            <button class="select-btn">Select</button>
                        </div>
                    </div>
                    <div class="price-row">
                        <span class="label">Couple</span>
                        <div class="price-select">
                            <span class="price">Rs. 85,000</span>
                            <button class="select-btn">Select</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Gold Membership -->
        <div class="membership-card gold">
            <div class="card-header">
                <h2 style="color: white;"><i class="fas fa-star"></i> Gold Membership</h2>
                <div class="timing">
                    <i class="far fa-clock"></i>
                    4:00 am to 4:30 pm
                </div>
            </div>
            <div class="card-content">
                <div class="features">
                    <div class="feature"><i class="fas fa-check"></i> Access to all equipment</div>
                    <div class="feature"><i class="fas fa-check"></i> 2 Personal trainer sessions</div>
                    <div class="feature"><i class="fas fa-check"></i> Group classes included</div>
                </div>
                <div class="price-options">
                    <div class="price-row">
                        <span class="label">Gents</span>
                        <div class="price-select">
                            <span class="price">Rs. 48,000</span>
                            <button class="select-btn">Select</button>
                        </div>
                    </div>
                    <div class="price-row">
                        <span class="label">Ladies</span>
                        <div class="price-select">
                            <span class="price">Rs. 48,000</span>
                            <button class="select-btn">Select</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Silver Membership -->
        <div class="membership-card silver">
            <div class="card-header">
                <h2 style="color: white;"><i class="fas fa-star"></i> Silver Membership</h2>
                <div class="timing">
                    <i class="far fa-clock"></i>
                    4:00 am to 12:00 Midnight
                </div>
            </div>
            <div class="card-content">
                <div class="features">
                    <div class="feature"><i class="fas fa-check"></i> Basic equipment access</div>
                    <div class="feature"><i class="fas fa-check"></i> 1 Personal trainer session</div>
                    <div class="feature"><i class="fas fa-check"></i> Pay-per-class option</div>
                </div>
                <div class="price-options">
                    <div class="price-row">
                        <span class="label">6 Months</span>
                        <div class="price-select">
                            <span class="price">Rs. 45,000</span>
                            <button class="select-btn">Select</button>
                        </div>
                    </div>
                    <div class="price-row">
                        <span class="label">3 Months</span>
                        <div class="price-select">
                            <span class="price">Rs. 35,000</span>
                            <button class="select-btn">Select</button>
                        </div>
                    </div>
                    <div class="price-row">
                        <span class="label">1 Month</span>
                        <div class="price-select">
                            <span class="price">Rs. 15,000</span>
                            <button class="select-btn">Select</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Day Pass -->
        <div class="membership-card day-pass">
            <div class="card-header">
                <h2 style="color: white;"><i class="fas fa-star"></i> Day Pass</h2>
                <div class="timing">
                    <i class="far fa-clock"></i>
                    4:00 am to 12:00 Midnight
                </div>
            </div>
            <div class="card-content">
                <div class="features">
                    <div class="feature"><i class="fas fa-check"></i> Single day access to facilities</div>
                    <div class="feature"><i class="fas fa-check"></i> Basic equipment access</div>
                </div>
                <div class="price-options">
                    <div class="price-row">
                        <span class="label">Individual</span>
                        <div class="price-select">
                            <span class="price">Rs. 1,500</span>
                            <button class="select-btn">Select</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>