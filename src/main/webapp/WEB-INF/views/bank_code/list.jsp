<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<%--URL--%>
<c:set var="bankCodeUpdateUrl" value="${s:mvcUrl('bankCodeController#update').build()}" scope="page"/>
<c:set var="paginationUrl" value="${s:mvcUrl('bankCodeController#list').build()}" scope="page"/>

<t:template>
    <jsp:body>

        <div class="card card-style mb-5">

            <h6 class="card-header-style card-header">Bankovní kódy</h6>

            <div class="card-body">

                <div class="row mb-4">
                    <a href="${bankCodeUpdateUrl}">
                        <input type="button" class="btn btn-primary button_primary_new" value="Načíst bankovní kódy">
                    </a>
                </div>

                <div class="row">
                    <div class="col-sm-8 col-md-8 col-lg-8 font-weight-bold">Název</div>
                    <div class="col-sm-4 col-md-4 col-lg-4 font-weight-bold">Kód</div>
                </div>
                <hr>

                <c:choose>
                    <c:when test="${empty bankCodes}">
                        <div class="row">
                            <div class="col-sm-12 col-md-12 col-lg-12">
                                Nejsou k dispozici žádné bankovní kódy.
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <c:forEach items="${bankCodes}" var="item" varStatus="i">
                            <div class="row">
                                <div class="col-sm-8 col-md-8 col-lg-8 align-self-center">${item.name}</div>
                                <div class="col-sm-4 col-md-4 col-lg-4 align-self-center">${item.bankCode}</div>
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
