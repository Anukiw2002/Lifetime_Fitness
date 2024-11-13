<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Membership Plans Management - Lifetime Fitness</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/viewMembershipPlans.css">
</head>
<body>
<div class="container">
    <div class="header">
        <h1>Membership Plans Management</h1>
        <button class="add-plan-btn" onclick="window.location.href='${pageContext.request.contextPath}/MembershipPlan?action=add'">
            <i class="fas fa-plus"></i> Add New Plan
        </button>
    </div>

    <div class="plans-grid">
        <!-- Platinum Plan -->
        <div class="plan-card">
            <div class="plan-header">
                <h2>Platinum Membership</h2>
                <p class="time-slot">4:00 am to 12:00 Midnight</p>
            </div>
            <div class="plan-features">
                <ul>
                    <li><i class="fas fa-check"></i> Access to all premium equipment</li>
                    <li><i class="fas fa-check"></i> Personal trainer sessions</li>
                    <li><i class="fas fa-check"></i> Spa & Sauna access</li>
                    <li><i class="fas fa-check"></i> Group classes included</li>
                </ul>
            </div>
            <div class="plan-options">
                <div class="option">
                    <span>Gents</span>
                    <span class="price">Rs. 65,000</span>
                    <button class="select-btn">Select</button>
                </div>
                <div class="option">
                    <span>Ladies</span>
                    <span class="price">Rs. 65,000</span>
                    <button class="select-btn">Select</button>
                </div>
                <div class="option">
                    <span>Couple</span>
                    <span class="price">Rs. 85,000</span>
                    <button class="select-btn">Select</button>
                </div>
            </div>
            <div class="plan-actions">
                <button class="edit-btn"><i class="fas fa-edit"></i></button>
                <button class="delete-btn" data-plan-name="Platinum Membership" data-plan-type="Platinum" onclick="showConfirmationModal('Platinum Membership', 'Platinum')"><i class="fas fa-trash"></i></button>
                <button class="status-btn active">Active</button>
            </div>
        </div>

        <!-- Gold Plan -->
        <div class="plan-card">
            <div class="plan-header">
                <h2>Gold Membership</h2>
                <p class="time-slot">4:00 am to 4:30 pm</p>
            </div>
            <div class="plan-features">
                <ul>
                    <li><i class="fas fa-check"></i> Access to all equipment</li>
                    <li><i class="fas fa-check"></i> 2 Personal trainer sessions</li>
                    <li><i class="fas fa-check"></i> Group classes included</li>
                </ul>
            </div>
            <div class="plan-options">
                <div class="option">
                    <span>Gents</span>
                    <span class="price">Rs. 48,000</span>
                    <button class="select-btn">Select</button>
                </div>
                <div class="option">
                    <span>Ladies</span>
                    <span class="price">Rs. 48,000</span>
                    <button class="select-btn">Select</button>
                </div>
            </div>
            <div class="plan-actions">
                <button class="edit-btn"><i class="fas fa-edit"></i></button>
                <button class="delete-btn" data-plan-name="Gold Membership" data-plan-type="Gold" onclick="showConfirmationModal('Gold Membership', 'Gold')"><i class="fas fa-trash"></i></button>
                <button class="status-btn active">Active</button>
            </div>
        </div>

        <!-- Silver Plan -->
        <div class="plan-card">
            <div class="plan-header">
                <h2>Silver Membership</h2>
                <p class="time-slot">4:00 am to 12:00 Midnight</p>
            </div>
            <div class="plan-features">
                <ul>
                    <li><i class="fas fa-check"></i> Basic equipment access</li>
                    <li><i class="fas fa-check"></i> 1 Personal trainer session</li>
                    <li><i class="fas fa-check"></i> Pay-per-class option</li>
                </ul>
            </div>
            <div class="plan-options">
                <div class="option">
                    <span>6 Months</span>
                    <span class="price">Rs. 45,000</span>
                    <button class="select-btn">Select</button>
                </div>
                <div class="option">
                    <span>3 Months</span>
                    <span class="price">Rs. 35,000</span>
                    <button class="select-btn">Select</button>
                </div>
                <div class="option">
                    <span>1 Month</span>
                    <span class="price">Rs. 15,000</span>
                    <button class="select-btn">Select</button>
                </div>
            </div>
            <div class="plan-actions">
                <button class="edit-btn"><i class="fas fa-edit"></i></button>
                <button class="delete-btn" data-plan-name="Silver Membership" data-plan-type="Silver" onclick="showConfirmationModal('Silver Membership', 'Silver')"><i class="fas fa-trash"></i></button>
                <button class="status-btn active">Active</button>
            </div>
        </div>

        <!-- Day Pass -->
        <div class="plan-card">
            <div class="plan-header">
                <h2>Day Pass</h2>
                <p class="time-slot">4:00 am to 12:00 Midnight</p>
            </div>
            <div class="plan-features">
                <ul>
                    <li><i class="fas fa-check"></i> Single day access to facilities</li>
                    <li><i class="fas fa-check"></i> Basic equipment access</li>
                </ul>
            </div>
            <div class="plan-options">
                <div class="option">
                    <span>Individual</span>
                    <span class="price">Rs. 1,500</span>
                    <button class="select-btn">Select</button>
                </div>
            </div>
            <div class="plan-actions">
                <button class="edit-btn"><i class="fas fa-edit"></i></button>
                <button class="delete-btn" data-plan-name="Day Pass" data-plan-type="Day Pass" onclick="showConfirmationModal('Day Pass', 'Day Pass')"><i class="fas fa-trash"></i></button>
                <button class="status-btn active">Active</button>
            </div>
        </div>
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
    // Get the modal and its elements
    const modal = document.getElementById("confirm-modal");
    const confirmBtn = document.querySelector(".confirm-btn");
    const cancelBtn = document.querySelector(".cancel-btn");
    const deleteBtns = document.querySelectorAll(".delete-btn");

    // Add click event listener to the delete buttons
    deleteBtns.forEach((btn) => {
        btn.addEventListener("click", (event) => {
            const planName = event.target.dataset.planName;
            const planType = event.target.dataset.planType;
            showConfirmationModal(planName, planType);
        });
    });

    // Add click event listener to the confirm and cancel buttons
    confirmBtn.addEventListener("click", () => {
        // Perform soft delete logic here
        // e.g., send a request to the server to archive the plan
        modal.style.display = "none";
    });

    cancelBtn.addEventListener("click", () => {
        modal.style.display = "none";
    });

    // Close the modal when clicking outside of it
    window.addEventListener("click", (event) => {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    });

    function showConfirmationModal(planName, planType) {
        const modalContent = document.querySelector(".modal-content");
        modalContent.querySelector("h2").textContent = `Are you sure you want to delete the ${planName} plan?`;
        modal.style.display = "block";
    }
</script>
</body>
</html>