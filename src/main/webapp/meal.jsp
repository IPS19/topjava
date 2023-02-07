<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
<head>
    <title>edit meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit meal</h2>
<form method="POST" action="meals">
    <input type="hidden" name="id" value="${meal.id}">
    <table>
        <tr>
            <td>DateTime:</td>
            <td><input type="text" name="dateTime" value="<c:out value="${meal.dateTimeFormated}" />"/></td>
        </tr>
        <tr>
            <td>Description:</td>
            <td><input type="text" name="description" value="<c:out value="${meal.description}" />"/></td>
        </tr>
        <tr>
            <td>Calories:</td>
            <td><input type="text" name="calories" value="<c:out value="${meal.calories}" />"></td>
        </tr>
    </table>
    <button type="submit">Save</button>
    <button onclick="window.history.back()" type="button">Cancel</button>

</form>

</body>
</html>
