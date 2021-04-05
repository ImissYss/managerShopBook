<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<c:set var="offset" value="2" />

<nav class="page-nav" aria-label="Page navigation">
    <ul class="pagination justify-content-center">
        <%--Prev--%>
        <c:url value="${urlPage}" var="prev">
            <c:param name="page" value="${page-1}"/>

            <c:forEach var="p" items="${param}">
                <c:if test="${not p.key.equals('page')}">
                    <c:param name="${p.key}" value="${p.value}"/>
                </c:if>
            </c:forEach>

        </c:url>
        <c:choose>
            <c:when test="${page > 1}">
                <li class="page-item ">
                    <a class="page-link page-prev-btn" href="<c:out value="${prev}" />" href="<c:out value="${prev}" />">Previous</a>
                </li>
            </c:when>
            <c:otherwise>
                <li class="page-item disabled">
                    <a class="page-link page-prev-btn" href="#" href="#">Previous</a>
                </li>
            </c:otherwise>
        </c:choose>


        <c:choose>
            <c:when test="${3 < page - offset}">
                <c:url value="${urlPage}" var="first">
                    <c:param name="page" value="1"/>

                    <c:forEach var="p" items="${param}">
                        <c:if test="${not p.key.equals('page')}">
                            <c:param name="${p.key}" value="${p.value}"/>
                        </c:if>
                    </c:forEach>

                </c:url>
                <li class="page-item page-not-small-mobile"><a class="page-link" href="<c:out value="${first}" />">1</a></li>
                <li class="page-item page-not-small-mobile"><span class="page-link page-separator">. .</span></li>

                <c:forEach begin="${page - offset}" end="${page - 1}" step="1" varStatus="i">
                    <c:url value="${urlPage}" var="url">
                        <c:param name="page" value="${i.index}"/>

                        <c:forEach var="p" items="${param}">
                            <c:if test="${not p.key.equals('page')}">
                                <c:param name="${p.key}" value="${p.value}"/>
                            </c:if>
                        </c:forEach>

                    </c:url>
                    <li class="page-item page-mobile-hidden page-not-small-mobile"><a class="page-link" href="<c:out value="${url}" />">${i.index}</a></li>

                </c:forEach>
            </c:when>
            <c:otherwise>
                <c:forEach begin="1" end="${page - 1 >= 1 ? page -1 : 0}" step="1" varStatus="i">
                    <c:url value="${urlPage}" var="url">
                        <c:param name="page" value="${i.index}"/>

                        <c:forEach var="p" items="${param}">
                            <c:if test="${not p.key.equals('page')}">
                                <c:param name="${p.key}" value="${p.value}"/>
                            </c:if>
                        </c:forEach>

                    </c:url>
                    <li class="page-item page-not-small-mobile"><a class="page-link" href="<c:out value="${url}" />">${i.index}</a></li>

                </c:forEach>
            </c:otherwise>
        </c:choose>
        <li class="page-item active page-not-small-mobile"><span class="page-link" >${page} <span class="sr-only">(current)</span></span></li>
        <li class="page-item active page-only-small-mobile"><span class="page-link" >${page} / ${totalPage}<span class="sr-only">(current)</span></span></li>

        <c:choose>
            <c:when test="${page + offset < totalPage - 2}">
                <c:forEach begin="${page + 1}" end="${page + offset}" step="1" varStatus="i">
                    <c:url value="${urlPage}" var="url">
                        <c:param name="page" value="${i.index}"/>

                        <c:forEach var="p" items="${param}">
                            <c:if test="${not p.key.equals('page')}">
                                <c:param name="${p.key}" value="${p.value}"/>
                            </c:if>
                        </c:forEach>

                    </c:url>
                    <li class="page-item page-mobile-hidden page-not-small-mobile"><a class="page-link" href="<c:out value="${url}" />">${i.index}</a></li>

                </c:forEach>
                <li class="page-item page-not-small-mobile"><span class="page-link page-separator">. .</span></li>
                <c:url value="${urlPage}" var="last">
                    <c:param name="page" value="${totalPage}"/>

                    <c:forEach var="p" items="${param}">
                        <c:if test="${not p.key.equals('page')}">
                            <c:param name="${p.key}" value="${p.value}"/>
                        </c:if>
                    </c:forEach>

                </c:url>
                <li class="page-item page-not-small-mobile"><a class="page-link" href="<c:out value="${last}" />">${totalPage}</a></li>

            </c:when>
            <c:otherwise>
                <c:forEach begin="${page + 1}" end="${totalPage}" step="1" varStatus="i">
                    <c:url value="${urlPage}" var="url">
                        <c:param name="page" value="${i.index}"/>

                        <c:forEach var="p" items="${param}">
                            <c:if test="${not p.key.equals('page')}">
                                <c:param name="${p.key}" value="${p.value}"/>
                            </c:if>
                        </c:forEach>

                    </c:url>
                    <li class="page-item page-not-small-mobile"><a class="page-link" href="<c:out value="${url}" />">${i.index}</a></li>

                </c:forEach>
            </c:otherwise>
        </c:choose>

        <%--Next--%>
        <c:url value="${urlPage}" var="next">
            <c:param name="page" value="${page+1}"/>

            <c:forEach var="p" items="${param}">
                <c:if test="${not p.key.equals('page')}">
                    <c:param name="${p.key}" value="${p.value}"/>
                </c:if>
            </c:forEach>

        </c:url>
        <c:choose>
            <c:when test="${page + 1 <= totalPage}">
                <li class="page-item ">
                    <a class="page-link page-next-btn" href="<c:out value="${next}" />" href="<c:out value="${next}" />">Next</a>
                </li>
            </c:when>
            <c:otherwise>
                <li class="page-item disabled">
                    <a class="page-link page-next-btn" href="#" href="#">Next</a>
                </li>
            </c:otherwise>
        </c:choose>

    </ul>
</nav>