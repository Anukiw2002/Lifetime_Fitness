<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lifetime Fitness - Member Management</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/generalStyles.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/memberManagement.css">
    <script>
        function cancelMembership(id) {
            if (confirm("Are you sure you want to cancel this membership?")) {
                fetch('${pageContext.request.contextPath}/cancelMembership', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                    body: 'id=' + id
                })
                    .then(response => {
                        if (response.ok) {
                            location.reload();
                        } else {
                            alert('Error cancelling membership');
                        }
                    })
                    .catch(error => console.error('Error:', error));
            }
        }
    </script>
</head>
<body>
<jsp:include page="../common/verticalNavBar.jsp" />
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
                <c:forEach var="membership" items="${memberships}">
                    <tr>
                        <td>${membership.clientName}</td>
                        <td>${membership.planName}</td>
                        <td>
                                    <span class="status-badge ${membership.status}">
                                            ${membership.status}
                                    </span>
                        </td>
                        <td>${membership.endDate}</td>
                        <td>
                            <div class="flex">
                                <button class="action-button edit">
                                    <i class="fas fa-edit"></i>
                                </button>
                                <button class="action-button suspend">
                                    <i class="fas fa-pause"></i>
                                </button>
                                <button class="action-button cancel" onclick="cancelMembership(${membership.membershipId})">
                                    <i class="fas fa-times"></i>
                                </button>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
