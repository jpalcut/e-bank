<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<%--URL--%>
<c:set var="userIdUrl" value="${s:mvcUrl('userController#id').build()}" scope="page"/>
<c:set var="userDeleteUrl" value="${s:mvcUrl('userController#id-delete').build()}" scope="page"/>
<c:set var="paginationUrl" value="${s:mvcUrl('userController#list').build()}" scope="page"/>

<t:template>
    <jsp:body>
        <div class="row">
            <div class="col-md-12">
                <div class="card card-style mb-5">
                    <h6 class="card-header card-header-style">Seznam uživatelů</h6>
                    <div class="card-body">

                        <div class="row">
                            <div class="col-sm-12 col-md-3 col-lg-3 font-weight-bold">Jméno</div>
                            <div class="col-sm-12 col-md-3 col-lg-3 font-weight-bold">Příjmení</div>
                            <div class="col-sm-12 col-md-3 col-lg-3 font-weight-bold">Rodné číslo</div>
                            <div class="col-sm-12 col-md-3 col-lg-3 text-right">
                            </div>
                        </div>
                        <hr>
                        <c:choose>
                            <c:when test="${empty users}">
                                <div class="row">
                                    <div class="col-sm-12 col-md-12 col-lg-12">
                                        Nejsou k dispozici žádný uživatelé.
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <c:forEach items="${users}" var="item" varStatus="i">
                                    <div class="row">
                                        <div class="col-sm-12 col-md-3 col-lg-3 align-self-center">${item.firstname}</div>
                                        <div class="col-sm-12 col-md-3 col-lg-3 align-self-center">${item.lastname}</div>
                                        <div class="col-sm-12 col-md-3 col-lg-3 align-self-center">
                                                ${fn:substring(item.pid,0,6)}/${fn:substring(item.pid,6,11)}
                                        </div>
                                        <div class="col-sm-12 col-md-3 col-lg-3 text-right">

                                            <a href="${userDeleteUrl}${item.id}" class="text-decoration-none">
                                                <button type="button" class="btn btn-danger btn-sm" onclick="return confirm('Opravdu chcete uživatele smazat?')">
                                                    Smazat
                                                </button>
                                            </a>

                                                <%-- odkaz na účet uživatele --%>
                                            <a href="${userIdUrl}${item.id}" class="text-decoration-none">
                                                <button type="button" class="btn btn-primary btn-sm button_primary_new">
                                                    Zobrazit
                                                </button>
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