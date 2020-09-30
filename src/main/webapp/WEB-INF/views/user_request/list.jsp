<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--URL--%>
<c:set var="userRequestDetailUrl" value="${s:mvcUrl('userRequestController#id-detail').build()}" scope="page"/>
<c:set var="userRequestConfirmlUrl" value="${s:mvcUrl('userRequestController#id-confirm').build()}" scope="page"/>
<c:set var="paginationUrl" value="${s:mvcUrl('userRequestController#list').build()}" scope="page"/>

<t:template>
    <jsp:body>
        <div class="row">
            <div class="col-md-12">
                <div class="card card-style mb-5">
                    <h6 class="card-header card-header-style">Žádosti uživatelů</h6>
                    <div class="card-body">

                        <div class="row">
                            <div class="col-sm-12 col-md-3 col-lg-3 font-weight-bold">Účet</div>
                            <div class="col-sm-12 col-md-3 col-lg-4 font-weight-bold">Typ</div>
                            <div class="col-sm-12 col-md-3 col-lg-2 font-weight-bold">Hodnota</div>
                            <div class="col-sm-12 col-md-3 col-lg-3 text-right">
                            </div>
                        </div>
                        <hr>
                        <c:choose>
                            <c:when test="${empty requests}">
                                <div class="row">
                                    <div class="col-sm-12 col-md-12 col-lg-12">
                                        Nejsou k dispozici žádné žádosti.
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <c:forEach items="${requests}" var="item" varStatus="i">
                                    <div class="row">
                                        <div class="col-sm-12 col-md-3 col-lg-3 align-self-center">${item.account.number}/${bankCode}</div>

                                        <c:choose>
                                            <c:when test="${item.type == 'international_payment'}">
                                                <div class="col-sm-12 col-md-3 col-lg-4 align-self-center">
                                                    Mezinárodní platba kartou
                                                </div>
                                                <div class="col-sm-12 col-md-3 col-lg-2 align-self-center">
                                                    <c:choose>
                                                        <c:when test="${item.account.internationalPayment == false}">
                                                            Aktivovat
                                                        </c:when>
                                                        <c:otherwise>
                                                            Zakázat
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="col-sm-12 col-md-3 col-lg-4 align-self-center">
                                                    Povolení částky do mínusu
                                                </div>
                                                <div class="col-sm-12 col-md-3 col-lg-2 align-self-center">
                                                    <fmt:formatNumber type="number" minFractionDigits="2"
                                                                      maxFractionDigits="2"
                                                                      value="${item.value}"/> CZK
                                                </div>

                                            </c:otherwise>
                                        </c:choose>

                                        <div class="col-sm-12 col-md-3 col-lg-3 text-right">

                                                <%-- tlačítko pro schválení žádosti uživatele --%>
                                            <a href="${userRequestConfirmlUrl}${item.id}"
                                               class="text-decoration-none">
                                                <input type="button" class="btn btn-primary btn-sm btn-info"
                                                       value="Schválit">
                                            </a>

                                                <%-- tlačítko pro zobrazení žádosti uživatele --%>
                                            <a href="${userRequestDetailUrl}${item.id}"
                                               class="text-decoration-none">
                                                <input type="button" class="btn btn-primary btn-sm button_primary_new"
                                                       value="Zobrazit">
                                            </a>

                                        </div>
                                    </div>
                                    <c:if test="${!i.last}">
                                        <hr>
                                    </c:if>
                                </c:forEach>

                                <%-- přidání šablony pro stránkování záznamů --%>
                                <t:pagination/>

                            </c:otherwise>
                        </c:choose>

                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>