<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%--URL--%>
<c:set var="userRequestDeleteUrl" value="${s:mvcUrl('userRequestController#id-delete').build()}" scope="page"/>
<c:set var="accountChangeInternationalPaymentUrl" value="${s:mvcUrl('accountController#id-change-payment').build()}"
       scope="page"/>
<c:set var="accountChangeLimitBelowUrl" value="${s:mvcUrl('accountController#id-change-limit').build()}" scope="page"/>

<t:template>
    <jsp:body>
        <div class="row">
            <div class="col-md-12">

                <div class="card card-style">
                    <h6 class="card-header card-header-style">Detail účtu</h6>
                    <div class="card-body">

                            <%-- informace o účtu --%>
                        <div class="row mb-2">
                            <div class="col-sm-12 col-md-6 col-lg-6 align-self-center font-weight-bold">
                                Číslo účtu
                            </div>
                            <div class="col-sm-12 col-md-6 col-lg-6">
                                    ${account.number}/${bankCode}
                            </div>
                        </div>
                        <hr>
                        <div class="row mb-2">
                            <div class="col-sm-12 col-md-6 col-lg-6 align-self-center font-weight-bold">
                                Číslo kreditní karty
                            </div>
                            <div class="col-sm-12 col-md-6 col-lg-6">
                                    ${fn:substring(account.cardNumber,0,4)}
                                    ${fn:substring(account.cardNumber,4,6)}** ****
                                    ${fn:substring(account.cardNumber,12,16)}
                            </div>
                        </div>
                        <hr>
                        <div class="row mb-2">
                            <div class="col-sm-12 col-md-6 col-lg-6 align-self-center font-weight-bold">
                                Aktuální částka účtu
                            </div>
                            <div class="col-sm-12 col-md-6 col-lg-6">
                                <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2"
                                                  value="${account.balance}"/> CZK
                            </div>
                        </div>
                        <hr>
                        <div class="row mb-2">
                            <div class="col-sm-12 col-md-6 col-lg-6 align-self-center font-weight-bold">
                                Blokovaná částka účtu
                            </div>
                            <div class="col-sm-12 col-md-6 col-lg-6">
                                <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2"
                                                  value="${account.blockedBalance}"/> CZK
                            </div>
                        </div>
                        <hr>
                        <div class="row mb-2">
                            <div class="col-sm-12 col-md-6 col-lg-6 align-self-center font-weight-bold">
                                Povolená částka do mínusu
                            </div>
                            <div class="col-sm-12 col-md-6 col-lg-6">
                                <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2"
                                                  value="${account.limitBelow}"/> CZK
                            </div>
                        </div>
                        <hr>
                        <div class="row mb-4">
                            <div class="col-sm-12 col-md-6 col-lg-6 align-self-center font-weight-bold">
                                Mezinárodní platba kartou
                            </div>
                            <div class="col-sm-12 col-md-6 col-lg-6">
                                <c:if test="${account.internationalPayment == true}">
                                    Povolena
                                </c:if>
                                <c:if test="${account.internationalPayment == false}">
                                    Zakázána
                                </c:if>
                            </div>
                        </div>


                    </div>
                </div>

                    <%-- čekající žádosti ke schválení --%>
                <c:if test="${not empty requests}">
                    <div class="card card-style mt-3">
                        <h6 class="card-header card-header-style">Seznam žádostí čekající ke zpracování</h6>
                        <div class="card-body">

                            <div class="row">
                                <div class="col-sm-12 col-md-3 col-lg-5 font-weight-bold">
                                    Typ žádosti
                                </div>
                                <div class="col-sm-12 col-md-3 col-lg-4 font-weight-bold">
                                    Hodnota
                                </div>
                                <div class="col-sm-12 col-md-3 col-lg-3 text-right">

                                </div>
                            </div>
                            <hr>
                            <c:forEach items="${requests}" var="item" varStatus="i">
                                <div class="row">

                                    <c:choose>
                                        <c:when test="${item.type == 'international_payment'}">
                                            <div class="col-sm-12 col-md-3 col-lg-5 align-self-center">
                                                Mezinárodní platba kartou
                                            </div>
                                            <div class="col-sm-12 col-md-3 col-lg-4 align-self-center">
                                                <c:choose>
                                                    <c:when test="${account.internationalPayment == false}">
                                                        Aktivovat
                                                    </c:when>
                                                    <c:otherwise>
                                                        Zakázat
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="col-sm-12 col-md-3 col-lg-5 align-self-center">
                                                Povolení částky do mínusu
                                            </div>
                                            <div class="col-sm-12 col-md-3 col-lg-4 align-self-center">
                                                <fmt:formatNumber type="number" minFractionDigits="2"
                                                                  maxFractionDigits="2"
                                                                  value="${item.value}"/> CZK
                                            </div>

                                        </c:otherwise>
                                    </c:choose>


                                    <div class="col-sm-12 col-md-3 col-lg-3 text-right">
                                        <a href="${userRequestDeleteUrl}${item.id}">
                                            <button type="button" class="btn btn-sm btn-danger">
                                                Zrušit
                                            </button>
                                        </a>
                                    </div>
                                </div>
                                <c:if test="${!i.last}">
                                    <hr>
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>
                </c:if>

                    <%-- vytváření žádostí --%>
                <div class="card card-style mt-3 mb-5">
                    <h6 class="card-header card-header-style">Vytvoření žádosti</h6>
                    <div class="card-body">
                        <div class="row">
                            <div class="col-sm-12 col-md-3 col-lg-5 font-weight-bold">
                                Typ žádosti
                            </div>
                            <div class="col-sm-12 col-md-3 col-lg-4 font-weight-bold">
                                Hodnota
                            </div>
                            <div class="col-sm-12 col-md-3 col-lg-3 text-right">

                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-12 col-md-3 col-lg-5">
                                Mezinárodní platba kartou
                            </div>
                            <div class="col-sm-12 col-md-3 col-lg-4">
                                <c:choose>
                                    <c:when test="${account.internationalPayment == false}">
                                        Aktivovat
                                    </c:when>
                                    <c:otherwise>
                                        Zakázat
                                    </c:otherwise>
                                </c:choose>
                            </div>

                            <div class="col-sm-12 col-md-3 col-lg-3 text-right">
                                <a href="${accountChangeInternationalPaymentUrl}${account.id}">

                                        <%-- pro odeslání žádosti o změnu mezinárodní platby kartou --%>
                                    <button type="button" class="btn btn-primary btn-sm button_primary_new">
                                        Odeslat
                                    </button>

                                </a>
                            </div>

                        </div>
                        <hr>

                            <%-- formulář pro úpravy limitu do mínusu --%>
                        <form action="${pageContext.request.contextPath}/account/changeValueLimitBelow/${account.id}"
                              method="post">
                            <div class="row">
                                <div class="col-sm-12 col-md-3 col-lg-5 align-self-center mt-2">
                                    Povolení částky do mínusu
                                </div>
                                <div class="col-sm-12 col-md-3 col-lg-4 mt-2">
                                    <select name="value" class="form-control">
                                        <option value="0.00">0.00 CZK</option>
                                        <option value="2000.00">2,000.00 CZK</option>
                                        <option value="5000.00">5,000.00 CZK</option>
                                        <option value="10000.00">10,000.00 CZK</option>
                                    </select>
                                </div>
                                <div class="col-sm-12 col-md-3 col-lg-3 align-self-center text-right mt-2">

                                        <%-- tlačítko pro odeslání žádosti o změnu limitu do mínusu --%>
                                    <input type="submit" class="btn btn-primary btn-sm button_primary_new"
                                           value="Odeslat">

                                </div>
                            </div>
                        </form>

                    </div>
                </div>

            </div>
        </div>
    </jsp:body>
</t:template>