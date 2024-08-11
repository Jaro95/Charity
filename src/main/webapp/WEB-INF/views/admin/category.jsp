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
    <p class="w3-center w3-jumbo cantact-info">Category:</p>
    <c:if test="${not empty message}">
        <div class="alert alert-success">
                ${message}
        </div>
    </c:if>

    <p class="w3-center w3-jumbo cantact-info"><button class="w3-button w3-black"
            onclick="location.href='/charity/admin/category/add'">
        <i class="fa fa-pencil"></i> Add new category</button>
    </p>
    <table id="adminTable" class="display">
        <thead>
        <tr>
            <th>Id</th>
            <th>Category</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${categoryList}" var="institution" varStatus="status">
            <tr>
                <c:if test="${not empty updateId && institution.id eq updateId}">
                <form method="post">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <input name="categoryId" type="hidden" value="${institution.id}">
                    <td>${status.count}</td>
                    <td><input name="categoryName" type="text" value="${institution.name}"></td>
                    <td>
                        <button class="w3-button w3-black" type="submit">
                            <i class="fa fa-save"></i> Zapisz
                        </button>
                    </td>
                </form>
                    </c:if>
                    <c:if test="${empty updateId}">
                    <td>${status.count}</td>
                    <td>${institution.name}</td>
                    <td>

                        <button class="w3-button w3-black"
                                onclick="location.href='/charity/admin/category?updateId=${institution.id}'">
                            <i class="fa fa-pencil"></i> Edytuj
                        </button>
                        <sec:authorize access="hasRole('SUPER_ADMIN')">
                            <a class="w3-button w3-black" id ="delete-category"
                               href="${pageContext.request.contextPath}/charity/admin/category/delete?deleteId=${institution.id}">
                                <i class="fa fa-trash"></i>Usuń</a>
                        </sec:authorize>
                    </td>
                    </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>


<jsp:include page="footer.jsp"/>

