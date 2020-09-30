<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<%--URL--%>
<c:set var="transactionIdDetailUrl" value="${s:mvcUrl('transactionController#id-detail').build()}" scope="page"/>
<c:set var="paginationUrl" value="${s:mvcUrl('transactionController#list').build()}" scope="page"/>

<t:template>
    <jsp:body>
        <div class="row">
            <div class="col-md-12">
                <div class="card card-style mb-5">

                    <h6 class="card-header card-header-style">Pohyby</h6>

                    <div class="card-body">

                        <div class="row">
                            <div class="col-sm-12 col-md-2 col-lg-2 font-weight-bold">Datum</div>
                            <div class="col-sm-12 col-md-8 col-lg-8 font-weight-bold">Platba</div>
                            <div class="col-sm-12 col-md-2 col-lg-2 font-weight-bold text-right">Částka</div>
                        </div>
                        <hr>

                            <%--counter pro počítání transakcí --%>
                        <c:set var="transactionCounter" value="0"/>

                        <c:forEach items="${transactions}" var="item" varStatus="i">
                            <c:if test="${(item.income == true && item.processingDate != null) || (item.income == false)}">
                                <c:set var="transactionCounter" value="${transactionCounter+1}"/>
                                <div class="row">
                                    <div class="col-sm-12 col-md-2 col-lg-2 size-14 align-center">
                                        <fmt:formatDate type="date" pattern="dd.MM.yyyy" value="${item.dueDate}"/>
                                        <br>

                                            <%-- odkaz na detail transakce --%>
                                        <a href="${transactionIdDetailUrl}${item.id}">[Více]</a>

                                    </div>
                                    <div class="col-sm-12 col-md-7 col-lg-7">
                                        <div class="row">
                                            <div class="col-sm-12 col-md-12 col-lg-12">
                                                <c:choose>
                                                    <c:when test="${item.income eq true && item.processingDate != null}">
                                                        Příchozí úhrada (${item.number}/${item.code})
                                                    </c:when>
                                                    <c:when test="${item.income eq false && item.processingDate != null}">
                                                        Odchozí úhrada (${item.number}/${item.code})
                                                    </c:when>
                                                    <c:when test="${item.income eq false && item.processingDate == null}">
                                                        Nezpracovaná odchozí úhrada (${item.number}/${item.code})
                                                    </c:when>
                                                </c:choose>
                                            </div>
                                        </div>
                                        <div class="row size-14">
                                            <div class="col-sm-12 col-md-12 col-lg-4">
                                                Variabilní: ${item.variableSymbol}
                                            </div>
                                            <div class="col-sm-12 col-md-12 col-lg-4">
                                                Specifický: ${item.specificSymbol}
                                            </div>
                                            <div class="col-sm-12 col-md-12 col-lg-4">
                                                Konstantní: ${item.constantSymbol}
                                            </div>
                                        </div>
                                    </div>
                                    <c:if test="${item.income eq true}">
                                        <div class="col-sm-12 col-md-2 col-lg-3 align-self-center text-right">
                                    <span>
                                        <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2"
                                                          value="${item.value}"/> CZK
                                    </span>
                                        </div>
                                    </c:if>
                                    <c:if test="${item.income eq false}">
                                        <div class="col-sm-12 col-md-2 col-lg-3 align-self-center text-danger text-right">
                                    <span>
                                        -<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2"
                                                           value="${item.value}"/> CZK
                                    </span>
                                        </div>
                                    </c:if>

                                </div>
                                <c:if test="${!i.last}">
                                    <hr>
                                </c:if>
                            </c:if>
                        </c:forEach>

                            <%--Výpis pro nula transakcí --%>
                        <c:choose>
                            <c:when test="${transactionCounter == 0}">
                                <div class="row">
                                    <div class="col-sm-12 col-md-12 col-lg-12">
                                        Nejsou k dispozici žádné transakce.
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>

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