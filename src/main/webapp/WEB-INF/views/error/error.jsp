<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template>
    <jsp:body>
        <div class="row">
            <div class="col-md-12">
                <div class="card card-style mb-5">
                    <h6 class="card-header card-header-style">ERROR</h6>
                    <div class="card-body">

                            <%-- error code --%>
                        <div class="row mt-5 mb-3">
                            <div class="col-12 mx-auto text-center size-25">
                                    ${errorCode}
                            </div>

                        </div>

                            <%-- error message --%>
                        <div class="row mb-5">
                            <div class="col-12 mx-auto text-center">
                                    ${errorMessage}
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>