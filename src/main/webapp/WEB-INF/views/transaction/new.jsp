<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<%--JS--%>
<s:url value="${pageContext.request.contextPath}/js/transaction.js" var="transactionjs"/>
<script src="${transactionjs}" language="JavaScript" type="text/javascript"></script>

<%-- captcha --%>
<script src="https://www.google.com/recaptcha/api.js" async defer></script>

<%--URL --%>
<c:set var="transactionNewAddUrl" value="${s:mvcUrl('transactionController#new-add').build()}" scope="page"/>
<c:set var="transactionNewIdUrl" value="${s:mvcUrl('transactionController#new-id').build()}" scope="page"/>
<c:set var="transactionNewUrl" value="${s:mvcUrl('transactionController#new').build()}" scope="page"/>


<t:template>
    <jsp:body>
        <div class="row">
            <div class="col-md-12">
                <div class="card card-style mb-5">
                    <h6 class="card-header card-header-style">Nová platba</h6>
                    <div class="card-body">

                            <%-- formulář pro přidání transakce --%>
                        <form:form id="new_transaction_form" modelAttribute="transaction"
                                   action="${transactionNewAddUrl}" method="post">

                            <input name="checkAccount" id="checkAccountId" value="true" type="hidden"/>

                            <div class="row mb-2">
                                <div class="col-sm-12 col-md-6 col-lg-6 align-self-center font-weight-bold">
                                    Použít vzor
                                </div>

                                <div class="col-sm-12 col-md-6 col-lg-6">
                                    <select class="form-control" onchange="this.options[this.selectedIndex].value &&
                                        (window.location = this.options[this.selectedIndex].value);">
                                        <c:if test="${template != null}">
                                            <option value="${transactionNewIdUrl}${template.id}">${template.name}</option>
                                            <option value="${transactionNewUrl}">
                                                Bez použití vzoru
                                            </option>
                                        </c:if>
                                        <c:if test="${template == null}">
                                            <option value="${transactionNewUrl}">
                                                Bez použití vzoru
                                            </option>
                                        </c:if>
                                        <c:forEach items="${templates}" var="item" varStatus="i">
                                            <c:if test="${template != null}">
                                                <c:if test="${item.id != template.id}">
                                                    <option value="${transactionNewIdUrl}${item.id}">${item.name}</option>
                                                </c:if>
                                            </c:if>
                                            <c:if test="${template == null}">
                                                <option value="${transactionNewIdUrl}${item.id}">${item.name}</option>
                                            </c:if>
                                        </c:forEach>>
                                    </select>
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
                                            <form:input path="number" type="text" class="form-control" maxlength="17"
                                                        id="accountNumber"/>
                                        </div>
                                        <div class="col-sm-1 col-md-1 col-lg-1 align-self-center text-center size-25">
                                            /
                                        </div>
                                        <div class="col-sm-6 col-md-6 col-lg-6">
                                            <form:select cssClass="form-control" path="code" id="bankCode">
                                                <c:forEach var="item" items="${bankCodes}">
                                                    <c:if test="${template != null && template.code == item.bankCode}">
                                                        <option selected="selected" value="${item.bankCode}"><c:out
                                                                value="${item.bankCode} - ${item.name}"/></option>
                                                    </c:if>
                                                    <c:if test="${template == null && transaction.code == item.bankCode}">
                                                        <option selected="selected" value="${item.bankCode}"><c:out
                                                                value="${item.bankCode} - ${item.name}"/></option>
                                                    </c:if>
                                                    <c:if test="${template == null || transaction.code != item.bankCode}">
                                                        <option value="${item.bankCode}"><c:out
                                                                value="${item.bankCode} - ${item.name}"/></option>
                                                    </c:if>
                                                </c:forEach>
                                            </form:select>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-5 col-md-5 col-lg-5">
                                            <form:errors class="text-danger" path="number"/>
                                        </div>
                                        <div class="col-sm-1 col-md-1 col-lg-1 align-self-center text-center size-25">
                                        </div>
                                        <div class="col-sm-6 col-md-6 col-lg-6">
                                            <form:errors class="text-danger" path="code"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <hr>
                            <div class="row mb-2">
                                <div class="col-sm-12 col-md-6 col-lg-6 align-self-center font-weight-bold">
                                    Částka*
                                </div>
                                <div class="col-sm-12 col-md-5 col-lg-5">
                                    <c:if test="${template == null}">
                                        <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2"
                                                          value="${transaction.value}" var="newValue"
                                                          groupingUsed="false"/>
                                    </c:if>
                                    <c:if test="${template != null}">
                                        <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2"
                                                          value="${template.value}" var="newValue"
                                                          groupingUsed="false"/>
                                    </c:if>
                                    <form:input path="value" value="${newValue}" type="text" class="form-control"
                                                maxlength="15"/>
                                    <form:errors class="text-danger" path="value"/>
                                </div>
                                <div class="col-sm-12 col-md-1 col-lg-1 align-self-center text-right">CZK</div>
                            </div>
                            <hr>
                            <div class="row mb-2">
                                <div class="col-sm-12 col-md-6 col-lg-6 align-self-center font-weight-bold">
                                    Splatnost*
                                </div>
                                <div class="col-sm-12 col-md-6 col-lg-6">

                                        <%--Aktuální datum pro input date--%>
                                    <jsp:useBean id="dateNow" class="java.util.Date"/>
                                    <fmt:formatDate var="actualDate" value="${dateNow}" pattern="yyyy-MM-dd"/>

                                    <form:input path="dueDate" min="${actualDate}" placeholder="${actualDate}"
                                                maxlength="10" type="date" class="form-control"/>
                                    <form:errors class="text-danger" path="dueDate"/>
                                </div>
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
                                <div class="col-sm-12 col-md-6 col-lg-6 font-weight-bold">
                                    Zpráva
                                </div>
                                <div class="col-sm-12 col-md-6 col-lg-6">
                                    <form:input path="message" type="text" class="form-control" maxlength="100"/>
                                    <form:errors class="text-danger" path="message"/>
                                </div>
                            </div>
                            <hr>
                            <div class="row mb-2">
                                <div class="col-sm-12 col-md-12 col-lg-12 text-right">

                                        <%-- tlačítko pro odeslání nové transakce --%>
                                    <button class="g-recaptcha btn btn-primary btn-sm button_primary_new"
                                            data-sitekey="6LftVoYUAAAAAATyI7xI5eS3Nx0oM3WkksD0_KWC"
                                            data-callback='onSubmit'>
                                        Odeslat
                                    </button>

                                </div>
                            </div>
                        </form:form>

                    </div>
                </div>
            </div>
        </div>

        <%--modální okno--%>
        <div id="myModal" class="modal" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Potvrzení transakce</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <p>Zvolený účet neexistuje. Opravdu chcete transakci potvrdit?</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" onclick="sendTransaction()">ANO</button>
                        <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="captchaReset()">
                            NE
                        </button>
                    </div>
                </div>
            </div>
        </div>

    </jsp:body>
</t:template>