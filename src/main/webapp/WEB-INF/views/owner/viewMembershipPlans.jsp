<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Membership Plans Management - Lifetime Fitness</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/viewMembershipPlans.css">
</head>
<body>
<div style="display: none;">
    Debug Info:
    membershipPlans attribute exists: ${not empty membershipPlans}
    Number of plans: ${membershipPlans.size()}
    <c:forEach var="plan" items="${membershipPlans}">
        Plan: ${plan.planName}, ID: ${plan.planId}<br/>
    </c:forEach>
</div>
<div class="container">
    <div class="header">
        <h1>Membership Plans Management</h1>
        <button class="add-plan-btn" onclick="window.location.href='${pageContext.request.contextPath}/membership/add'">
            <i class="fas fa-plus"></i> Add New Plan
        </button>
    </div>

    <div class="plans-grid">
        <c:forEach var="plan" items="${membershipPlans}">
            <div class="plan-card">
                <div class="plan-header">
                    <h2>${plan.planName}</h2>
                    <p class="time-slot">${plan.startTime} to ${plan.endTime}</p>
                </div>
                <div class="plan-options">
                    <c:forEach var="duration" items="${plan.durations}">
                        <c:choose>
                            <%-- Handle Uniform Pricing --%>
                            <c:when test="${plan.pricingType eq 'uniform'}">
                                <div class="option">
                                    <span>
                                        ${duration.durationValue}
                                        <c:choose>
                                            <c:when test="${duration.durationValue eq 1}">${duration.durationType}</c:when>
                                            <c:otherwise>${duration.durationType}s</c:otherwise>
                                        </c:choose>
                                    </span>
                                    <span class="price">Rs. ${duration.uniformPricing[0].price}</span>
                                </div>
                            </c:when>

                            <%-- Handle Category Pricing --%>
                            <c:when test="${plan.pricingType eq 'category'}">
                                <c:forEach var="pricing" items="${duration.categoryPricing}">
                                    <div class="option">
                                        <span>${pricing.category}</span>
                                        <span class="price">Rs. ${pricing.price}</span>
                                    </div>
                                </c:forEach>
                            </c:when>
                        </c:choose>
                    </c:forEach>
                </div>
                <div class="plan-actions">
                    <button class="edit-btn" >
                        <i class="fas fa-edit"></i>
                    </button>
                    <button class="delete-btn"
                            data-plan-name="${plan.planName}"
                            data-plan-id="${plan.planId}"
                            onclick="showConfirmationModal('${plan.planName}', ${plan.planId})">
                        <i class="fas fa-trash"></i>
                    </button>
                    <button class="status-btn active">Active</button>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<div id="confirm-modal" class="modal">
    <div class="modal-content">
        <h2>Are you sure you want to delete this plan?</h2>
        <p>This plan will be archived and can be restored later if needed.</p>
        <div class="modal-buttons">
            <button type="button" class="confirm-btn">Yes, Delete</button>
            <button type="button" class="cancel-btn">Cancel</button>
        </div>
    </div>
</div>

<script>
    const modal = document.getElementById("confirm-modal");
    const confirmBtn = document.querySelector(".confirm-btn");
    const cancelBtn = document.querySelector(".cancel-btn");
    let planToDelete = null;

    function showConfirmationModal(planName, planId) {
        planToDelete = planId;
        const modalContent = document.querySelector(".modal-content");
        modalContent.querySelector("h2").textContent = `Are you sure you want to delete the ${planName} plan?`;
        modal.style.display = "block";
    }

    confirmBtn.addEventListener("click", async () => {
        if (planToDelete) {
            try {
                const response = await fetch(`${pageContext.request.contextPath}/membership/delete`, {
                    method: 'POST'
                });

                if (response.ok) {
                    window.location.reload();
                } else {
                    alert('Failed to delete the plan. Please try again.');
                }
            } catch (error) {
                console.error('Error:', error);
                alert('An error occurred while deleting the plan.');
            }
        }
        modal.style.display = "none";
    });

    cancelBtn.addEventListener("click", () => {
        modal.style.display = "none";
        planToDelete = null;
    });

    window.addEventListener("click", (event) => {
        if (event.target == modal) {
            modal.style.display = "none";
            planToDelete = null;
        }
    });
</script>
</body>
</html>