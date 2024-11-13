<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Frontend Navigation Test Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 40px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        h1 {
            color: #333;
            margin-bottom: 20px;
        }
        .nav-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
            gap: 15px;
            margin-top: 20px;
        }
        .nav-item {
            background-color: #5D0C0B;
            color: white;
            padding: 15px;
            border-radius: 5px;
            text-decoration: none;
            text-align: center;
            transition: background-color 0.2s;
        }
        .nav-item:hover {
            background-color: #5D0C0B;
        }
        .current-page {
            margin-top: 20px;
            padding: 10px;
            background-color: #e9ecef;
            border-radius: 5px;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Frontend Navigation Test Page</h1>

    <div class="current-page">
        Current Page: <%= request.getParameter("page") != null ? request.getParameter("page") : "none" %>
    </div>

    <div class="nav-grid">
        <a href="${pageContext.request.contextPath}/testView?page=login" class="nav-item">Login</a>
        <a href="${pageContext.request.contextPath}/testView?page=page1" class="nav-item">Sign Up Step 1</a>
        <a href="${pageContext.request.contextPath}/testView?page=page2" class="nav-item">Sign Up Step 2</a>
        <a href="${pageContext.request.contextPath}/testView?page=page3" class="nav-item">Sign Up Step 3</a>
        <a href="${pageContext.request.contextPath}/testView?page=page4" class="nav-item">Reset Password</a>
        <a href="${pageContext.request.contextPath}/testView?page=page5" class="nav-item">Reset Password Form</a>
        <a href="${pageContext.request.contextPath}/testView?page=page6" class="nav-item">Sign Up Step 4</a>
        <a href="${pageContext.request.contextPath}/testView?page=page7" class="nav-item">Member Profile</a>
        <a href="${pageContext.request.contextPath}/testView?page=page8" class="nav-item">Edit Profile</a>
        <a href="${pageContext.request.contextPath}/testView?page=page11" class="nav-item">Member Management</a>
        <a href="${pageContext.request.contextPath}/testView?page=page12" class="nav-item">Edit Member</a>
        <a href="${pageContext.request.contextPath}/testView?page=page13" class="nav-item">Instructor Management</a>
        <a href="${pageContext.request.contextPath}/testView?page=page14" class="nav-item">Add Instructor</a>
        <a href="${pageContext.request.contextPath}/testView?page=page15" class="nav-item">Edit Instructor</a>
        <a href="${pageContext.request.contextPath}/testView?page=page16" class="nav-item">Add client</a>
        <a href="${pageContext.request.contextPath}/testView?page=page17" class="nav-item">View Plans</a>
    </div>
</div>
</body>
</html>