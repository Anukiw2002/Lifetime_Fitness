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
    <jsp:include page="../client/clientVerticalNavbar.jsp" />
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
        <tr>
            <td>1</td>
            <td>John Doe</td>
            <td>Deadlifts</td>
            <td>200</td>
        </tr>
        <tr>
            <td>2</td>
            <td>Jane Smith</td>
            <td>Bench Press</td>
            <td>150</td>
        </tr>
        <tr>
            <td>3</td>
            <td>Mark Taylor</td>
            <td>Squats</td>
            <td>180</td>
        </tr>
        <tr>
            <td>4</td>
            <td>Emma Watson</td>
            <td>Overhead Press</td>
            <td>120</td>
        </tr>
        <tr>
            <td>5</td>
            <td>Chris Evans</td>
            <td>Pull-Ups</td>
            <td>50</td>
        </tr>
        </tbody>
    </table>


</div>
</body>
</html>
