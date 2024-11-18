<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Dropdown UI</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/selectUser.css">
</head>
<body>
<div class="main-container">
  <jsp:include page="../common/verticalNavBar.jsp" />
  <label for="userSelect" class="label">Select User :</label>
  <select id="userSelect" class="dropdown">
    <option>Dropdown</option>
    <option>Item 1</option>
    <option>Item 2</option>
    <option>Item 3</option>
  </select>
</div>
</body>
</html>
