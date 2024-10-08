<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 14.07.2024
  Time: 12:38
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="header.jsp"/>
<c:if test="${not empty messageError}">
    <h1 class="slogan--steps">
        <div class="alert alert-error">
                ${messageError}
        </div>
    </h1>
</c:if>
<c:if test="${not empty message}">
    <h1 class="slogan--steps">
        <div class="alert alert-success">
                ${message}
        </div>
    </h1>
</c:if>
<div class="slogan container container--90">
    <div class="slogan--item">
        <h1>
            Zacznij pomagać!<br>
            Oddaj niechciane rzeczy w zaufane ręce
        </h1>
    </div>
</div>
</header>
<section id="stats" class="stats">
    <div class="container container--85">
        <div class="stats--item">
            <em>${quantityBag}</em>
            <h3>Oddanych worków</h3>
            <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Eius est beatae, quod accusamus illum
                tempora!</p>
        </div>

        <div class="stats--item">
            <em>${allDonations}</em>
            <h3>Przekazanych darów</h3>
            <p>Lorem ipsum dolor sit amet consectetur, adipisicing elit. Laboriosam magnam, sint nihil cupiditate quas
                quam.</p>
        </div>

    </div>
</section>

<section id="steps" class="steps">
    <h2>Wystarczą 4 proste kroki</h2>

    <div class="steps--container">
        <div class="steps--item">
            <span class="icon icon--hands"></span>
            <h3>Wybierz rzeczy</h3>
            <p>ubrania, zabawki, sprzęt i inne</p>
        </div>
        <div class="steps--item">
            <span class="icon icon--arrow"></span>
            <h3>Spakuj je</h3>
            <p>skorzystaj z worków na śmieci</p>
        </div>
        <div class="steps--item">
            <span class="icon icon--glasses"></span>
            <h3>Zdecyduj komu chcesz pomóc</h3>
            <p>wybierz zaufane miejsce</p>
        </div>
        <div class="steps--item">
            <span class="icon icon--courier"></span>
            <h3>Zamów kuriera</h3>
            <p>kurier przyjedzie w dogodnym terminie</p>
        </div>
    </div>

    <a href="/charity/registration" class="btn btn--large">Załóż konto</a>
</section>

<section id="about-us" class="about-us">
    <div class="about-us--text">
        <h2>O nas</h2>
        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Voluptas vitae animi rem pariatur incidunt libero
            optio esse quisquam illo omnis.</p>
        <img src="images/signature.svg" class="about-us--text-signature" alt="Signature">
    </div>
    <div class="about-us--image"><img src="images/about-us.jpg" alt="People in circle"></div>
</section>

<section id="help" class="help">
    <h2>Komu pomagamy?</h2>

    <!-- SLIDE 1 -->
    <div class="help--slides active" data-id="1">
        <p>W naszej bazie znajdziesz listę zweryfikowanych Fundacji, z którymi współpracujemy.
            Możesz sprawdzić czym się zajmują.</p>

        <ul class="help--slides-items">
            <li>
            <c:forEach items="${institutions}" var="institution" varStatus="status">
                <c:if test="${status.count % 3 == 0}">
            <li>
            </c:if>
                <div class="col">
                    <div class="title">Fundacja "${institution.name}"</div>
                    <div class="subtitle">Cel i misja: ${institution.description}</div>
                </div>
                <c:if test="${status.count % 2 == 0 || status.last}">
                    </li>
                </c:if>

            </c:forEach>

        </ul>


    </div>

</section>
<jsp:include page="footer.jsp"/>