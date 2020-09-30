<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<%--URL--%>
<c:set var="templateIdEditUrl" value="${s:mvcUrl('templateController#id-edit').build()}" scope="page"/>

<t:template>
    <jsp:body>
        <div class="row">
            <div class="col-md-12">
                <div class="card card-style mb-5">
                    <h6 class="card-header card-header-style">Úprava vzoru platby</h6>
                    <div class="card-body">

                            <%-- formulář pro úpravu šablony --%>
                        <form:form modelAttribute="template" action="${templateIdEditUrl}${template.id}"
                                   method="post">
                            <div class="row mb-2">
                                <div class="col-sm-12 col-md-6 col-lg-6 align-self-center font-weight-bold">
                                    Název*
                                </div>
                                <div class="col-sm-12 col-md-6 col-lg-6">
                                    <form:input path="name" type="text" class="form-control" maxlength="50"/>
                                    <form:errors class="text-danger" path="name"/>
                                </div>
                            </div>
                            <hr>
                            <div class="row mb-2">
                                <div class="col-sm-12 col-md-6 col-lg-6 align-self-center font-weight-bold">
                                    Na účet*
                                </div>
                                <div class="col-sm-12 col-md-6 col-lg-6 col">
                                    <div class="row">
                                        <div class="col-sm-5 col-md-5 col-lg-5">
                                            <form:input path="number" type="text" class="form-control" maxlength="17"/>
                                            <form:errors class="text-danger" path="number"/>
                                        </div>
                                        <div class="col-sm-1 col-md-1 col-lg-1 align-self-center text-center size-25">
                                            /
                                        </div>
                                        <div class="col-sm-6 col-md-6 col-lg-6">
                                            <form:select cssClass="form-control" path="code">
                                                <c:forEach var="item" items="${bankCodes}">
                                                    <c:if test="${template.code == item.bankCode}">
                                                        <option selected="selected" value="${item.bankCode}"><c:out
                                                                value="${item.bankCode} - ${item.name}"/></option>
                                                    </c:if>
                                                    <c:if test="${template.code != item.bankCode}">
                                                        <option value="${item.bankCode}"><c:out
                                                                value="${item.bankCode} - ${item.name}"/></option>
                                                    </c:if>
                                                </c:forEach>
                                            </form:select>
                                            <form:errors class="text-danger" path="code"/>
                                        </div>

                                    </div>
                                </div>
                            </div>
                            <hr>
                            <div class="row mb-2">
                                <div class="col-sm-12 col-md-6 col-lg-6 align-self-center font-weight-bold">
                                    Částka
                                </div>
                                <div class="col-sm-12 col-md-5 col-lg-5">
                                    <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2"
                                                      value="${template.value}" var="newValue" groupingUsed="false"/>
                                    <form:input path="value" value="${newValue}" type="text" class="form-control"
                                                maxlength="15"/>
                                    <form:errors class="text-danger" path="value"/>
                                </div>
                                <div class="col-sm-12 col-md-1 col-lg-1 align-self-center text-right">CZK</div>
                            </div>
                            <hr>
                            <div class="row mb-2">
                                <div class="col-sm-12 col-md-6 col-lg-6 align-self-center font-weight-bold">
                                    Variabilní symbol
                                </div>
                                <div class="col-sm-12 col-md-6 col-lg-6">
                                    <form:input path="variableSymbol" type="text" class="form-control" maxlength="10"/>
                                    <form:errors class="text-danger" path="variableSymbol"/>
                                </div>
                            </div>
                            <hr>
                            <div class="row mb-2">
                                <div class="col-sm-12 col-md-6 col-lg-6 align-self-center font-weight-bold">
                                    Konstantní symbol
                                </div>
                                <div class="col-sm-12 col-md-6 col-lg-6">
                                    <form:input path="constantSymbol" type="text" class="form-control" maxlength="4"/>
                                    <form:errors class="text-danger" path="constantSymbol"/>
                                </div>
                            </div>
                            <hr>
                            <div class="row mb-2">
                                <div class="col-sm-12 col-md-6 col-lg-6 align-self-center font-weight-bold">
                                    Specifický symbol
                                </div>
                                <div class="col-sm-12 col-md-6 col-lg-6">
                                    <form:input path="specificSymbol" type="text" class="form-control" maxlength="10"/>
                                    <form:errors class="text-danger" path="specificSymbol"/>
                                </div>
                            </div>
                            <hr>
                            <div class="row mb-2">
                                <div class="col-sm-12 col-md-6 col-lg-6 align-self-center font-weight-bold">
                                    Zpráva
                                </div>
                                <div class="col-sm-12 col-md-6 col-lg-6">
                                    <form:input path="message" type="text" class="form-control" maxlength="100"/>
                                    <form:errors class="text-danger" path="message"/>
                                </div>
                            </div>
                            <hr>
                            <div class="row mb-2">

                                    <%-- tlačítko pro upravení šablony --%>
                                <div class="col-sm-12 col-md-12 col-lg-12 text-right">
                                    <button type="submit" class="btn btn-primary btn-sm button_primary_new">Upravit
                                    </button>
                                </div>

                            </div>
                        </form:form>


                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>