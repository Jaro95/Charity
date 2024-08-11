<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 15.07.2024
  Time: 11:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp"/>
<section id="profile" class="help">
    <form:form modelAttribute="user">
        <form:input path="id" type="hidden"/>
        <form:input path="password" type="hidden"/>
        <form:input path="enabled" type="hidden"/>
        <form:input path="role" type="hidden"/>
        <form:input path="token" type="hidden"/>
        <form:input path="createdAccount" type="hidden"/>
    <div class="help--slides active" data-id="1">
        <ul class="help--slides-items">
            <li>
                <div class="col">
                    <div class="title">Imię: <form:input class="input-contact edit-profile centre-text" type="text" placeholder="Name" path="name"/></div>
                    <p><form:errors path="name" cssClass="alert alert-error"/></p>
                </div>
            </li>
            <li>
                <div class="col">
                    <div class="title">Nazwisko: <form:input class="input-contact edit-profile centre-text" type="text" placeholder="LastName" path="lastName"/></div>
                    <p><form:errors path="lastName" cssClass="alert alert-error"/></p>
                </div>
            </li>
            <li>
                <div class="col">
                    <div class="title">Email: <form:input class="input-contact edit-profile centre-text" type="text" placeholder="LastName" path="email"/></div>
                    <p><form:errors path="email" cssClass="alert alert-error"/></p>
                </div>
            </li>
        </ul>
        <div class="profile-button centre-text">
            <button type="submit" class="btn">Zapisz</button>
        </div>
    </div>

    </form:form>
</section>
</header>
<jsp:include page="footer.jsp"/>
