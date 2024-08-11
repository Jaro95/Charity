<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 13.06.2024
  Time: 22:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp"/>
<div class="w3-display-container w3-light-grey main-height" id="contact">
    <p class="w3-center w3-jumbo cantact-info">Edit password</p>
    <c:if test="${not empty messageError}">
        <div class="alert alert-error">
                ${messageError}
        </div>
    </c:if>
    <div class="w3-center cantact-info">
        <form:form method="post" class="cantact-details" modelAttribute="editPassword">
            <p><form:input class="input-contact w3-border" type="password" placeholder="Password" path="password"/>
            <p><form:errors path="password" cssClass="alert alert-error"/></p>
            <p><form:input class="input-contact w3-border" type="password" placeholder="Repeat password" path="repeatPassword"/>
            <p><form:errors path="repeatPassword" cssClass="alert alert-error"/></p>
            <input type="hidden" name="id" value="${id}">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button class="w3-button w3-black" type="submit">
                <i class="fa fa-edit"></i> Zmień hasło
            </button>
        </form:form>
    </div>
</div>
<jsp:include page="footer.jsp"/>
