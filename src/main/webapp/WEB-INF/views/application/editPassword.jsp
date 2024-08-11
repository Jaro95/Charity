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
    <form:form modelAttribute="editPassword">

        <div class="help--slides active" data-id="1">
            <ul class="help--slides-items">
                <li>
                    <div class="col">
                        <div class="title">Hasło: <form:input class="input-contact centre-text" type="password"
                                                              placeholder="Password" path="password"/></div>
                        <div class="alert alert-error"><form:errors path="password" cssClass="alert alert-error"/></div>
                    </div>
                </li>
                <li>
                    <div class="col">
                        <div class="title">Powtórz hasło: <form:input class="input-contact centre-text" type="password"
                                                                      placeholder="Repeat password" path="repeatPassword"/></div>
                        <c:if test="${not empty messageError}">
                            <div class="title alert alert-error centre-text">${messageError}</div>
                        </c:if>
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
