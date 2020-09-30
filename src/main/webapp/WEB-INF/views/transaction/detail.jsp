<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:template>
    <jsp:body>
        <div class="row">
            <div class="col-md-12">
                <div class="card card-style mb-5">
                    <h6 class="card-header card-header-style">Detail transakce</h6>
                    <div class="card-body">

                        <div class="row">
                            <div class="col-sm-12 col-md-6 col-lg-6 align-self-center font-weight-bold">
                                Účet
                            </div>
                            <div class="col-sm-12 col-md-6 col-lg-6">
                                    ${transaction.number}/${transaction.code}
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-12 col-md-6 col-lg-6 align-self-center font-weight-bold">
                                Částka
                            </div>
                            <div class="col-sm-12 col-md-6 col-lg-6">
                                <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2"
                                                  value="${transaction.value}"/> CZK
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-12 col-md-6 col-lg-6 align-self-center font-weight-bold">
                                Splatnost
                            </div>
                            <div class="col-sm-12 col-md-6 col-lg-6">
                                <fmt:formatDate type="date" pattern="dd.MM.yyyy" value="${transaction.dueDate}"/>
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-12 col-md-6 col-lg-6 align-self-center font-weight-bold">
                                Variabilní symbol
                            </div>
                            <div class="col-sm-12 col-md-6 col-lg-6">
                                    ${transaction.variableSymbol}
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-12 col-md-6 col-lg-6 align-self-center font-weight-bold">
                                Konstantní symbol
                            </div>
                            <div class="col-sm-12 col-md-6 col-lg-6">
                                    ${transaction.constantSymbol}
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-12 col-md-6 col-lg-6 align-self-center font-weight-bold">
                                Specifický symbol
                            </div>
                            <div class="col-sm-12 col-md-6 col-lg-6">
                                    ${transaction.specificSymbol}
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-12 col-md-6 col-lg-6 align-self-center font-weight-bold">
                                Zpráva
                            </div>
                            <div class="col-sm-12 col-md-6 col-lg-6">
                                    ${transaction.message}
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>