<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Client Profile - Lifetime Fitness</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/addClient.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/library/button.css">
</head>
<body>
<jsp:include page="../common/verticalNavBar.jsp" />
<div class="main-content">
    <div class="container">
        <div class="card">
            <div class="progress-steps">
                <div class="step active">
                    <div class="step-number">1</div>
                    <span>Basic details</span>
                </div>
                <div class="step-line2"></div>
                <div class="step">
                    <div class="step-number">2</div>
                    <span>Medical Records</span>
                </div>
                <div class="step-line2"></div>
                <div class="step">
                    <div class="step-number">3</div>
                    <span>Membership plan</span>
                </div>
            </div>

            <form id="clientProfileForm" class="form">
                <!-- Step 1: Basic Details -->
                <div class="form-step active" id="step1">
                    <h2 class="text-center">Basic Details</h2>
                    <div class="form-group">
                        <label class="form-label" for="firstName">First Name*</label>
                        <input type="text" id="firstName" name="firstName" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label class="form-label" for="lastName">Last Name*</label>
                        <input type="text" id="lastName" name="lastName" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label class="form-label" for="dob">Date of Birth*</label>
                        <input type="date" id="dob" name="dob" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label class="form-label" for="phone">Phone Number*</label>
                        <input type="tel" id="phone" name="phone" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label class="form-label" for="email">Email*</label>
                        <input type="email" id="email" name="email" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label class="form-label" for="address">Address*</label>
                        <textarea id="address" name="address" class="form-control" required></textarea>
                    </div>
                    <div class="form-group">
                        <label class="form-label" for="emergencyContact">Emergency Contact Number*</label>
                        <input type="tel" id="emergencyContact" name="emergencyContact" class="form-control" required>
                    </div>
                </div>

                <!-- Step 2: Medical Records -->
                <div class="form-step" id="step2">
                    <h2 class="text-center">Medical Records</h2>
                    <div class="form-group">
                        <label class="form-label">Does the client have any medical conditions?</label>
                        <div class="checkbox-group">
                            <label><input type="checkbox" name="conditions" value="heart"> Heart Disease</label>
                            <label><input type="checkbox" name="conditions" value="diabetes"> Diabetes</label>
                            <label><input type="checkbox" name="conditions" value="asthma"> Asthma</label>
                            <label><input type="checkbox" name="conditions" value="hypertension"> Hypertension</label>
                            <label><input type="checkbox" name="conditions" value="other"> Other</label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-label" for="medications">Current Medications</label>
                        <textarea id="medications" name="medications" class="form-control"></textarea>
                    </div>
                    <div class="form-group">
                        <label class="form-label" for="allergies">Allergies</label>
                        <textarea id="allergies" name="allergies" class="form-control"></textarea>
                    </div>
                    <div class="form-group">
                        <label class="form-label" for="injuries">Previous Injuries</label>
                        <textarea id="injuries" name="injuries" class="form-control"></textarea>
                    </div>
                </div>

                <!-- Step 3: Membership Plan -->
                <div class="form-step" id="step3">
                    <h2 class="text-center">Membership Plan</h2>
                    <div class="form-group">
                        <label class="form-label">Select Membership Duration*</label>
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
                        <label class="form-label" for="startDate">Membership Start Date*</label>
                        <input type="date" id="startDate" name="startDate" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label class="form-label" for="paymentMethod">Payment Method*</label>
                        <select id="paymentMethod" name="paymentMethod" class="form-control" required>
                            <option value="">Select Payment Method</option>
                            <option value="cash">Cash</option>
                            <option value="card">Credit/Debit Card</option>
                            <option value="bank">Bank Transfer</option>
                        </select>
                    </div>
                </div>

                <div class="flex justify-end gap-md mt-4">
                    <button type="button" id="prevBtn" class="btn btn-secondary" style="display: none;">Previous</button>
                    <button type="button" id="nextBtn" class="btn btn-primary">Next</button>
                    <button type="submit" id="submitBtn" class="btn btn-success" style="display: none;">Create Profile</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>