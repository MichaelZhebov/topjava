<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <style><%@include file="/WEB-INF/css/meals.css"%></style>
    <meta charset="UTF-8">
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
    <th colspan=2>Actions</th>
    </thead>
    <c:forEach var="meal" items="${meals}">
        <tr class="${meal.excess ? 'red' : 'green'}">
            <td>${requestScope.dateTimeFormatter.format(meal.dateTime)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=edit&mealId=${meal.id}">Update</a></td>
            <td><a href="meals?action=delete&mealId=${meal.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>
<p><a href="meals?action=insert">Add Meal</a></p>
</body>
</html>