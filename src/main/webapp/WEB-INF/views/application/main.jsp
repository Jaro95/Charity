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


<div class="slogan container container--90">

    <div class="slogan--item" id="step">
        <c:if test="${not empty messageError}">
            <div class="slogan--steps">
            <div class="alert alert-error slogan--steps-title">
                ${messageError}
            </div>
            </div>
        </c:if>
        <c:if test="${not empty message}">
            <div class="slogan--steps">
                <div class="alert alert-success slogan--steps-title">
                        ${message}
                </div>
            </div>
        </c:if>
        <c:if test="${not empty donationSuccessFull}">
            <h2>
                Dziękujemy za przesłanie formularza Na maila prześlemy wszelkie
                informacje o odbiorze.
            </h2>
        </c:if>
        <c:if test="${empty donationSuccessFull && empty messageError}">
            <h1>
                Oddaj rzeczy, których już nie chcesz<br>
                <span class="uppercase">potrzebującym</span>
            </h1>

            <div class="slogan--steps">
                <div class="slogan--steps-title">Wystarczą 4 proste kroki:</div>
                <ul class="slogan--steps-boxes">
                    <li>
                        <div><em>1</em><span>Wybierz rzeczy</span></div>
                    </li>
                    <li>
                        <div><em>2</em><span>Spakuj je w worki</span></div>
                    </li>
                    <li>
                        <div><em>3</em><span>Wybierz fundację</span></div>
                    </li>
                    <li>
                        <div><em>4</em><span>Zamów kuriera</span></div>
                    </li>
                </ul>
            </div>
        </c:if>
    </div>
</div>
</header>
<section id="donation" class="form--steps">
    <div class="form--steps-instructions" hidden="">
        <div class="form--steps-container">
            <h3>Ważne!</h3>
            <p data-step="1" class="">
                Uzupełnij szczegóły dotyczące Twoich rzeczy. Dzięki temu będziemy
                wiedzieć komu najlepiej je przekazać.
            </p>
            <p data-step="2" class="">
                Uzupełnij szczegóły dotyczące Twoich rzeczy. Dzięki temu będziemy
                wiedzieć komu najlepiej je przekazać.
            </p>
            <p data-step="3" class="">
                Wybierz jedną, do
                której trafi Twoja przesyłka.
            </p>
            <p data-step="4" class="">Podaj adres oraz termin odbioru rzeczy.</p>
        </div>
    </div>

    <div class="form--steps-container">
        <div class="form--steps-counter" hidden="">Krok <span>5</span>/4</div>

        <form:form method="post" modelAttribute="donation">
            <!-- STEP 1: class .active is switching steps -->
            <div data-step="1" class="" id="categoriesChoose">
                <h3>Zaznacz co chcesz oddać:</h3>

                <c:forEach items="${categories}" var="category">
                    <div class="form-group form-group--checkboxes">
                        <label>
                            <form:checkbox path="category" value="${category.id}"
                                           data-category-name="${category.name}"/>
                            <span class="checkbox"></span>
                            <span class="description">${category.name}</span>
                        </label>
                    </div>
                </c:forEach>
                <h1><form:errors path="category" cssClass="alert alert-error "/></h1>

                <div class="form-group form-group--buttons">
                    <button type="button" class="btn next-step">Dalej</button>
                </div>
            </div>

            <!-- STEP 2 -->
            <div data-step="2" class="">
                <h3>Podaj liczbę 60l worków, w które spakowałeś/aś rzeczy:</h3>

                <div class="form-group form-group--inline">
                    <label>
                        Liczba 60l worków:
                        <form:input path="quantity" type="number" step="1" min="1" value="1" id="quantityBag"/>
                    </label>
                </div>

                <div class="form-group form-group--buttons">
                    <button type="button" class="btn prev-step">Wstecz</button>
                    <button type="button" class="btn next-step">Dalej</button>
                </div>
            </div>


            <!-- STEP 4 -->
            <div data-step="3" class="" id="institutionChoose">
                <h3>Wybierz organizacje, której chcesz pomóc:</h3>
                <c:forEach items="${institutions}" var="institutionType" varStatus="status">
                    <div class="form-group form-group--checkbox">
                        <label>
                            <form:radiobutton path="institution" value="${institutionType.id}"
                                              data-institution-name="${institutionType.name}"
                                              checked="${status.index == 0 ? 'checked' : ''}"
                            />
                            <span class="checkbox radio"></span>
                            <span class="description">
                  <div class="title">Fundacja “${institutionType.name}"</div>
                  <div class="subtitle">
                          Cel i misja: ${institutionType.description}
                  </div>
                </span>
                        </label>
                    </div>
                </c:forEach>

                <div class="form-group form-group--buttons">
                    <button type="button" class="btn prev-step">Wstecz</button>
                    <button type="button" class="btn next-step">Dalej</button>
                </div>
            </div>

            <!-- STEP 5 -->
            <div data-step="4" class="">
                <h3>Podaj adres oraz termin odbioru rzecz przez kuriera:</h3>

                <div class="form-section form-section--columns">
                    <div class="form-section--column">
                        <h4>Adres odbioru</h4>
                        <div class="form-group form-group--inline">
                            <label> Ulica <form:input path="street" type="text" id="street"/> </label>
                            <label><form:errors path="street" cssClass="alert alert-error"/></label>
                        </div>

                        <div class="form-group form-group--inline">
                            <label> Miasto <form:input path="city" type="text" id="city"/> </label>
                            <label><form:errors path="city" cssClass="alert alert-error"/></label>
                        </div>

                        <div class="form-group form-group--inline">
                            <label>
                                Kod pocztowy <form:input path="zipCode" type="text" id="zipCode"/>
                            </label>
                            <label><form:errors path="zipCode" cssClass="alert alert-error"/></label>
                        </div>

                        <div class="form-group form-group--inline">
                            <label>
                                Numer telefonu <form:input path="phoneNumber" type="number" pattern="[0-9]*"
                                                           id="phoneNumber"/>

                            </label>
                            <label><form:errors path="phoneNumber" cssClass="alert alert-error"/></label>

                        </div>
                    </div>

                    <div class="form-section--column">
                        <h4>Termin odbioru</h4>
                        <div class="form-group form-group--inline">
                            <label> Data <form:input path="pickUpDate" type="date" id="pickUpDate"/> </label>
                            <label><form:errors path="pickUpDate" cssClass="alert alert-error"/></label>
                        </div>

                        <div class="form-group form-group--inline">
                            <label> Godzina <form:input path="pickUpTime" type="time" id="pickUpTime"/> </label>
                            <label><form:errors path="pickUpTime" cssClass="alert alert-error"/></label>
                        </div>

                        <div class="form-group form-group--inline">
                            <label>
                                Uwagi dla kuriera
                                <form:textarea path="pickUpComment" rows="5" id="pickUpComment"/>
                            </label>
                            <label><form:errors path="pickUpComment" cssClass="alert alert-error"/></label>
                        </div>
                    </div>
                </div>
                <div class="form-group form-group--buttons">
                    <button type="button" class="btn prev-step">Wstecz</button>
                    <button id="last-step" type="button" class="btn next-step">Dalej</button>
                </div>
            </div>

            <!-- STEP 6 -->
            <div data-step="5" class="active">
                <h3>Podsumowanie Twojej darowizny</h3>

                <div class="summary">
                    <div class="form-section">
                        <h4>Oddajesz:</h4>
                        <ul>
                            <li>
                                <span class="icon icon-bag"></span>
                                <span class="summary--text" id="quantityBagSend"></span>
                            </li>

                            <li>
                                <span class="icon icon-hand"></span>
                                <span class="summary--text" id="institutionSelect"></span>
                            </li>
                        </ul>
                    </div>

                    <div class="form-section form-section--columns">
                        <div class="form-section--column">
                            <h4>Adres odbioru:</h4>
                            <ul>
                                <li id="inputStreet"></li>
                                <li id="inputCity"></li>
                                <li id="inputZipCode"></li>
                                <li id="inputPhoneNumber"></li>
                            </ul>
                        </div>

                        <div class="form-section--column">
                            <h4>Termin odbioru:</h4>
                            <ul>
                                <li id="inputPickUpDate"></li>
                                <li id="inputPickUpTime"></li>
                                <li id="inputPickUpComment"></li>
                            </ul>
                        </div>
                    </div>
                </div>

                <div class="form-group form-group--buttons">
                    <button type="button" class="btn prev-step">Wstecz</button>
                    <button type="submit" class="btn">Potwierdzam</button>
                </div>
            </div>
        </form:form>
    </div>
</section>
<section id="about-us" class="about-us">
    <div class="about-us--text">
        <h2>O nas</h2>
        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Voluptas vitae animi rem pariatur incidunt libero
            optio esse quisquam illo omnis.</p>
        <img src="/images/signature.svg" class="about-us--text-signature" alt="Signature">
    </div>
    <div class="about-us--image"><img src="/images/about-us.jpg" alt="People in circle"></div>
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
