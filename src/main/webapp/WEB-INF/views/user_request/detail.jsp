<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<%--URL--%>
<c:set var="userRequestConfirmlUrl" value="${s:mvcUrl('userRequestController#id-confirm').build()}" scope="page"/>
<c:set var="userRequestDeleteUrl" value="${s:mvcUrl('userRequestController#id-delete').build()}" scope="page"/>
<c:set var="userIdUrl" value="${s:mvcUrl('userController#id').build()}" scope="page"/>

<t:template>
    <jsp:body>
        <div class="row">
            <div class="col-md-12">
                <div class="card card-style mb-5">
                    <h6 class="card-header card-header-style">Detail žádosti</h6>
                    <div class="card-body">

                        <div class="row">
                            <div class="col-sm-12 col-md-6 col-lg-6 align-self-center font-weight-bold">
                                Účet
                            </div>
                            <div class="col-sm-12 col-md-6 col-lg-6">
                                    ${request.account.number}/${bankCode}
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-12 col-md-6 col-lg-6 align-self-center font-weight-bold">
                                Vlastník
                            </div>
                            <div class="col-sm-12 col-md-6 col-lg-6">
                                <a href="${userIdUrl}${request.account.user.id}">
                                        ${request.account.user.firstname} ${request.account.user.lastname}
                                </a>
                            </div>
                        </div>
                        <hr>
                        <c:choose>
                            <c:when test="${request.type == 'international_payment'}">
                                <div class="row">
                                    <div class="col-sm-12 col-md-6 col-lg-6 align-self-center font-weight-bold">
                                        Typ žádosti
                                    </div>
                                    <div class="col-sm-12 col-md-6 col-lg-6">
                                        Mezinárodní platba kartou
                                    </div>
                                </div>
                                <hr>
                                <div class="row">
                                    <div class="col-sm-12 col-md-6 col-lg-6 align-self-center font-weight-bold">
                                        Změna na
                                    </div>
                                    <div class="col-sm-12 col-md-6 col-lg-6">
                                        <c:choose>
                                            <c:when test="${request.account.internationalPayment == false}">
                                                Aktivovat
                                            </c:when>
                                            <c:otherwise>
                                                Zakázat
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                                <hr>
                            </c:when>
                            <c:otherwise>
                                <div class="row">
                                    <div class="col-sm-12 col-md-6 col-lg-6 align-self-center font-weight-bold">
                                        Typ žádosti
                                    </div>
                                    <div class="col-sm-12 col-md-6 col-lg-6">
                                        Povolení částky do mínusu
                                    </div>
                                </div>
                                <hr>
                                <div class="row">
                                    <div class="col-sm-12 col-md-6 col-lg-6 align-self-center font-weight-bold">
                                        Aktuální hodnota
                                    </div>
                                    <div class="col-sm-12 col-md-6 col-lg-6">
                                        <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2"
                                                          value="${request.account.limitBelow}"/> CZK
                                    </div>
                                </div>
                                <hr>
                                <div class="row">
                                    <div class="col-sm-12 col-md-6 col-lg-6 align-self-center font-weight-bold">
                                        Změna na
                                    </div>
                                    <div class="col-sm-12 col-md-6 col-lg-6">
                                        <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2"
                                                          value="${request.value}"/> CZK
                                    </div>
                                </div>
                                <hr>
                            </c:otherwise>
                        </c:choose>

                        <div class="row mb-2 mt-2">
                            <div class="col-sm-12 col-md-12 col-lg-12 text-right">

                                    <%-- tlačítko pro schválení žádosti uživatele --%>
                                <a href="${userRequestConfirmlUrl}${request.id}"
                                   class="text-decoration-none">
                                    <input type="button" class="btn btn-primary btn-sm btn-info"
                                           value="Schválit">
                                </a>

                                    <%-- tlačítko pro zamítnutí žádosti uživatele --%>
                                <a href="${userRequestDeleteUrl}${request.id}"
                                   class="text-decoration-none">
                                    <input type="button" class="btn btn-sm btn-danger" value="Zamítnout" onclick="return confirm('Opravdu chcete požadavek zamítnout?')">
                                </a>

                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>