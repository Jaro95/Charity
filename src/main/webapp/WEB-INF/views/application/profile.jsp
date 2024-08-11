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
    <c:if test="${not empty message}">
        <h1 class="slogan--steps">
            <div class="alert alert-success">
                    ${message}
            </div>
        </h1>
    </c:if>
    <h2>${name} ${lastName}</h2>

    <div class="help--slides active" data-id="1">
        <ul class="help--slides-items">
            <li>
                <div class="col">
                    <div class="title">Email: ${email}</div>
                </div>

            </li>
            <li>
                <div class="col">
                    <div class="title">Ilość zbiórek: ${donation}</div>
                </div>
            </li>
        </ul>
    </div>
    <div class="profile-button">
        <a href="/charity/donation/profile/edit" class="btn">Edytuj dane</a>
        <a href="/charity/donation/profile/password" class="btn">Zmień hasło</a>
    </div>
</section>
</header>


<jsp:include page="footer.jsp"/>
