<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lifetime Fitness - Member Management</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/generalStyles.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/memberManagement.css">
</head>
<body>
<jsp:include page="instructorVerticalNavbar.jsp" />
<div class="main-content">
    <div class="container">
        <h1 class="text-center mb-4">Member Management</h1>

        <!-- Search and Filters -->
        <div class="search-section">
            <div class="flex justify-between items-center gap-lg">
                <div class="form-group mb-0">
                    <input type="text" class="form-control" placeholder="Search members...">
                </div>
                <div class="flex gap-md">
                    <select class="form-control">
                        <option value="">All Memberships</option>
                        <option value="platinum">Platinum</option>
                        <option value="gold">Gold</option>
                        <option value="silver">Silver</option>
                    </select>
                    <select class="form-control">
                        <option value="">All Statuses</option>
                        <option value="active">Active</option>
                        <option value="suspended">Suspended</option>
                        <option value="cancelled">Cancelled</option>
                    </select>
                    <button class="btn btn-secondary">
                        <i class="fas fa-filter"></i>
                    </button>
                </div>
            </div>
        </div>

        <!-- Member List -->
        <div class="card">
            <table class="member-table">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Membership</th>
                    <th>Status</th>
                    <th>Renewal Date</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>John Doe</td>
                    <td>Platinum</td>
                    <td><span class="status-badge active">Active</span></td>
                    <td>2023-12-31</td>
                    <td>
                        <div class="flex">
                            <button class="action-button edit"><i class="fas fa-edit"></i></button>
                            <button class="action-button suspend"><i class="fas fa-pause"></i></button>
                            <button class="action-button cancel"><i class="fas fa-times"></i></button>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>Jane Smith</td>
                    <td>Gold</td>
                    <td><span class="status-badge active">Active</span></td>
                    <td>2024-06-30</td>
                    <td>
                        <div class="flex">
                            <button class="action-button edit"><i class="fas fa-edit"></i></button>
                            <button class="action-button suspend"><i class="fas fa-pause"></i></button>
                            <button class="action-button cancel"><i class="fas fa-times"></i></button>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>Bob Johnson</td>
                    <td>Silver</td>
                    <td><span class="status-badge suspended">Suspended</span></td>
                    <td>2023-09-15</td>
                    <td>
                        <div class="flex">
                            <button class="action-button edit"><i class="fas fa-edit"></i></button>
                            <button class="action-button suspend"><i class="fas fa-pause"></i></button>
                            <button class="action-button cancel"><i class="fas fa-times"></i></button>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>Sara Lee</td>
                    <td>Platinum</td>
                    <td><span class="status-badge cancelled">Cancelled</span></td>
                    <td>2023-03-01</td>
                    <td>
                        <div class="flex">
                            <button class="action-button edit"><i class="fas fa-edit"></i></button>
                            <button class="action-button suspend"><i class="fas fa-pause"></i></button>
                            <button class="action-button cancel"><i class="fas fa-times"></i></button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>