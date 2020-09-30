<%@ tag description="Pagination template" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row mt-5">

    <%-- celkový počet elementů --%>
    <div class="col-sm-12 col-md-3 col-lg-3 mt-2 d-none d-sm-block">
        Celkem:${pagination.totalElements}
    </div>

    <%-- přepínání stránkek k zobrazení --%>
    <div class="col-sm-12 col-md-6 col-lg-6">
        <div class="row">
            <ul class="pagination mx-auto">
                <li class="page-item ${pagination.hasPrevious() == false ? 'disabled' : ''}">
                    <a class="page-link pagination-style"
                       href="${paginationUrl}?page=0&size=${pagination.size}">&lt;&lt;</a>
                </li>
                <li class="page-item ${pagination.hasPrevious() == false ? 'disabled' : ''}">
                    <a class="page-link pagination-style"
                       href="${paginationUrl}?page=${pagination.number-1}&size=${pagination.size}">&lt;</a>
                </li>

                <c:if test="${pagination.hasNext() == false && pagination.number-2 >= 0}">
                    <li class="page-item">
                        <a class="page-link pagination-style"
                           href="${paginationUrl}?page=${pagination.number-2}&size=${pagination.size}">
                                ${pagination.number-1}
                        </a>
                    </li>
                </c:if>

                <c:if test="${pagination.hasPrevious() == true}">
                    <li class="page-item">
                        <a class="page-link pagination-style"
                           href="${paginationUrl}?page=${pagination.number-1}&size=${pagination.size}">
                                ${pagination.number}
                        </a>
                    </li>
                </c:if>

                <li class="page-item"><a class="page-link pagination-active"
                                         href="${paginationUrl}?page=${pagination.number}&size=${pagination.size}">${pagination.number+1}</a>
                </li>

                <c:if test="${pagination.hasNext() == true}">
                    <li class="page-item">
                        <a class="page-link pagination-style"
                           href="${paginationUrl}?page=${pagination.number+1}&size=${pagination.size}">
                                ${pagination.number+2}
                        </a>
                    </li>
                </c:if>

                <c:if test="${pagination.hasPrevious() == false && ((pagination.number+3) <= pagination.totalPages)}">
                    <li class="page-item">
                        <a class="page-link pagination-style"
                           href="${paginationUrl}?page=${pagination.number+2}&size=${pagination.size}">
                                ${pagination.number+3}
                        </a>
                    </li>
                </c:if>

                <li class="page-item ${pagination.hasNext() == false ? 'disabled' : ''}">
                    <a class="page-link pagination-style"
                       href="${paginationUrl}?page=${pagination.number+1}&size=${pagination.size}">&gt;</a>
                </li>
                <li class="page-item ${pagination.hasNext() == false ? 'disabled' : ''}">
                    <a class="page-link pagination-style"
                       href="${paginationUrl}?page=${pagination.totalPages-1}&size=${pagination.size}">&gt;&gt;</a>
                </li>
            </ul>
        </div>
    </div>

    <%-- výběr počtu elementů k zobrazení --%>
    <div class="col-sm-12 col-md-3 col-lg-3 text-right mt-2">
        <div class="d-block d-sm-none">
            Celkem:${pagination.totalElements}
        </div>

        <a href="${paginationUrl}?page=0&size=2"
           class="${pagination.size == 2 ? 'pagination-size-active' : 'pagination-size'}">2</a>
        /
        <a href="${paginationUrl}?page=0&size=10"
           class="${pagination.size == 10 ? 'pagination-size-active' : 'pagination-size'}">10</a>
        /
        <a href="${paginationUrl}?page=0&size=20"
           class="${pagination.size == 20 ? 'pagination-size-active' : 'pagination-size'}">20</a>
        /
        <a href="${paginationUrl}?page=0&size=50"
           class="${pagination.size == 50 ? 'pagination-size-active' : 'pagination-size'}">50</a>
    </div>

</div>