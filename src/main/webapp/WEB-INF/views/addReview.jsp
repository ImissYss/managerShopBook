<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>

<div class="col-md-12">

    <c:choose>
        <c:when test="${edit}">
            <h3 class="my-4">Edit review : ${reviewTitle}</h3>
        </c:when>
        <c:otherwise>
            <h3 class="my-4">Add review book "${book.title}</h3>
        </c:otherwise>
    </c:choose>

    <c:if test="${added}">
        Add review success. View <a href="/mybooks/review/${reviewPath}">${reviewTitle}</a> Or <a href="${pageContext.request.contextPath}/reviews/{bookId}/add">Add review</a>

    </c:if>

    <c:if test="${edited}">
        Edited Success. View <a href="/mybooks/review/${reviewPath}">${reviewTitle}</a>
    </c:if>
    <c:if test="${not added and not edited}">
        <div class="alert alert-danger">
            ${errorMsg}
        </div>
    </c:if>
    <form:form modelAttribute="review" method="post">
        <form:errors path="*" cssClass="alert alert-danger" element="div"/>
        <div class="form-group">
            <label for="title">Title</label>
            <form:input class="form-control" path="title" type="text" id="title" value="${review.title}" placeholder="Title"/>
            <form:errors path="title" cssClass="text-danger"/>

        </div>
        <div class="form-group">
            <label for="content">Content Review</label>
            <form:textarea path="content" class="form-control" value="${review.content}" placeholder="Content"/>
            <form:errors path="content" cssClass="text-danger"/>
        </div>
        <button type="submit" class="btn btn-primary">Add Review</button>


    </form:form>

</div>