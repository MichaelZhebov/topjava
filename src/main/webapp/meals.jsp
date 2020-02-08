<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <style><%@include file="/WEB-INF/css/meals.css"%></style>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table>
    <thead>
    <th>Data</th>
    <th>Description</th>
    <th>Calories</th>
    </thead>
    <c:forEach var="meal" items="${meals}">
        <tr class="${meal.excess ? 'red' : 'green'}">
            <td>${meal.dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm"))}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>