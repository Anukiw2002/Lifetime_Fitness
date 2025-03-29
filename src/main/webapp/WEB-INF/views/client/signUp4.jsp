<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lifetime Fitness - Choose Your Plan</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/signUp4.css">
</head>
<body>
<%!
    public String formatDurationType(int value, String durationType) {
        if (durationType == null) return "";
        return value == 1 ? durationType.substring(0, durationType.length() - 1) : durationType;
    }
%>
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
        <c:forEach var="plan" items="${membershipPlans}">
            <div class="membership-card ${plan.status == 'INACTIVE' ? 'inactive-plan' : ''}">
                    <div class="card-header">
                        <h2 style="color: white;">
                            <i class="fas fa-star" style="color: ${plan.colour};"></i> ${plan.planName}
                        </h2>
                        <div class="timing">
                            <i class="far fa-clock"></i>
                                ${plan.startTime} to ${plan.endTime}
                        </div>
                    </div>
                    <div class="card-content">
                        <div class="price-options">
                            <c:forEach var="duration" items="${plan.durations}">
                                <c:choose>
                                    <c:when test="${plan.pricingType eq 'uniform'}">
                                        <div class="price-row">
                                            <span class="label">Individual -
                                                ${duration.durationValue}
                                                <%
                                                    Object durationObj = pageContext.getAttribute("duration");
                                                    if (durationObj != null) {
                                                        java.lang.reflect.Method getDurationValueMethod = durationObj.getClass().getMethod("getDurationValue");
                                                        java.lang.reflect.Method getDurationTypeMethod = durationObj.getClass().getMethod("getDurationType");
                                                        int durationValue = (Integer) getDurationValueMethod.invoke(durationObj);
                                                        String durationType = (String) getDurationTypeMethod.invoke(durationObj);
                                                        out.print(formatDurationType(durationValue, durationType));
                                                    }
                                                %>
                                            </span>
                                            <div class="price-select">
                                                <span class="price">Rs.
                                                    <fmt:formatNumber value="${duration.uniformPricing[0].price}" type="number" pattern="#,##,###"/>
                                                </span>
                                                <a href="/payment?planId=${plan.planId}&durationId=${duration.durationId}" class="select-btn">Select</a>
                                            </div>
                                        </div>
                                    </c:when>
                                    <c:when test="${plan.pricingType eq 'category'}">
                                        <c:forEach var="pricing" items="${duration.categoryPricing}">
                                            <div class="price-row">
                                                <span class="label">
                                                    <c:choose>
                                                        <c:when test="${pricing.category eq 'Gents'}">Gents - Annual</c:when>
                                                        <c:when test="${pricing.category eq 'Ladies'}">Ladies - Annual</c:when>
                                                        <c:when test="${pricing.category eq 'Couple'}">Couple - Annual</c:when>
                                                        <c:otherwise>${pricing.category}</c:otherwise>
                                                    </c:choose>
                                                </span>
                                                <div class="price-select">
                                                    <span class="price">Rs.
                                                        <fmt:formatNumber value="${pricing.price}" type="number" pattern="#,##,###"/>
                                                    </span>
                                                    <a href="/payment?planId=${plan.planId}&durationId=${duration.durationId}&category=${pricing.category}" class="select-btn">Select</a>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </c:when>
                                </c:choose>
                            </c:forEach>
                        </div>
                    </div>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>