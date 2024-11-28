<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Instructor Onboarding - Bank Details</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/selfOnboarding.css">
</head>
<body>
<div class="main-content">
    <div class="container">
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

        <div class="card">
            <h1 class="text-center mb-4">Bank Details</h1>
            <form id="bankDetailsForm" action="${pageContext.request.contextPath}/selfOnboarding/step2" method="post" class="bank-form">
                <div class="form-group mb-4">
                    <label class="form-label" for="bankName">Bank Name</label>
                    <input
                            type="text"
                            id="bankName"
                            name="bankName"
                            class="form-control"
                            placeholder="Enter the name of your bank"
                            required
                    >
                </div>

                <div class="form-group mb-4">
                    <label class="form-label" for="bankBranch">Bank Branch</label>
                    <input
                            type="text"
                            id="bankBranch"
                            name="bankBranch"
                            class="form-control"
                            placeholder="Enter the branch of your bank"
                            required
                    >
                </div>

                <div class="form-group mb-4">
                    <label class="form-label" for="accNo">Account Number</label>
                    <input
                            type="number"
                            id="accNo"
                            name="accNo"
                            class="form-control"
                            placeholder="Enter your account number"
                            required
                    >
                </div>

                <div class="form-group mb-4">
                    <label class="form-label" for="accName">Account Holder's Name</label>
                    <input
                            type="text"
                            id="accName"
                            name="accName"
                            class="form-control"
                            placeholder="Enter the name on your account"
                            required
                    >
                </div>

                <div class="flex justify-between mt-4">
                    <button type="button" class="btn btn-secondary" onclick="window.location.href='/selfOnboarding/step3'">Back</button>
                    <button type="submit" class="btn btn-primary">Submit</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>