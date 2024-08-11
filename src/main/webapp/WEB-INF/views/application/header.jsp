<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 15.07.2024
  Time: 11:54
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
<header class="header--form-page">
    <nav class="container container--70">
        <ul class="nav--actions">
            <li class="logged-user">
                <sec:authorize access="isAuthenticated()">
                    <sec:authentication property="principal.user.name"/>
                </sec:authorize>
                <ul class="dropdown">
                    <li><a href="/charity/donation/profile">Profil</a></li>
                    <li><a href="/charity/donation/userDonation">Moje zbiórki</a></li>
                    <li><a href="/charity/donation/logout">Wyloguj</a></li>
                </ul>
            </li>
        </ul>

        <ul>
            <li><a href="/charity/donation#step" class="btn btn--without-border active">Start</a></li>
            <li><a href="/charity/donation#donation" class="btn btn--without-border">Przekaż dary</a></li>
            <li><a href="/charity/donation#about-us" class="btn btn--without-border">O nas</a></li>
            <li><a href="/charity/donation#help" class="btn btn--without-border">Fundacje i organizacje</a></li>
            <li><a href="/charity/donation#contact" class="btn btn--without-border">Kontakt</a></li>
            <sec:authorize access="hasAnyRole('SUPER_ADMIN','ADMIN')">
            <li><a href="/charity/admin" class="btn btn--without-border">Admin Panel</a></li>
            </sec:authorize>
        </ul>
    </nav>

