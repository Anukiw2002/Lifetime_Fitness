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
                <button class="delete-btn"><i class="fas fa-trash"></i></button>
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
                <button class="delete-btn"><i class="fas fa-trash"></i></button>
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
                <button class="delete-btn"><i class="fas fa-trash"></i></button>
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
                <button class="delete-btn"><i class="fas fa-trash"></i></button>
                <button class="status-btn active">Active</button>
            </div>
        </div>
    </div>
</div>

<!-- Add/Edit Plan Modal -->
<div id="planModal" class="modal">
    <div class="modal-content">
        <span class="close">&times;</span>
        <h2 id="modalTitle">Add New Plan</h2>
        <form id="planForm">
            <div class="form-group">
                <label>Plan Name:</label>
                <input type="text" name="planName" required>
            </div>
            <div class="form-group">
                <label>Time Slot:</label>
                <input type="text" name="timeSlot" required>
            </div>
            <div class="form-group">
                <label>Features:</label>
                <textarea name="features" rows="4" required></textarea>
            </div>
            <div class="form-group">
                <label>Plan Options:</label>
                <div id="planOptions">
                    <div class="option-row">
                        <input type="text" placeholder="Option Name" required>
                        <input type="number" placeholder="Price" required>
                        <button type="button" onclick="removeOption(this)">Remove</button>
                    </div>
                </div>
                <button type="button" onclick="addOption()">Add Option</button>
            </div>
            <div class="form-actions">
                <button type="submit" class="save-btn">Save Plan</button>
                <button type="button" class="cancel-btn" onclick="closeModal()">Cancel</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>