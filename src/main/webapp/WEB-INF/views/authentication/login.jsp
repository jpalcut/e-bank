<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<t:template>
    <jsp:body>
        <div class="row">
            <div class="col-md-12">
                <div class="card card-style mb-5">
                    <h6 class="card-header card-header-style">Přihlášení uživatele</h6>
                    <div class="card-body">

                            <%-- formulář pro přihlášení --%>
                        <div class="form-group" id="login-form">
                            <form action="${pageContext.request.contextPath}/j_spring_security_check" method="post">
                                <div class="row mb-3 mt-5">
                                    <input type="text" name="login_id" class="form-control"
                                           placeholder="Identifikační číslo"/>
                                </div>
                                <div class="row mb-3">
                                    <input type="password" name="pin" class="form-control" placeholder="PIN"/>
                                </div>
                                <div class="row mb-5">
                                    <input type="submit" class="btn btn-primary button_primary_new width-max"
                                           value="Přihlásit"/>
                                </div>
                            </form>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>