<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<t:template>
    <jsp:body>
        <div class="row">
            <div class="col-md-12">
                <div class="card card-style mb-5">
                    <h6 class="card-header card-header-style">Přihlášení uživatele</h6>
                    <div class="card-body">
                        <div class="form-group" style="max-width: 450px; margin: 0 auto;">

                                <%-- formulář pro přihlášení --%>
                            <form action="${pageContext.request.contextPath}/j_spring_security_check" method="post">
                                <div class="row" style="margin-bottom: 7px;">
                                    <input type="text" name="login_id" class="form-control"
                                           placeholder="Identifikační číslo"/>
                                </div>
                                <div class="row" style="margin-bottom: 7px;">
                                    <input type="password" name="pin" class="form-control" placeholder="PIN"/>
                                </div>
                                <div class="row" style="margin-bottom: 7px;">
                                    <input type="submit" class="form-control" value="Přihlásit"/>
                                </div>
                            </form>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>