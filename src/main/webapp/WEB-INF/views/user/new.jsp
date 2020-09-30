<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<%-- captcha --%>
<script src="https://www.google.com/recaptcha/api.js" async defer></script>
<script>
    function onSubmit(token) {
        document.getElementById("new_user_form").submit();
    }
</script>

<%--URL--%>
<c:set var="userNewAddUrl" value="${s:mvcUrl('userController#new-add').build()}" scope="page"/>

<t:template>
    <jsp:body>
        <div class="row">
            <div class="col-md-12">
                <div class="card card-style mb-5">
                    <h6 class="card-header card-header-style">Nový uživatel</h6>
                    <div class="card-body">

                            <%-- formulář pro přidání nového uživatele --%>
                        <form:form id="new_user_form" modelAttribute="userForm" action="${userNewAddUrl}" method="post">
                            <div class="row">
                                <div class="col-sm-12 col-md-6 col-lg-6 align-self-center font-weight-bold">
                                    Jméno*
                                </div>
                                <div class="col-sm-12 col-md-6 col-lg-6">
                                    <form:input path="firstname" type="text" class="form-control" maxlength="50"/>
                                    <form:errors class="text-danger" path="firstname"/>
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-sm-12 col-md-6 col-lg-6 align-self-center font-weight-bold">
                                    Příjmení*
                                </div>
                                <div class="col-sm-12 col-md-6 col-lg-6">
                                    <form:input path="lastname" type="text" class="form-control" maxlength="50"/>
                                    <form:errors class="text-danger" path="lastname"/>
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-sm-12 col-md-6 col-lg-6 align-self-center font-weight-bold">
                                    Rodné číslo*
                                </div>
                                <div class="col-sm-12 col-md-6 col-lg-6">
                                    <form:input path="pid" type="text" class="form-control" maxlength="10"/>
                                    <form:errors class="text-danger" path="pid"/>
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-sm-12 col-md-6 col-lg-6 align-self-center font-weight-bold">
                                    Pohlaví*
                                </div>
                                <div class="col-sm-12 col-md-6 col-lg-6">
                                    <div class="row">
                                        <div class="col-12">
                                            <form:radiobutton path="sex" value="Muž"/>Muž
                                            <form:radiobutton path="sex" value="Žena"/>Žena
                                        </div>

                                    </div>
                                    <div class="row">
                                        <div class="col-12">
                                            <form:errors class="text-danger" path="sex"/>
                                        </div>
                                    </div>
                                </div>

                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-sm-12 col-md-6 col-lg-6 align-self-center font-weight-bold">
                                    Adresa
                                </div>
                                <div class="col-sm-12 col-md-6 col-lg-6">
                                    <form:input path="address" type="text" class="form-control" maxlength="50"/>
                                    <form:errors class="text-danger" path="address"/>
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-sm-12 col-md-6 col-lg-6 align-self-center font-weight-bold">
                                    Číslo popisné
                                </div>
                                <div class="col-sm-12 col-md-6 col-lg-6">
                                    <form:input path="addressNumber" type="text" class="form-control" maxlength="8"/>
                                    <form:errors class="text-danger" path="addressNumber"/>
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-sm-12 col-md-6 col-lg-6 align-self-center font-weight-bold">
                                    Město
                                </div>
                                <div class="col-sm-12 col-md-6 col-lg-6">
                                    <form:input path="town" type="text" class="form-control" maxlength="50"/>
                                    <form:errors class="text-danger" path="town"/>
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-sm-12 col-md-6 col-lg-6 align-self-center font-weight-bold">
                                    PSČ
                                </div>
                                <div class="col-sm-12 col-md-6 col-lg-6">
                                    <form:input path="zipCode" type="text" class="form-control" maxlength="10"/>
                                    <form:errors class="text-danger" path="zipCode"/>
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-sm-12 col-md-6 col-lg-6 align-self-center font-weight-bold">
                                    Stát*
                                </div>

                                <div class="col-sm-12 col-md-6 col-lg-6">
                                    <form:select path="state" cssClass="form-control">
                                        <form:options items="${states}" itemValue="id" itemLabel="name"/>
                                    </form:select>
                                    <form:errors class="text-danger" path="state"/>
                                </div>
                            </div>
                            <hr>
                            <div class="row mb-2">
                                <div class="col-sm-12 col-md-6 col-lg-6 align-self-center font-weight-bold">
                                    Email*
                                </div>
                                <div class="col-sm-12 col-md-6 col-lg-6">
                                    <form:input path="email" type="email" class="form-control" maxlength="50"/>
                                    <form:errors class="text-danger" path="email"/>
                                </div>
                            </div>
                            <hr>
                            <div class="row mb-2">
                                <div class="col-sm-12 col-md-12 col-lg-12 text-right">

                                        <%-- tlačítko pro vytvoření nového uživatele --%>
                                    <button class="g-recaptcha btn btn-primary btn-sm button_primary_new"
                                            data-sitekey="6LftVoYUAAAAAATyI7xI5eS3Nx0oM3WkksD0_KWC"
                                            data-callback='onSubmit'>Vytvořit
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