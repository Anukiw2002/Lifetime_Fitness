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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
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
        <h1 style="color: white;">Membership Plans Management</h1>
        <button class="btn btn-primary" onclick="window.location.href='${pageContext.request.contextPath}/membership/add'">
            <i class="fas fa-plus"></i> Add New Plan
        </button>
    </div>

    <div class="plans-grid">
        <c:forEach var="plan" items="${membershipPlans}">
            <div class="plan-card ${plan.status == 'INACTIVE' ? 'inactive-plan' : ''}">
                <div class="plan-header">
                    <h2>${plan.planName}</h2>
                    <p class="time-slot">${plan.startTime} to ${plan.endTime}</p>
                </div>
                <div class="plan-options">
                    <c:forEach var="duration" items="${plan.durations}">
                        <c:choose>
                            <c:when test="${plan.pricingType eq 'uniform'}">
                                <div class="option">
        <span style="color: white;">Individual -
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
                                    <span class="price" style="color: white;">Rs.
            <fmt:formatNumber value="${duration.uniformPricing[0].price}" type="number" pattern="#,##,###"/>
        </span>
                                </div>
                            </c:when>
                            <c:when test="${plan.pricingType eq 'category'}">
                                <c:forEach var="pricing" items="${duration.categoryPricing}">
                                    <div class="option">
            <span style="color: white;">
                <c:choose>
                    <c:when test="${pricing.category eq 'Male'}">Gents - Annual</c:when>
                    <c:when test="${pricing.category eq 'Female'}">Ladies - Annual</c:when>
                    <c:when test="${pricing.category eq 'Couple'}">Couples - Annual</c:when>
                    <c:otherwise>${pricing.category} - Annual</c:otherwise>
                </c:choose>
            </span>
                                        <span class="price" style="color: white;">Rs.
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
                    <button
                            class="status-btn ${plan.status eq 'INACTIVE' ? 'inactive' : 'active'}"
                            onclick="toggleStatus(${plan.planId}, '${plan.status}', this, event)"
                            data-plan-id="${plan.planId}"
                            data-current-status="${plan.status}">
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
        <p>This action cannot be undone. Are you sure you want to proceed?</p>
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

                const data = await response.json();

                if (response.ok && data.status === 'success') {
                    window.location.reload();
                } else {
                    // Show more detailed error message
                    alert('Error: ' + (data.message || 'Failed to delete the plan. Please try again.'));
                    console.error('Delete plan error:', data);
                }
            } catch (error) {
                console.error('Delete plan error:', error);
                alert('An error occurred while deleting the plan: ' + error.message);
            }
            closeModal();
        }
    }

    // Close modal when clicking outside
    window.onclick = function(event) {
        if (event.target === modal) {
            closeModal();
        }
    };

    async function toggleStatus(planId, currentStatus, buttonElement, event) {
        // Prevent any default behavior
        if (event) {
            event.preventDefault();
            event.stopPropagation();
        }

        // Prevent double-clicks
        if (buttonElement.disabled) {
            return;
        }

        try {
            // Disable the button while processing
            buttonElement.disabled = true;

            console.log(`Attempting to toggle plan ${planId} status`);

            const response = await fetch(`${pageContext.request.contextPath}/membership/updateStatus`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    planId: planId
                })
            });

            const data = await response.json();
            console.log('Server response:', data);

            if (response.ok && data.status === 'success') {
                // Get the new status from the response
                const newStatus = data.newStatus;

                // Get the plan card element
                const planCard = buttonElement.closest('.plan-card');

                // Update button text
                buttonElement.textContent = newStatus === 'ACTIVE' ? 'Active' : 'Inactive';

                // Update button classes
                buttonElement.classList.remove('active', 'inactive');
                buttonElement.classList.add(newStatus.toLowerCase());

                // Update the plan card
                if (newStatus === 'INACTIVE') {
                    planCard.classList.add('inactive-plan');
                } else {
                    planCard.classList.remove('inactive-plan');
                }

                // Update the button's onclick attribute
                buttonElement.setAttribute('onclick',
                    `toggleStatus(${planId}, '${newStatus}', this, event)`);

                console.log('UI updated successfully');
            } else {
                throw new Error(data.message || 'Failed to update status');
            }
        } catch (error) {
            console.error('Error in toggleStatus:', error);
            alert('Failed to update status: ' + error.message);
        } finally {
            // Re-enable the button
            buttonElement.disabled = false;
        }
    }

    document.addEventListener('DOMContentLoaded', function() {
        const statusButtons = document.querySelectorAll('.status-btn');
        statusButtons.forEach(button => {
            button.addEventListener('click', function(e) {
                e.preventDefault();
                const planId = this.getAttribute('data-plan-id');
                const currentStatus = this.getAttribute('data-current-status');
                toggleStatus(planId, currentStatus, this);
            });
        });
    });
</script>
</div>
</body>
</html>