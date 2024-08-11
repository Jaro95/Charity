<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 15.07.2024
  Time: 11:56
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


    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css">
    <link rel="stylesheet" href="/css/style.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/app.js"></script>
</head>
<body>
<header>
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
</header>

<section>
    <c:if test="${not empty message}">
        <h1 class="slogan--steps centre-text">
            <div class="alert alert-success">
                    ${message}
            </div>
        </h1>
    </c:if>
    <h2>Moje dary</h2>

    <table id="donationUserTable" class="display">
        <thead>
        <tr>
            <th>Id</th>
            <th>Ilość</th>
            <th>Kategoria</th>
            <th>Fundacja</th>
            <th>Address</th>
            <th>Nr. telefonu</th>
            <th>Termin odbioru</th>
            <th>Uwagi</th>
            <th>Odebrano</th>
            <th>Utworzono</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${donationList}" var="donation" varStatus="status">
            <tr>
                <td>${status.count}</td>
                <td>${donation.quantity}</td>
                <td>
                    <c:forEach items="${donation.category}" var="categories">
                        <p>${categories.name}</p>
                    </c:forEach>
                </td>
                <td>
                    <p>${donation.institution.name}</p>
                    <p>Cel i misja: ${donation.institution.description}</p>
                </td>
                <td>
                    <p>${donation.street}</p>
                    <p>${donation.zipCode} ${donation.city}</p>
                </td>
                <td>${donation.phoneNumber}</td>
                <td>
                    <p>${donation.pickUpTime}</p>
                    <p>${donation.pickUpDate}</p>
                </td>
                <td>${donation.pickUpComment}</td>
                <td>${donation.receive}</td>
                <td>
                    <p>${donation.createdTime}</p>
                    <p>${donation.createdDate}</p>
                </td>
                <td>
                    <button class="send-button"
                            onclick="location.href='/charity/donation/userDonation/update?id=${donation.id}'">
                        Edytuj
                    </button>
                    <a class="send-button" id="delete-donation"
                       href="${pageContext.request.contextPath}/charity/donation/userDonation/delete?id=${donation.id}">
                        Usuń</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</section>


<jsp:include page="footer.jsp"/>
