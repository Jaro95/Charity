<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 16.07.2024
  Time: 16:38
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
    <h2>Zaloguj się</h2>
    <form method="post">
        <div class="form-group">
            <input type="email" name="username" placeholder="Email">
        </div>
        <div class="form-group">
            <input type="password" name="password" placeholder="Hasło">
            <a href="/charity/recovery/email" class="btn btn--small btn--without-border reset-password">Przypomnij hasło</a>
        </div>

        <div class="form-group form-group--buttons">
            <a href="/charity/registration" class="btn btn--without-border">Załóż konto</a>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button class="btn" type="submit">Zaloguj się</button>
        </div>
<%--        <c:if test="${wrongPassword == true}">--%>
<%--            <p class="alert alert-error">Nieprawidłowy login lub hasło</p>--%>
<%--        </c:if>--%>
    </form>
</section>
<jsp:include page="footer.jsp"/>
