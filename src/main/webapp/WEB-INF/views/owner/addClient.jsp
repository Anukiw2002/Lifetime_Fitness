<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Client Profile - Lifetime Fitness</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/addClient.css">
</head>
<body>
<jsp:include page="../common/verticalNavBar.jsp" />
<div class="main-content">
<div class="container">
    <div class="progress-bar">
        <div class="progress" id="progress"></div>
        <div class="progress-step active" data-title="Basic Details">1</div>
        <div class="progress-step" data-title="Medical Records">2</div>
        <div class="progress-step" data-title="Membership Plan">3</div>
    </div>

    <form id="clientProfileForm">
        <!-- Step 1: Basic Details -->
        <div class="form-step active" id="step1">
            <h2>Basic Details</h2>
            <div class="form-group">
                <label for="firstName">First Name*</label>
                <input type="text" id="firstName" name="firstName" required>
            </div>
            <div class="form-group">
                <label for="lastName">Last Name*</label>
                <input type="text" id="lastName" name="lastName" required>
            </div>
            <div class="form-group">
                <label for="dob">Date of Birth*</label>
                <input type="date" id="dob" name="dob" required>
            </div>
            <div class="form-group">
                <label for="phone">Phone Number*</label>
                <input type="tel" id="phone" name="phone" required>
            </div>
            <div class="form-group">
                <label for="email">Email*</label>
                <input type="email" id="email" name="email" required>
            </div>
            <div class="form-group">
                <label for="address">Address*</label>
                <textarea id="address" name="address" required></textarea>
            </div>
            <div class="form-group">
                <label for="emergencyContact">Emergency Contact Number*</label>
                <input type="tel" id="emergencyContact" name="emergencyContact" required>
            </div>
        </div>

        <!-- Step 2: Medical Records -->
        <div class="form-step" id="step2">
            <h2>Medical Records</h2>
            <div class="form-group">
                <label>Does the client have any medical conditions?</label>
                <div class="checkbox-group">
                    <label><input type="checkbox" name="conditions" value="heart"> Heart Disease</label>
                    <label><input type="checkbox" name="conditions" value="diabetes"> Diabetes</label>
                    <label><input type="checkbox" name="conditions" value="asthma"> Asthma</label>
                    <label><input type="checkbox" name="conditions" value="hypertension"> Hypertension</label>
                    <label><input type="checkbox" name="conditions" value="other"> Other</label>
                </div>
            </div>
            <div class="form-group">
                <label for="medications">Current Medications</label>
                <textarea id="medications" name="medications"></textarea>
            </div>
            <div class="form-group">
                <label for="allergies">Allergies</label>
                <textarea id="allergies" name="allergies"></textarea>
            </div>
            <div class="form-group">
                <label for="injuries">Previous Injuries</label>
                <textarea id="injuries" name="injuries"></textarea>
            </div>
        </div>

        <!-- Step 3: Membership Plan -->
        <div class="form-step" id="step3">
            <h2>Membership Plan</h2>
            <div class="form-group">
                <label>Select Membership Duration*</label>
                <div class="plan-options">
                    <div class="plan-card" data-plan="1">
                        <h3>1 Month</h3>
                        <p class="price">Rs. 15,000</p>
                        <ul>
                            <li>Full Gym Access</li>
                            <li>Basic Training</li>
                            <li>Locker Access</li>
                        </ul>
                    </div>
                    <div class="plan-card" data-plan="3">
                        <h3>3 Months</h3>
                        <p class="price">Rs. 40,000</p>
                        <ul>
                            <li>Full Gym Access</li>
                            <li>Personal Trainer</li>
                            <li>Locker Access</li>
                            <li>Nutrition Guide</li>
                        </ul>
                    </div>
                    <div class="plan-card" data-plan="6">
                        <h3>6 Months</h3>
                        <p class="price">Rs. 75,000</p>
                        <ul>
                            <li>Full Gym Access</li>
                            <li>Personal Trainer</li>
                            <li>Locker Access</li>
                            <li>Nutrition Guide</li>
                            <li>Free Supplements</li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label for="startDate">Membership Start Date*</label>
                <input type="date" id="startDate" name="startDate" required>
            </div>
            <div class="form-group">
                <label for="paymentMethod">Payment Method*</label>
                <select id="paymentMethod" name="paymentMethod" required>
                    <option value="">Select Payment Method</option>
                    <option value="cash">Cash</option>
                    <option value="card">Credit/Debit Card</option>
                    <option value="bank">Bank Transfer</option>
                </select>
            </div>
        </div>

        <div class="form-navigation">
            <button type="button" id="prevBtn" class="btn secondary" style="display: none;">Previous</button>
            <button type="button" id="nextBtn" class="btn primary">Next</button>
            <button type="submit" id="submitBtn" class="btn primary" style="display: none;">Create Profile</button>
        </div>
    </form>
</div>
</body>
</html>