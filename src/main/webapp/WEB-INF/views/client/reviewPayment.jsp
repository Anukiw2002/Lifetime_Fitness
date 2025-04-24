<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Review Payment</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
</head>
<body>
<div class="container text-center">
    <div class="logo-container" style="margin-bottom: var(--spacing-xl); padding-top: var(--spacing-lg);">
        <img src="${pageContext.request.contextPath}/images/LogoWhite.png" alt="Lifetime Fitness" class="logo" style="max-height: 80px;">
    </div>

    <h1>Review Before Payment</h1>

    <div class="card" style="max-width: 600px; margin: auto;">
        <form method="post">
            <input type="hidden" name="paymentId" value="${param.paymentId}" />
            <input type="hidden" name="PayerID" value="${param.PayerID}" />
            <input type="hidden" name="durationId" value="${param.durationId}" />

            <div class="card-body">
                <h3>Transaction Details</h3>
                <div class="form-group">
                    <label class="form-label">Description:</label>
                    <div class="form-control">${transaction.description}</div>
                </div>
                <div class="form-group">
                    <label class="form-label">Subtotal:</label>
                    <div class="form-control">${transaction.amount.details.subtotal} LKR</div>
                </div>
                <div class="form-group">
                    <label class="form-label"><strong>Total:</strong></label>
                    <div class="form-control"><strong>${transaction.amount.total} LKR</strong></div>
                </div>

                <h3 style="margin-top: var(--spacing-xl);">Payer Information</h3>
                <div class="form-group">
                    <label class="form-label">First Name:</label>
                    <div class="form-control">${payer.firstName}</div>
                </div>
                <div class="form-group">
                    <label class="form-label">Last Name:</label>
                    <div class="form-control">${payer.lastName}</div>
                </div>
                <div class="form-group">
                    <label class="form-label">Email:</label>
                    <div class="form-control">${payer.email}</div>
                </div>
            </div>

            <div class="card-footer">
                <button type="submit" formaction="ExecutePayment" class="btn btn-primary">Pay Now</button>
                <button type="submit" formaction="CancelPayment" class="btn btn-secondary" style="margin-left: var(--spacing-sm);">Cancel</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>
