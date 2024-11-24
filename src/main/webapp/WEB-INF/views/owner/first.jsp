<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Total Reports</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/first.css">
  <script src="<%= request.getContextPath() %>/js/first.js"></script>
</head>
<body>
<div class="main-container">
  <h1>Total Reports</h1>

  <!-- Search Bar -->
  <div class="search-container">
    <input type="text" id="search-email-input" class="search-bar" placeholder="Search by email">
    <button class="search-button" onclick="searchEmail()">Search</button>
  </div>

  <!-- Reports Table -->
  <div class="table-container">
    <table id="reports-table">
      <thead>
      <tr>
        <th>Email</th>
        <th>Actions</th>
      </tr>
      </thead>
      <tbody>
      <!-- Dynamically populate the table -->
      <c:forEach var="email" items="${approvedEmails}">
        <tr>
          <td>${email}</td>
          <td>
            <div class="button-container">
              <!-- View Button -->
              <form action="<%= request.getContextPath() %>/viewReport" method="get" style="display:inline;">
                <input type="hidden" name="email" value="${email}">
                <button type="submit" class="table-button view">View</button>
              </form>
              <!-- Update Button -->
              <form action="<%= request.getContextPath() %>/updateReport" method="get" style="display:inline;">
                <input type="hidden" name="email" value="${email}">
                <button type="submit" class="table-button update">Update</button>
              </form>
              <!-- Delete Button -->
              <form action="<%= request.getContextPath() %>/deleteReport" method="post" style="display:inline;">
                <input type="hidden" name="email" value="${email}">
                <button type="submit" class="table-button delete" onclick="return confirm('Are you sure you want to delete this report?')">Delete</button>
              </form>
            </div>
          </td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </div>

  <!-- Add Section -->
  <div class="add-container">
    <input type="email" id="new-email-input" class="add-input" placeholder="Enter email">
    <button class="add-button" onclick="addAndRedirect()">Add</button>
  </div>
</div>
</body>
</html>
