<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Meals</title>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <jsp:useBean id="action" type="java.lang.String" scope="request"/>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<form method="post">
    <h3>${action.equals("add") ? "Add" : "Edit"} meal</h3>
    <input type="hidden" name="id" value=${meal.id}>
    <input type="hidden" name="action" value=${action}>
    <pre><label>Date/time:          <input type="datetime-local" name="datetime" value=${meal.dateTime}></label></pre>
    <pre><label>Description:        <input type="text" name="description" size="40" value='${meal.description}'></label></pre>
    <pre><label>Calories:           <input type="number" name="calories" value=${meal.calories}></label></pre>
    <hr>
    <button type="submit">Save</button>
    <button onclick="window.history.back()" type="reset">Cancel</button>
</form>
</body>
</html>
