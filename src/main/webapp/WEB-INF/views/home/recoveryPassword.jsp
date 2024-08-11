<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="input" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 16.07.2024
  Time: 16:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>Document</title>

    <link rel="stylesheet" href="/css/style.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/app.js"></script>
</head>
<body>
<header>
    <nav class="container container--70">
        <ul class="nav--actions">
            <li><a href="/charity/login" class="btn btn--small btn--without-border">Zaloguj</a></li>
            <li><a href="/charity/registration" class="btn btn--small btn--highlighted">Załóż konto</a></li>
        </ul>

        <ul>
            <li><a href="/charity#stats" class="btn btn--without-border active">Start</a></li>
            <li><a href="/charity#steps" class="btn btn--without-border">O co chodzi?</a></li>
            <li><a href="/charity#about-us" class="btn btn--without-border">O nas</a></li>
            <li><a href="/charity#help" class="btn btn--without-border">Fundacje i organizacje</a></li>
            <li><a href="/charity#contact" class="btn btn--without-border">Kontakt</a></li>
        </ul>
    </nav>
</header>
<%--<c:if test="${not empty wrongRepeatPassword}">--%>
<%--<h1 class="slogan--steps">--%>
<%--    <div class="alert alert-error">--%>
<%--            ${wrongRepeatPassword}--%>
<%--    </div>--%>
<%--</h1>--%>
<%--</c:if>--%>
<section class="login-page">

    <h2>Wprowadź nowe hasło</h2>
    <form:form modelAttribute="editPassword"  method="post" >
        <div class="form-group centre-text">
            <input:input type="password" path="password" class="centre-text"/>
            <form:errors path="password" cssClass="alert alert-error"/>
        </div>
        <div class="form-group centre-text">
            <input:input type="password" path="repeatPassword" class="centre-text"/>
            <form:errors path="repeatPassword" cssClass="alert alert-error"/>
        </div>
        <div class="form-group centre-text">
            <button type="submit" class="btn">Wyślij</button>
        </div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" name="email" value="${email}">
    </form:form>
</section>
<jsp:include page="footer.jsp"/>