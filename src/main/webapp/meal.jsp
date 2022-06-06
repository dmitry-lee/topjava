<%@ page import="ru.javawebinar.topjava.util.MealsUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"></jsp:useBean>
    <jsp:useBean id="action" type="java.lang.String" scope="request"></jsp:useBean>
</head>
<body>
<form method="post">
    <h3><a href="index.html">Home</a></h3>
    <hr>
    <h3>Edit meal</h3>
    <input type="hidden" name="id" value=${meal.id}>
    <input type="hidden" name="action" value=${action}>
    <pre><p><label>Date/time:          <input type="datetime-local" name="datetime" value=${meal.dateTime}></label></p></pre>
    <pre><p>Description:        <input type="text" name="description" value=${meal.description}></p></pre>
    <pre><p>Calories:           <input type="number" name="calories" value=${meal.calories}></p></pre>
    <hr>
    <button type="submit">Save</button>
    <button onclick="window.history.back()" type="reset">Cancel</button>
</form>
</body>
</html>
