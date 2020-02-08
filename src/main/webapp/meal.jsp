<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add meal</title>
</head>
<body>
<form method="POST" action='meals' name="frmAddUser">
    ID : <input
        type="number" readonly="readonly" name="mealId"
        value="${meal.id}"/> <br />
    Date/time : <input
        type="datetime-local" name="date"
        value="${meal.dateTime}" required/> <br />
    Description : <input
        type="text" name="desc"
        value="${meal.description}" required> <br />
    Calories : <input
        type="number" name="calories"
        value="${meal.calories}" required/> <br />
    <input type="submit" value="Submit" />
</form>
<p><a href="meals">List meals</a></p>
</body>
</html>
