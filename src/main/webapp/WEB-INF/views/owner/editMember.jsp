<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Member</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/editMember.css">
</head>
<body>
<!-- Main Layout -->
<div class="main-content">
    <!-- Side Navbar -->
    <jsp:include page="../common/verticalNavBar.jsp" />

    <!-- Content Section -->
    <div class="container">
        <div class="card">
            <div class="page-header">
                <h2 class="text-center">Edit Member</h2>
            </div>

            <form class="form-group">
                <div class="form-group">
                    <label class="form-label" for="name">Name:</label>
                    <input type="text" id="name" name="name" value="John Doe" class="form-control" required>
                </div>

                <div class="form-group">
                    <label class="form-label" for="email">Email:</label>
                    <input type="email" id="email" name="email" value="john.doe@example.com" class="form-control" required>
                </div>

                <div class="form-group">
                    <label class="form-label" for="phone">Phone:</label>
                    <input type="tel" id="phone" name="phone" value="123-456-7890" class="form-control" required>
                </div>

                <div class="form-group">
                    <label class="form-label" for="status">Status:</label>
                    <select id="status" name="status" class="form-control" required>
                        <option value="active" selected>Active</option>
                        <option value="suspended">Suspended</option>
                        <option value="cancelled">Cancelled</option>
                    </select>
                </div>

                <div class="form-group">
                    <label class="form-label" for="plan">Membership Plan:</label>
                    <select id="plan" name="plan" class="form-control" required>
                        <option value="platinum">Platinum</option>
                        <option value="gold" selected>Gold</option>
                        <option value="silver">Silver</option>
                    </select>
                </div>

                <div class="form-actions flex justify-end gap-md">
                    <a href="#" class="btn btn-secondary">Cancel</a>
                    <button type="submit" class="btn btn-primary">Save Changes</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>