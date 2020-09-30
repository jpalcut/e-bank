<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<%--URL--%>
<c:set var="transactionNewIdUrl" value="${s:mvcUrl('transactionController#new-id').build()}" scope="page"/>
<c:set var="templateIdtUrl" value="${s:mvcUrl('templateController#id').build()}" scope="page"/>
<c:set var="templateIdDeleteUrl" value="${s:mvcUrl('templateController#id-delete').build()}" scope="page"/>
<c:set var="templateNewtUrl" value="${s:mvcUrl('templateController#new').build()}" scope="page"/>

<c:set var="paginationUrl" value="${s:mvcUrl('templateController#list').build()}" scope="page"/>

<t:template>
    <jsp:body>

        <div class="card card-style mb-5">

            <h6 class="card-header-style card-header">Vzory plateb</h6>

            <div class="card-body">

                <div class="row mb-4">
                    <a href="${templateNewtUrl}">
                        <input type="button" class="btn btn-primary button_primary_new" value="Přidat vzor">
                    </a>
                </div>

                <div class="row">
                    <div class="col-sm-12 col-md-3 col-lg-3 font-weight-bold">Název</div>
                    <div class="col-sm-12 col-md-3 col-lg-3 font-weight-bold">Účet</div>
                    <div class="col-sm-12 col-md-2 col-lg-2 font-weight-bold">Částka</div>
                    <div class="col-sm-12 col-md-4 col-lg-4 text-right">
                    </div>
                </div>
                <hr>

                <c:choose>
                    <c:when test="${empty templates}">
                        <div class="row">
                            <div class="col-sm-12 col-md-12 col-lg-12">
                                Nejsou k dispozici žádné šablony.
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <c:forEach items="${templates}" var="item" varStatus="i">
                            <div class="row">
                                <div class="col-sm-12 col-md-3 col-lg-3 align-self-center">${item.name}</div>
                                <div class="col-sm-12 col-md-3 col-lg-3 align-self-center">${item.number}/${item.code}</div>
                                <div class="col-sm-12 col-md-3 col-lg-3 align-self-center">
                                    <c:if test="${not empty item.value}">
                                        <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2"
                                                          value="${item.value}"/> CZK
                                    </c:if>
                                </div>
                                <div class="col-sm-12 col-md-3 col-lg-3 text-right">

                                        <%-- použití šablony --%>
                                    <a href="${transactionNewIdUrl}${item.id}"
                                       class="text-decoration-none">
                                        <input type="button" class="btn btn-primary btn-sm button_primary_new"
                                               value="Použít">
                                    </a>

                                        <%-- upravení šablony --%>
                                    <a href="${templateIdtUrl}${item.id}"
                                       class="text-decoration-none">
                                        <input type="button" class="btn btn-sm btn-info" value="Upravit">
                                    </a>

                                        <%-- smazání šablony --%>
                                    <a href="${templateIdDeleteUrl}${item.id}"
                                       class="text-decoration-none">
                                        <input type="button" class="btn btn-sm btn-danger" value="Smazat"
                                               onclick="return confirm('Opravdu chcete šablonu smazat?')">
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
                <br>

            </div>
        </div>

    </jsp:body>
</t:template>
