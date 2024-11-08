<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Leaderboard</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/leaderBoard.css">
</head>
<body>
<div class="leaderboard">
    <h1>LEADERBOARD</h1>
    <table>
        <thead>
        <tr>
            <th>RATING</th>
            <th>NAME</th>
            <th>CATEGORY</th>
            <th>MAXIMUM(KG)</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="entry" items="${leaderboardEntries}">
            <tr>
                <td>${entry.rating}</td>
                <td>${entry.name}</td>
                <td>${entry.category}</td>
                <td>${entry.maximumKg}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
