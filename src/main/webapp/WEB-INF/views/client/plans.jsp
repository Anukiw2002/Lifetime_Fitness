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
                                        <span class="label">Solo -
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
                                            <form action="${pageContext.request.contextPath}/Checkout" method="get" style="display:inline;">
                                                <input type="hidden" name="product" value="${plan.planName} - Solo - ${duration.durationValue} ${duration.durationType}" />
                                                <input type="hidden" name="subtotal" value="${duration.uniformPricing[0].price}" />
                                                <c:set var="total" value="${duration.uniformPricing[0].price + 20}" />
                                                <input type="hidden" name="total" value="${total}" />
                                                <input type="hidden" name="durationId" value="${duration.durationId}" />
                                                <button type="submit" class="select-btn">Select</button>
                                            </form>
                                        </div>
                                    </div>
                                </c:when>


                                <c:when test="${plan.pricingType eq 'category'}">
                                    <c:forEach var="pricing" items="${duration.categoryPricing}">
                                        <div class="price-row">
                                            <span class="label">${pricing.category} - ${duration.durationValue}
                                                <%
                                                    Object durationObj = pageContext.getAttribute("duration");
                                                    if (durationObj != null) {
                                                        java.lang.reflect.Method getDurationValueMethod = durationObj.getClass().getMethod("getDurationValue");
                                                        java.lang.reflect.Method getDurationTypeMethod = durationObj.getClass().getMethod("getDurationType");
                                                        int durationValue = (Integer) getDurationValueMethod.invoke(durationObj);
                                                        String durationType = (String) getDurationTypeMethod.invoke(durationObj);
                                                        out.print(" " + formatDurationType(durationValue, durationType));
                                                    }
                                                %>
                                            </span>
                                            <div class="price-select">
                                                <span class="price">Rs.
                                                    <fmt:formatNumber value="${pricing.price}" type="number" pattern="#,##,###"/>
                                                </span>
                                                <form action="${pageContext.request.contextPath}/Checkout" method="get" style="display:inline;">
                                                    <input type="hidden" name="product" value="${plan.planName} - ${pricing.category} - ${duration.durationValue} ${duration.durationType}" />
                                                    <input type="hidden" name="subtotal" value="${pricing.price}" />
                                                    <c:set var="totalCat" value="${pricing.price}" />
                                                    <input type="hidden" name="total" value="${totalCat}" />
                                                    <input type="hidden" name="durationId" value="${duration.durationId}" />
                                                    <button type="submit" class="select-btn">Select</button>
                                                </form>
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
