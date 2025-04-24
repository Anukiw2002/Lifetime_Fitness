<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Membership Expired</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/generalStyles.css">
</head>
<body>
<div class="container text-center">
    <div class="logo-container" style="margin-bottom: var(--spacing-xl);">
        <img src="${pageContext.request.contextPath}/images/LogoWhite.png" alt="Lifetime Fitness" class="logo" style="max-width: 200px;">
    </div>

    <div class="card" style="display: inline-block;">
        <div class="card-body">
            <h1>Your Membership has Expired</h1>
            <p class="text-muted">Your subscription plan has expired. Please renew your membership to continue enjoying our services.</p>
            <div style="margin-top: var(--spacing-lg); display: flex; justify-content: center; gap: var(--spacing-md); flex-wrap: wrap;">
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/Plans">Renew</a>
                <a class="btn btn-secondary" href="${pageContext.request.contextPath}/landingPage">Exit</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
