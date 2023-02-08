<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>

<a href="meals?action=add">Add meal</a>

<table border="1" cellpadding="8" cellspacing="0">
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach items="${mealsTo}" var="mealTo">
        <jsp:useBean id="mealTo" type="ru.javawebinar.topjava.model.MealTo"/>
        <tr style="${mealTo.excess ? 'color:red' : 'color:green'}">
<%--            <td <fmt:parseDate value="${ mealTo.dateTime }" pattern="yyyy-MM-dd HH:mm" var="parsedDateTime" type="both" />></td>--%>
        <td> ${mealTo.dateTime}</td>
        <td> ${mealTo.description}</td>
        <td> ${mealTo.calories}</td>
        <td><a href="meals?id=${mealTo.id}&action=delete">удалить</a></td>
        <td><a href="meals?id=${mealTo.id}&action=edit">edit</a></td>
        </tr>
    </c:forEach>
</table>
</body>

</html>