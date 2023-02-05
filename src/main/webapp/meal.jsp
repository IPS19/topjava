<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Иван
  Date: 04.02.2023
  Time: 19:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:useBean id="mealTo" type="ru.javawebinar.topjava.model.MealTo" scope="request"/>
<head>
    <title>edit meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit meal</h2>


<form method="POST" action="meals">
    DateTime: <input type="text" name="dateTime" value="<fmt:formatDate pattern="MM/dd/yyyy hh:mm" value="${mealTo.dateTime}" />"/>
    Description: <input type="text" name="description" value="<c:out value="${mealTo.description}" />" />
    Calories: <input type="text" name="description" value="<c:out value="${mealTo.calories}" />">
</form>

</body>
</html>
