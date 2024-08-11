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
        <h1 class="slogan--steps">
            <div class="alert alert-success">
                    ${message}
            </div>
        </h1>
    </c:if>
    <h2>Edytuj dar</h2>
    <form:form modelAttribute="donation">

    <table id="updateDonationUser" class="display">
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
            <form:input path="id" type="hidden"/>
            <form:input path="user" type="hidden"/>
            <tr>
                <td>1</td>
                <td><form:input path="quantity" type="number" step="1" min="1" id="quantityBag"/></td>
                <td>
                    <c:forEach items="${categories}" var="category">
                        <p><form:checkbox path="category" value="${category}"/> ${category.name}</p>
                    </c:forEach>
                </td>
                <td>
                    <c:forEach items="${institutions}" var="institutionType">

                        <p><form:radiobutton path="institution" value="${institutionType}"/>
                            <div class="title">Fundacja “${institutionType.name}"</div>
                            <div class="subtitle">Cel i misja: ${institutionType.description}</div>
                        </p>

                    </c:forEach>
                </td>
                <td>
                    <p><form:input path="street" type="text" id="street"/></p>
                    <p><form:errors path="street" cssClass="alert alert-error"/></p>

                    <p><form:input path="zipCode" type="text" id="zipCode"/></p>
                    <p><form:errors path="zipCode" cssClass="alert alert-error"/></p>
                    <p><form:input path="city" type="text" id="city"/></p>
                    <p><form:errors path="city" cssClass="alert alert-error"/></p>
                </td>
                <td>
                    <p><form:input path="phoneNumber" type="number" pattern="[0-9]*"
                                   id="phoneNumber"/></p>
                    <p><form:errors path="phoneNumber" cssClass="alert alert-error"/></p>
                </td>
                <td>
                    <p><form:input path="pickUpDate" type="date" id="pickUpDate"/></p>
                    <p><form:errors path="pickUpDate" cssClass="alert alert-error"/></p>
                    <p><form:input path="pickUpTime" type="time" id="pickUpTime"/></p>
                    <p><form:errors path="pickUpTime" cssClass="alert alert-error"/></p>
                </td>
                <td>
                    <p><form:textarea path="pickUpComment" type="text" id="pickUpComment"/></p>
                    <p><form:errors path="pickUpComment" cssClass="alert alert-error"/></p>
                </td>
                <td>
                    <form:select class="w3-border" path="receive">
                        <form:option value="true">Tak</form:option>
                        <form:option value="false">Nie</form:option>
                    </form:select>
                </td>
                <td>
                    <p>${createdDate}</p>
                    <p>${createdTime}</p>
                    <p><form:input path="createdDate" type="hidden" id="createdDate"/></p>
                    <p><form:input path="createdTime" type="hidden" id="createdTime"/></p>
                </td>
                <td>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <button class="send-button" type="submit">Zapisz</button>
                </td>
            </tr>
        </tbody>
    </table>
    </form:form>
</section>


<jsp:include page="footer.jsp"/>
