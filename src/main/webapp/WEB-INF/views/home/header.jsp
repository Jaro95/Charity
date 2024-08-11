<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 14.07.2024
  Time: 12:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>Charity</title>

    <link rel="stylesheet" href="/css/style.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/app.js"></script>
</head>
<body>
<header class="header--main-page">
    <nav class="container container--70">
        <sec:authorize access="isAnonymous()">
        <ul class="nav--actions">
            <li><a href="/charity/login" class="btn btn--small btn--without-border">Zaloguj</a></li>
            <li><a href="/charity/registration" class="btn btn--small btn--highlighted">Załóż konto</a></li>
        </ul>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
        <ul class="nav--actions">
            <li class="logged-user">
                <sec:authorize access="isAuthenticated()">
                    <sec:authentication property="principal.user.name"/>
                </sec:authorize>
                <ul class="dropdown">
                    <li><a href="/charity/donation">Aplikacja</a></li>
                    <li><a href="#">Profil</a></li>
                    <li><a href="#">Moje zbiórki</a></li>
                    <li><a href="/charity/donation/logout">Wyloguj</a></li>
                </ul>
            </li>
        </ul>
        </sec:authorize>
        <ul>
            <li><a href="/charity#stats" class="btn btn--without-border active">Start</a></li>
            <li><a href="/charity#steps" class="btn btn--without-border">O co chodzi?</a></li>
            <li><a href="/charity#about-us" class="btn btn--without-border">O nas</a></li>
            <li><a href="/charity#help" class="btn btn--without-border">Fundacje i organizacje</a></li>
            <li><a href="/charity/donation" class="btn btn--without-border">Przekaż dary</a></li>
            <li><a href="/charity#contact" class="btn btn--without-border">Kontakt</a></li>
        </ul>
    </nav>

