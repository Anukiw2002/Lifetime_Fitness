<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Membership Plans Management - Lifetime Fitness</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/viewMembershipPlans.css">
</head>
<body>
<%!
    public String formatDurationType(int value, String durationType) {
        if (durationType == null) return "";
        return value == 1 ? durationType.substring(0, durationType.length() - 1) : durationType;
    }
%>
<jsp:include page="../common/verticalNavBar.jsp" />
<div class="main-content">
<div class="container">
    <div class="header">
        <h1>Membership Plans Management</h1>
        <button class="add-plan-btn" onclick="window.location.href='${pageContext.request.contextPath}/membership/add'">
            <i class="fas fa-plus"></i> Add New Plan
        </button>
    </div>

    <div class="plans-grid">
        <c:forEach var="plan" items="${membershipPlans}">
            <div class="plan-card ${plan.status eq 'INACTIVE' ? 'inactive-plan' : ''}">
                <div class="plan-header">
                    <h2>${plan.planName}</h2>
                    <p class="time-slot">${plan.startTime} to ${plan.endTime}</p>
                </div>
                <div class="plan-options">
                    <c:forEach var="duration" items="${plan.durations}">
                        <c:choose>
                            <c:when test="${plan.pricingType eq 'uniform'}">
                                <div class="option">
                                    <span>Individual -
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
                                    <span class="price">Rs.
                                        <fmt:formatNumber value="${duration.uniformPricing[0].price}" type="number" pattern="#,##,###"/>
                                    </span>
                                </div>
                            </c:when>
                            <c:when test="${plan.pricingType eq 'category'}">
                                <c:forEach var="pricing" items="${duration.categoryPricing}">
                                    <div class="option">
                                        <span>
                                            <c:choose>
                                                <c:when test="${pricing.category eq 'Male'}">Gents - Annual</c:when>
                                                <c:when test="${pricing.category eq 'Female'}">Ladies - Annual</c:when>
                                                <c:when test="${pricing.category eq 'Couple'}">Couples - Annual</c:when>
                                                <c:otherwise>${pricing.category} - Annual</c:otherwise>
                                            </c:choose>
                                        </span>
                                        <span class="price">Rs.
                                            <fmt:formatNumber value="${pricing.price}" type="number" pattern="#,##,###"/>
                                        </span>
                                    </div>
                                </c:forEach>
                            </c:when>
                        </c:choose>
                    </c:forEach>
                </div>
                <div class="plan-actions">
                    <button class="edit-btn" onclick="window.location.href='${pageContext.request.contextPath}/membership/update?planId=${plan.planId}'">
                        <i class="fas fa-edit"></i>
                    </button>
                    <button class="delete-btn" onclick="confirmDelete('${plan.planName}', ${plan.planId})">
                        <i class="fas fa-trash"></i>
                    </button>
                    <button class="status-btn ${plan.status eq 'INACTIVE' ? 'inactive' : 'active'}"
                            onclick="toggleStatus(${plan.planId}, '${plan.status}')">
                            ${plan.status eq 'INACTIVE' ? 'Inactive' : 'Active'}
                    </button>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<!-- Dynamic Modal -->
<div id="deleteModal" class="modal">
    <div class="modal-content">
        <h2 id="modalTitle"></h2>
        <p>This plan will be archived and can be restored later if needed.</p>
        <div class="modal-buttons">
            <button type="button" onclick="deletePlan()" class="confirm-btn">Yes, Delete</button>
            <button type="button" onclick="closeModal()" class="cancel-btn">Cancel</button>
        </div>
    </div>
</div>

<script>
    let currentPlanId = null;
    const modal = document.getElementById("deleteModal");

    function confirmDelete(planName, planId) {
        currentPlanId = planId;
        document.getElementById("modalTitle").textContent = `Are you sure you want to delete the ${planName} plan?`;
        modal.style.display = "block";
    }

    function closeModal() {
        modal.style.display = "none";
        currentPlanId = null;
    }

    async function deletePlan() {
        if (currentPlanId) {
            try {
                const response = await fetch(`${pageContext.request.contextPath}/membership/delete`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({ planId: currentPlanId })
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
        closeModal();
    }

    // Close modal when clicking outside
    window.onclick = function(event) {
        if (event.target === modal) {
            closeModal();
        }
    };

    async function toggleStatus(planId, currentStatus) {
        const newStatus = currentStatus === 'ACTIVE' ? 'INACTIVE' : 'ACTIVE';
        try {
            const response = await fetch('${pageContext.request.contextPath}/membership/view', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    planId: planId,
                    status: newStatus
                })
            });

            if (response.ok) {
                window.location.reload(); // Reload the same page to show updated status
            } else {
                alert('Failed to update plan status. Please try again.');
            }
        } catch (error) {
            console.error('Error:', error);
            alert('An error occurred while updating the plan status.');
        }
    }
</script>
</div>
</body>
</html>