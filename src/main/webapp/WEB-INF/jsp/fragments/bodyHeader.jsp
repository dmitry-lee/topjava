<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header>
    <c:set var="ctxPath" value="${pageContext.request.contextPath}"/>
    <a href="${ctxPath}/meals"><spring:message code="app.title"/></a> | <a href="${ctxPath}/users"><spring:message
        code="user.title"/></a> |
    <a href="${ctxPath}"><spring:message code="app.home"/></a>
</header>