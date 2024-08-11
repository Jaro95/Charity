<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
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
<section class="login-page">
<%--    <c:if test="${not empty messageError}">--%>
<%--        <h1 class="slogan--steps">--%>
<%--            <div class="alert alert-error">--%>
<%--                    ${messageError}--%>
<%--            </div>--%>
<%--        </h1>--%>
<%--    </c:if>--%>
    <h2>Załóż konto</h2>
    <form:form method="post" modelAttribute="registrationRequest">
        <div class="form-group">
            <form:input path="email" type="email" placeholder="Email"/>
            <form:errors path="email" cssClass="alert alert-error"/>
        </div>
        <div class="form-group">
            <form:input type="text" path="firstName" placeholder="Imię"/>
            <form:errors path="firstName" cssClass="alert alert-error"/>
        </div>
        <div class="form-group">
            <form:input type="text" path="lastName" placeholder="Nazwisko"/>
            <form:errors path="lastName" cssClass="alert alert-error"/>
        </div>
        <div class="form-group">
            <form:input type="password" path="password" placeholder="Hasło"/>
            <h3><form:errors path="password" cssClass="alert alert-error"/></h3>
        </div>
        <div class="form-group">
            <form:input type="password" path="repeatPassword" placeholder="Powtórz hasło"/>
            <h3><form:errors path="repeatPassword" cssClass="alert alert-error"/></h3>
        </div>

        <div class="form-group form-group--buttons">
            <a href="/login" class="btn btn--without-border">Zaloguj się</a>
            <button class="btn" type="submit">Załóż konto</button>
        </div>
    </form:form>
</section>
<jsp:include page="footer.jsp"/>