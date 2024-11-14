<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Instructor Onboarding - Bank Details</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/selfOnboarding.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/library/button.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/library/typography.css">
</head>
<body>
<jsp:include page="../common/verticalNavBar.jsp" />
<div class="main-content">
    <div class="container">
        <div class="header">
            <div class="progress-steps">
                <div class="step completed">
                    <div class="step-number">1</div>
                    <span>Basic Information</span>
                </div>
                <div class="step-line1"></div>
                <div class="step completed">
                    <div class="step-number">2</div>
                    <span>Professional Bio</span>
                </div>
                <div class="step-line1"></div>
                <div class="step completed">
                    <div class="step-number">3</div>
                    <span>Availability and Schedule</span>
                </div>
                <div class="step-line1"></div>
                <div class="step active">
                    <div class="step-number">4</div>
                    <span>Bank Details</span>
                </div>
            </div>

            <div class="content-card">
                <h1>Bank Details</h1>
                <form id="basicInfoForm" action="${pageContext.request.contextPath}/selfOnboarding/step2" method="post">
                    <div class="form-section">
                        <div class="form-group full-width">
                           <label for="bankName" style="text-align: left;">Bank Name</label>
                            <input type="text" id="bankName" name="bankName" placeholder="Enter the name of your bank" required>
                        </div>
                    </div>

                    <div class="form-section">
                        <div class="form-group full-width">
                            <label for="bankBranch" style="text-align: left;">Bank Branch</label>
                            <input type="text" id="bankBranch" name="bankBranch" placeholder="Enter the branch of your bank" required>
                        </div>
                    </div>

                    <div class="form-section">
                        <div class="form-group full-width">
                            <label for="accNo" style="text-align: left;">Account Number</label>
                            <input type="number" id="accNo" name="accNo" placeholder="Enter your account number" required>
                        </div>
                    </div>

                    <div class="form-section">
                        <div class="form-group full-width">
                            <label for="accName" style="text-align: left;">Account Holder’s Name</label>
                            <input type="text" id="accName" name="accName" placeholder=" Enter the name on your account" required>
                        </div>
                    </div>

                    <div class="button-group">
                        <button type="button" class="back-button" onclick="window.location.href='/selfOnboarding/step3'">Back</button>
                        <button type="submit" class="next-button" >Submit</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>