<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Blog</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/editBlog.css" />
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400&display=swap" rel="stylesheet">
</head>
<body>
<jsp:include page="../common/verticalNavBar.jsp" />

<div class="edit-container">
    <h2>Edit Blog</h2>

    <!-- Step 1: Form to Request Blog ID -->
    <c:if test="${empty blog}">
        <form action="${pageContext.request.contextPath}/getBlogDetails" method="post">
            <label for="blogId">Enter Blog ID:</label>
            <input type="text" id="blogId" name="blogId" placeholder="Enter Blog ID" required />
            <button type="submit" class="submit-button">Fetch Blog Details</button>
        </form>
    </c:if>

    <!-- Step 2: Edit Blog Details -->
    <c:if test="${not empty blog}">
        <form action="${pageContext.request.contextPath}/editBlog" method="post">
            <label for="blogId">Blog ID:</label>
            <input type="text" id="blogId" name="blogId" value="${blog.id}" readonly />

            <label for="blogTitle">Blog Title:</label>
            <input type="text" id="blogTitle" name="blogTitle" value="${blog.title}" required />

            <label for="blogLink">Blog Link:</label>
            <input type="url" id="blogLink" name="blogLink" value="${blog.link}" placeholder="https://example.com/blog" required />

            <label for="blogDescription">Blog Description:</label>
            <textarea id="blogDescription" name="blogDescription" rows="10" required>${blog.description}</textarea>

            <button type="submit" class="submit-button">Update Blog</button>
        </form>
    </c:if>
</div>

</body>
</html>
