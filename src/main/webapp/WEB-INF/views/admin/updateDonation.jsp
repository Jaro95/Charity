<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 13.06.2024
  Time: 23:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp"/>

<div class="w3-display-container w3-light-grey contact" id="admin">
    <p class="w3-center w3-jumbo cantact-info">Donation:</p>
    <c:if test="${not empty message}">
        <div class="alert alert-success">
                ${message}
        </div>
    </c:if>
    <form:form modelAttribute="donation" method="post">

        <table id="adminTable" class="display">
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
</div>


<jsp:include page="footer.jsp"/>

