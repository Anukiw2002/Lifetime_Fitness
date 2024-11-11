<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Member</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/editMember.css">
</head>
<body>
<div class="container">
    <h1 class="page-title">Edit Member</h1>

    <form class="edit-form">
        <div class="form-group">
            <label for="name">Name:</label>
            <input type="text" id="name" name="name" value="John Doe" required>
        </div>

        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" value="john.doe@example.com" required>
        </div>

        <div class="form-group">
            <label for="phone">Phone:</label>
            <input type="tel" id="phone" name="phone" value="123-456-7890" required>
        </div>

        <div class="form-group">
            <label for="status">Status:</label>
            <select id="status" name="status" required>
                <option value="active" selected>Active</option>
                <option value="suspended">Suspended</option>
                <option value="cancelled">Cancelled</option>
            </select>
        </div>

        <div class="form-group">
            <label for="plan">Membership Plan:</label>
            <select id="plan" name="plan" required>
                <option value="platinum">Platinum</option>
                <option value="gold" selected>Gold</option>
                <option value="silver">Silver</option>
            </select>
        </div>

        <div class="form-actions">
            <button type="submit" class="submit-btn">Save Changes</button>
            <a href="#" class="cancel-btn">Cancel</a>
        </div>
    </form>
</div>
</body>
</html>