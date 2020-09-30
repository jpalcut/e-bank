<%@ tag description="Page template" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>

<html lang="cs">
<head>

    <%-- meta tags --%>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- vlastní CSS -->
    <spring:url value="${pageContext.request.contextPath}/css/style.css" var="style"/>
    <link rel="stylesheet" href="${style}">

    <!-- Bootstrap 4.1.3 -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
            integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
            integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
            crossorigin="anonymous"></script>

    <%--FONT AWESOME--%>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.1/css/all.css"
          integrity="sha384-5sAR7xN1Nv6T6+dT2mhtzEpVJvfS3NScPQTrOxhwjIuvcA67KV2R5Jz6kr4abQsz" crossorigin="anonymous">

    <title>Bank</title>
</head>
<body class="body-bg">

<%-- URL --%>
<c:set var="loginUrl" value="${s:mvcUrl('loginController#login').build()}" scope="page"/>
<c:set var="logoutUrl" value="${s:mvcUrl('loginController#logout').build()}" scope="page"/>

<c:set var="templateListUrl" value="${s:mvcUrl('templateController#list').build()}" scope="page"/>

<c:set var="transactionNewUrl" value="${s:mvcUrl('transactionController#new').build()}" scope="page"/>
<c:set var="transactionListUrl" value="${s:mvcUrl('transactionController#list').build()}" scope="page"/>

<c:set var="userUserUrl" value="${s:mvcUrl('userController#user').build()}" scope="page"/>
<c:set var="userListUrl" value="${s:mvcUrl('userController#list').build()}" scope="page"/>
<c:set var="userNewUrl" value="${s:mvcUrl('userController#new').build()}" scope="page"/>

<c:set var="accountAccountUrl" value="${s:mvcUrl('accountController#account').build()}" scope="page"/>

<c:set var="hometUrl" value="${s:mvcUrl('homeController#home').build()}" scope="page"/>

<c:set var="userRequestListUrl" value="${s:mvcUrl('userRequestController#list').build()}" scope="page"/>

<c:set var="bankCodeListUrl" value="${s:mvcUrl('bankCodeController#list').build()}" scope="page"/>

<c:set var="currentUrl" value="${requestScope['javax.servlet.forward.request_uri']}" scope="page"/>

<!-- DEFAULT MENU-->
<nav class="navbar navbar-expand-lg navbar-dark navbar-bg">

    <div class="container-fluid container-max-width">
        <a href="${hometUrl}" class="navbar-brand navbar-color">e-Banking</a>

        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link navbar-color" href="${hometUrl}">O nás</a>
                </li>
            </ul>
            <ul class="navbar-nav navbar-right">

                <sec:authorize access="isAuthenticated()">
                    <li class="nav-item">
                        <a class="nav-link navbar-color ${currentUrl == userUserUrl ? ' active-link' : ''}"
                           href="${userUserUrl}">
                            <i class="fas fa-user"></i>
                            <sec:authentication property="principal.firstName"/>
                            <sec:authentication property="principal.lastName"/>
                        </a>
                    </li>
                </sec:authorize>

                <li class="nav-item">
                    <sec:authorize access="!isAuthenticated()">
                        <a class="nav-link navbar-color ${currentUrl == loginUrl ? ' active-link' : ''}"
                           href="${loginUrl}">Přihlášení</a>
                    </sec:authorize>
                    <sec:authorize access="isAuthenticated()">
                        <a class="nav-link navbar-color"
                           href="${pageContext.request.contextPath}/logout">Odhlášení</a>
                    </sec:authorize>
                </li>
            </ul>
        </div>
    </div>
</nav>

<sec:authorize access="!isAuthenticated()">
    <div id="under-menu-line"></div>
</sec:authorize>


<%-- USER MENU --%>
<sec:authorize access="hasAuthority('USER')">
    <nav class="navbar navbar-expand-lg navbar-dark navbar-user-bg">

        <div class="container-fluid container-max-width">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link navbar-user-color ${currentUrl == accountAccountUrl ? ' active-link2' : ''}"
                       href="${accountAccountUrl}">Účet</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link navbar-user-color ${currentUrl == transactionNewUrl ? ' active-link2' : ''}"
                       href="${transactionNewUrl}">Nová
                        platba</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link navbar-user-color ${currentUrl == transactionListUrl ? ' active-link2' : ''}"
                       href="${transactionListUrl}">Pohyby</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link navbar-user-color ${currentUrl == templateListUrl ? ' active-link2' : ''}"
                       href="${templateListUrl}">Vzory
                        plateb</a>
                </li>
            </ul>
        </div>
    </nav>
</sec:authorize>

<%-- ADMIN MENU --%>
<sec:authorize access="hasAuthority('ADMIN')">
    <nav class="navbar navbar-expand-lg navbar-dark navbar-user-bg">

        <div class="container-fluid container-max-width">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link navbar-user-color ${currentUrl == userNewUrl ? ' active-link2' : ''}"
                       href="${userNewUrl}">
                        Přidat uživatele
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link navbar-user-color ${currentUrl == userListUrl ? ' active-link2' : ''}"
                       href="${userListUrl}">
                        Uživatelé
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link navbar-user-color ${currentUrl == userRequestListUrl ? ' active-link2' : ''}"
                       href="${userRequestListUrl}">
                        Žádosti
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link navbar-user-color ${currentUrl == bankCodeListUrl ? ' active-link2' : ''}"
                       href="${bankCodeListUrl}">
                        Kódy
                    </a>
                </li>
            </ul>
        </div>
    </nav>
</sec:authorize>


<!-- BODY -->
<div id="body" class="mt-3">
    <div class="container-fluid container-max-width">

        <%--flash message success--%>
        <c:if test="${flashMessageSuccess == true}">
            <div id="flash-message-success" class="alert alert-success alert-dismissible">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                <b>${flashMessageText}</b>
            </div>
        </c:if>

        <%--flash message danger--%>
        <c:if test="${flashMessageSuccess == false}">
            <div id="flash-message-danger" class="alert alert-danger alert-dismissible">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                <b>${flashMessageText}</b>
            </div>
        </c:if>

        <jsp:doBody/>
    </div>
</div>

</body>
</html>