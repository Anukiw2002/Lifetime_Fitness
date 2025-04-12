<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Videos</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/viewVideos.css" />
</head>
<body>

<div class="main-content">
    <jsp:include page="../client/clientVerticalNavbar.jsp" />

    <div class="container">
        <!-- Header Section -->
        <div class="flex justify-between items-center mb-4">
            <h2>All Videos</h2>
        </div>

        <!-- Video Content Section -->
        <div class="card">
            <c:if test="${not empty videos}">
                <table class="video-table">
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Watch Video</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="video" items="${videos}">
                        <tr>
                            <td>${video.name}</td>
                            <td>${video.description}</td>
                        </tr>

                        <td>
                            <form action="${video.url}" method="get" target="_blank">
                                <button type="submit" class="btn btn-secondary">Watch Video</button>
                            </form>
                        </td>

                    </c:forEach>
                    </tbody>
                </table>
            </c:if>

            <c:if test="${empty videos}">
                <p class="no-videos">No videos available at the moment.</p>
            </c:if>
        </div>
    </div>
</div>

</body>
</html>
