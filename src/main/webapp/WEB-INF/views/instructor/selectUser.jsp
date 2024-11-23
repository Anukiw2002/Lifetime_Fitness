<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Search User UI</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/selectUser.css">
</head>
<body>
<!-- Navbar Container -->
<div class="navbar-container">
  <jsp:include page="../common/verticalNavBar.jsp" />
</div>

<!-- Main Content Container -->
<div class="content-container">
  <div class="search-bar">
    <label for="searchInput" class="label">Search User:</label>
    <input type="text" id="searchInput" class="search-input" placeholder="Type user name...">
    <button class="search-button" onclick="searchUser()">Search</button>
  </div>
</div>

<script>
  function searchUser() {
    const inputValue = document.getElementById('searchInput').value;
    if (inputValue.trim()) {
      alert(`Searching for: ${inputValue}`);
      // Perform actual search logic here (e.g., send the value to the server).
    } else {
      alert('Please enter a valid search term.');
    }
  }
</script>
</body>
</html>
