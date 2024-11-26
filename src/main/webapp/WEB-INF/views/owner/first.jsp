<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Total Reports</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/generalStyles.css">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/first.css">
  <script src="<%= request.getContextPath() %>/js/first.js"></script>
</head>
<body>
<jsp:include page="../common/verticalNavBar.jsp" />
<div class="main-content">
  <div class="container">
    <h1 class="text-center mb-4">Total Reports</h1>

    <!-- Search Section -->
    <div class="card mb-4">
      <div class="search-container">
        <div class="search-wrapper">
          <input type="text"
                 id="search-email-input"
                 class="form-control search-input"
                 placeholder="Search by email">
          <button class="btn btn-primary" onclick="searchEmail()">Search</button>
        </div>
      </div>
    </div>

    <!-- Reports Table -->
    <div class="table-container">
      <table id="reports-table">
        <thead>
        <tr>
          <th>Email</th>
          <th class="actions-header">Actions</th>  <!-- Add this class -->
        </tr>
        </thead>
        <tbody>
        <c:forEach var="email" items="${approvedEmails}">
          <tr>
            <td>${email}</td>
            <td>
              <div class="action-buttons">
                <form action="<%= request.getContextPath() %>/viewReport" method="get">
                  <input type="hidden" name="email" value="${email}">
                  <button type="submit" class="btn btn-secondary">View</button>
                </form>
                <form action="<%= request.getContextPath() %>/updateReport" method="get">
                  <input type="hidden" name="email" value="${email}">
                  <button type="submit" class="btn btn-primary">Update</button>
                </form>
                <form action="<%= request.getContextPath() %>/deleteReport" method="post">
                  <input type="hidden" name="email" value="${email}">
                  <button type="submit"
                          class="btn btn-danger"
                          onclick="return confirm('Are you sure you want to delete this report?')">
                    Delete
                  </button>
                </form>
              </div>
            </td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
    </div>

    <!-- Add Section-->
    <div class="card mb-4">
      <div class="add-container">
        <div class="add-wrapper">
          <input type="email"
                 id="new-email-input"
                 class="form-control add-input"
                 placeholder="Enter email">
          <button class="btn btn-success" onclick="addAndRedirect()">Add New Report</button>
        </div>
      </div>
    </div>

  </div>
</div>
</body>
</html>