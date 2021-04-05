<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<div class="col-md-12">
    <h3 class="my-4">Search Author</h3>

    <form method="get" action="<c:url value="/authors/search"/> " class="form-inline">
        <label class="sr-only" for="searchTerm">Search</label>
        <input type="text" style="flex: 1;" class="form-control mb-2 mr-sm-2"
        name="searchTerm" id="searchTerm" placeholder="Search..">
        <c:if test="${not empty searchTerm}">value=${searchTerm}</c:if>

        <button type="submit" class="btn btn-primary mb-2">Search</button>
    </form>

    <c:if test="${search}">
        <h5 class="my-4">Search: ${resultCount} author</h5>

        <c:forEach items="${authors}" var="author" varStatus="status">
            <a href="/mybooks/author/${author.id}">${author.getDisplayName()}</a>
            <br/>
        </c:forEach>
        <br/>
        <c:if test="${resultCount >0}">
            <tiles:insertAttribute name="pagination"/>
        </c:if>
    </c:if>
</div>