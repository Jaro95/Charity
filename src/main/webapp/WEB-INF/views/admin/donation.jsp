<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
    <table id="adminTable" class="display">
        <thead>
        <tr>
            <th>Id</th>
            <th>Quantity</th>
            <th>Categories</th>
            <th>Institution</th>
            <th>Address</th>
            <th>Phone number</th>
            <th>Pick Up Date Time</th>
            <th>Receive</th>
            <th>Created</th>
            <th>User Id</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${donationList}" var="donation">
            <tr>
                <td>${donation.id}</td>
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
                <td>${donation.street}, ${donation.zipCode} ${donation.city}</td>
                <td>${donation.phoneNumber}</td>
                <td>
                    <p>${donation.pickUpTime}</p>
                    <p>${donation.pickUpDate}</p>

                </td>
                <td>${donation.receive}</td>
                <td>
                    <p>${donation.createdTime}</p>
                    <p>${donation.createdDate}</p>
                </td>
                <td>${donation.user.id}</td>
                <td>
                    <button class="w3-button w3-black"
                            onclick="location.href='/charity/admin/donation/update?id=${donation.id}'">
                        <i class="fa fa-pencil"></i> Edytuj
                    </button>
                    <sec:authorize access="hasRole('SUPER_ADMIN')">
                        <a class="w3-button w3-black" id="delete-donation"
                           href="${pageContext.request.contextPath}/charity/admin/donation/delete?id=${donation.id}">
                            <i class="fa fa-trash"></i>Usuń</a>
                    </sec:authorize>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>


<jsp:include page="footer.jsp"/>

