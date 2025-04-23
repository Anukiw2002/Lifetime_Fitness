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

        // Searching Members by name - Fixed function
        function membershipSearch() {
            var input = document.getElementById("clientSearch").value.toLowerCase();
            var table = document.getElementById("memberTable");
            var rows = table.getElementsByClassName("memberRow");

            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                var clientName = row.cells[0].textContent.toLowerCase();

                if (clientName.indexOf(input) > -1) {
                    row.style.display = "";
                } else {
                    row.style.display = "none";
                }
            }
        }

        // Filter memberships by plan and status
        function filterMemberships() {
            var planFilter = document.getElementById("planFilter").value.toLowerCase();
            var statusFilter = document.getElementById("statusFilter").value.toLowerCase();
            var table = document.getElementById("memberTable");
            var rows = table.getElementsByClassName("memberRow");

            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                var planName = row.cells[1].textContent.toLowerCase();
                var status = row.cells[2].textContent.trim().toLowerCase();

                var matchPlan = planFilter === "" || planName === planFilter;
                var matchStatus = statusFilter === "" || status === statusFilter;

                if (matchPlan && matchStatus) {
                    row.style.display = "";
                } else {
                    row.style.display = "none";
                }
            }
        }

        // Reset all filters
        function resetFilters() {
            document.getElementById("clientSearch").value = "";
            document.getElementById("planFilter").selectedIndex = 0;
            document.getElementById("statusFilter").selectedIndex = 0;

            var table = document.getElementById("memberTable");
            var rows = table.getElementsByClassName("memberRow");
            for (var i = 0; i < rows.length; i++) {
                rows[i].style.display = "";
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
                    <input type="text" id="clientSearch" class="form-control" placeholder="Search members..." onkeyup="membershipSearch()">
                </div>
                <div class="flex gap-md">
                    <select id="planFilter" class="form-control">
                        <option value="">All Memberships</option>
                        <c:set var="plans" value="" />
                        <c:forEach var="membership" items="${memberships}">
                            <c:if test="${!plans.contains(membership.planName)}">
                                <c:set var="plans" value="${plans}${membership.planName}," />
                                <option value="${membership.planName}">${membership.planName}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                    <select id="statusFilter" class="form-control">
                        <option value="">All Statuses</option>
                        <option value="active">Active</option>
                        <option value="expired">Expired</option>
                        <option value="cancelled">Cancelled</option>
                    </select>
                    <button class="btn btn-secondary" onclick="filterMemberships()">
                        <i class="fas fa-filter"></i> Apply
                    </button>
                    <button class="btn btn-outline-secondary" onclick="resetFilters()">
                        <i class="fas fa-undo"></i> Reset
                    </button>
                </div>
            </div>
        </div>

        <!-- Member List -->
        <div class="card">
            <table class="member-table" id="memberTable">
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
                    <tr class="memberRow">
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
                                <button class="action-button edit" onClick="viewClientDetails(${membership.userId})">
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
<script>
    function viewClientDetails(userId) {
        window.location.href = "viewMember?id=" + userId;
    }
</script>
</body>
</html>