

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lifetime Fitness - Member Management</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/memberManagement.css">
</head>
<body>
<div class="container">

    <!-- Main Content -->
    <main>
        <h1 class="page-title">Member Management</h1>

        <!-- Search and Filters -->
        <div class="search-filters">
            <div class="search-container">
                <input type="text" placeholder="Search members..." class="search-input">
                <button class="search-btn"><i class="fas fa-search"></i></button>
            </div>
            <div class="filter-container">
                <select class="filter-select">
                    <option value="">All Memberships</option>
                    <option value="platinum">Platinum</option>
                    <option value="gold">Gold</option>
                    <option value="silver">Silver</option>
                </select>
                <select class="filter-select">
                    <option value="">All Statuses</option>
                    <option value="active">Active</option>
                    <option value="suspended">Suspended</option>
                    <option value="cancelled">Cancelled</option>
                </select>
                <button class="filter-btn"><i class="fas fa-filter"></i></button>
            </div>
        </div>

        <!-- Member List -->
        <div class="member-list">
            <table>
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
                    <td><span class="status active">Active</span></td>
                    <td>2023-12-31</td>
                    <td>
                        <div class="actions">
                            <button class="action-btn edit-btn"><i class="fas fa-edit"></i></button>
                            <button class="action-btn suspend-btn"><i class="fas fa-pause"></i></button>
                            <button class="action-btn cancel-btn"><i class="fas fa-times"></i></button>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>Jane Smith</td>
                    <td>Gold</td>
                    <td><span class="status active">Active</span></td>
                    <td>2024-06-30</td>
                    <td>
                        <div class="actions">
                            <button class="action-btn edit-btn"><i class="fas fa-edit"></i></button>
                            <button class="action-btn suspend-btn"><i class="fas fa-pause"></i></button>
                            <button class="action-btn cancel-btn"><i class="fas fa-times"></i></button>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>Bob Johnson</td>
                    <td>Silver</td>
                    <td><span class="status suspended">Suspended</span></td>
                    <td>2023-09-15</td>
                    <td>
                        <div class="actions">
                            <button class="action-btn edit-btn"><i class="fas fa-edit"></i></button>
                            <button class="action-btn suspend-btn"><i class="fas fa-pause"></i></button>
                            <button class="action-btn cancel-btn"><i class="fas fa-times"></i></button>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>Sara Lee</td>
                    <td>Platinum</td>
                    <td><span class="status cancelled">Cancelled</span></td>
                    <td>2023-03-01</td>
                    <td>
                        <div class="actions">
                            <button class="action-btn edit-btn"><i class="fas fa-edit"></i></button>
                            <button class="action-btn suspend-btn"><i class="fas fa-pause"></i></button>
                            <button class="action-btn cancel-btn"><i class="fas fa-times"></i></button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </main>
</div>
</body>
</html>